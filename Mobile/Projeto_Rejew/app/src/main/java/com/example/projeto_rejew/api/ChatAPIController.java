package com.example.projeto_rejew.api;

import android.util.Log;

import com.example.projeto_rejew.entity.Chat;
import com.example.projeto_rejew.entity.Genero;
import com.example.projeto_rejew.entity.Livro;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatAPIController {

    ChatApi chatApi;
    private RetrofitClient retrofitClient;

    public interface ChatCallback {
        void onSuccess(Chat chat);
        void onSuccessByte(byte[] bytes);
        void onSuccessList(List<Chat> ChatL);

        void onFailure(Throwable t);
    }

    public ChatAPIController(RetrofitClient retrofitClient) {
        this.retrofitClient = retrofitClient;
        this.chatApi = retrofitClient.getRetrofit().create(ChatApi.class);
    }

    public void carregarChats(ChatAPIController.ChatCallback responseCallback) {
        Call<List<Chat>> call = chatApi.listarTodosChats();
        call.enqueue(new Callback<List<Chat>>() {
            @Override
            public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    responseCallback.onSuccessList(response.body());
                } else {
                    Log.e("API Error", "Erro ao carregar chats: " + response.code());
                    responseCallback.onFailure(new Exception("Erro ao carregar chats"));
                }
            }
            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {
                Log.e("API Error", "Falha na chamada: " + t.getMessage());
                responseCallback.onFailure(t);
            }
        });
    }
    public void retornarImagem(ChatAPIController.ChatCallback callback, String caminhoImgCapa) {
        Call<ResponseBody> call = chatApi.retornarImagem(caminhoImgCapa);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        byte[] imageBytes = response.body().bytes();
                        callback.onSuccessByte(imageBytes);
                    } catch (Exception e) {
                        callback.onFailure(e);
                    }
                } else {
                    callback.onFailure(new Exception("Erro ao baixar a imagem: " + response.code()));
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }
}
