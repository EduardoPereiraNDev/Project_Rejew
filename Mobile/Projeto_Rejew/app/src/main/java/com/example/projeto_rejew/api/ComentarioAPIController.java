package com.example.projeto_rejew.api;

import android.util.Log;

import com.example.projeto_rejew.entity.Comentario;
import com.example.projeto_rejew.entity.Usuario;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComentarioAPIController {

    private RetrofitClient retrofitClient;
    private String status;
    private ComentarioApi comentarioApi;

    public interface ComentarioCallback {
        void onSuccess(Comentario comentario);
        void onSuccessList(List<Comentario> ComentarioL);
        void onFailure(Throwable t);
    }

    public ComentarioAPIController(RetrofitClient retrofitClient) {
        this.retrofitClient = retrofitClient;
        this.comentarioApi = RetrofitClient.getRetrofit().create(ComentarioApi.class);
        this.status = "";
    }


    public void adicionarComentario(Comentario comentario, ComentarioAPIController.ComentarioCallback responseCallback) {
        Log.d("API Call", "realizando comentario " + comentario.getUsuarioComent().getEmailEntrada());
        Call<Comentario> call = this.comentarioApi.adicionarComentario(comentario);
        call.enqueue(new Callback<Comentario>() {
            @Override
            public void onResponse(Call<Comentario> call, Response<Comentario> response) {
                Log.d("API Response", "Código de resposta: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API Response", "Comentario adicionado: " + response.body().toString());
                    responseCallback.onSuccess(response.body());
                } else {
                    // Adicione uma verificação para imprimir o corpo da resposta de erro
                    if (response.errorBody() != null) {
                        try {
                            String errorBody = response.errorBody().string();
                            Log.e("API Error", "Corpo da resposta de erro: " + errorBody);
                        } catch (IOException e) {
                            Log.e("API Error", "Erro ao ler corpo da resposta de erro: " + e.getMessage());
                        }
                    } else {
                        Log.e("API Error", "Resposta sem corpo de erro");
                    }
                    responseCallback.onFailure(new Exception("Erro: " + response.code()));
                }
            }
            @Override
            public void onFailure(Call<Comentario> call, Throwable t) {
                Log.e("API Error", "Falha na chamada: " + t.getMessage());
                responseCallback.onFailure(t);
            }
        });
    }
}
