package com.example.projeto_rejew.api;

import android.util.Log;

import com.example.projeto_rejew.entity.Mensagem;
import com.example.projeto_rejew.entity.Usuario;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MensagemAPIController {

    private RetrofitClient retrofitClient;
    private MensagemApi mensagemApi;

    public interface MensagemCallback {
        void onSuccess(Mensagem mensagem);
        void onSuccessString(String string);
        void onSuccessResponse(ResponseBody responseBody);
        void onSuccessList(List<Mensagem> mensagemList);
        void onSuccessUsuario(Usuario usuario);
        void onFailure(Throwable t);
    }

    // Construtor
    public MensagemAPIController(RetrofitClient retrofitClient) {
        this.retrofitClient = retrofitClient;
        this.mensagemApi = retrofitClient.getRetrofit().create(MensagemApi.class);
    }

    // Método para salvar uma mensagem
    public void salvarMensagem(Mensagem mensagem, MensagemCallback responseCallback) {
        Call<Mensagem> call = mensagemApi.salvarMensagem(mensagem);
        call.enqueue(new Callback<Mensagem>() {
            @Override
            public void onResponse(Call<Mensagem> call, Response<Mensagem> response) {
                if (response.isSuccessful() && response.body() != null) {
                    responseCallback.onSuccess(response.body());
                } else {
                    Log.e("API Error", "Erro ao salvar mensagem: " + response.code());
                    responseCallback.onFailure(new Exception("Erro ao salvar mensagem"));
                }
            }

            @Override
            public void onFailure(Call<Mensagem> call, Throwable t) {
                Log.e("API Error", "Falha na chamada: " + t.getMessage());
                responseCallback.onFailure(t);
            }
        });
    }

    // Método para deletar uma mensagem
    public void deletarMensagem(Long idMensagem, MensagemCallback responseCallback) {
        Call<Void> call = mensagemApi.deletarMensagem(idMensagem);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    responseCallback.onSuccessResponse(null);
                } else {
                    Log.e("API Error", "Erro ao deletar mensagem: " + response.code());
                    responseCallback.onFailure(new Exception("Erro ao deletar mensagem"));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API Error", "Falha ao deletar mensagem: " + t.getMessage());
                responseCallback.onFailure(t);
            }
        });
    }

    // Método para buscar mensagens por chat
    public void buscarMensagensPorChat(String chatId, MensagemCallback responseCallback) {
        Call<List<Mensagem>> call = mensagemApi.buscarMensagensPorChat(chatId);
        call.enqueue(new Callback<List<Mensagem>>() {
            @Override
            public void onResponse(Call<List<Mensagem>> call, Response<List<Mensagem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    responseCallback.onSuccessList(response.body());
                } else {
                    Log.e("API Error", "Erro ao buscar mensagens: " + response.code());
                    responseCallback.onFailure(new Exception("Erro ao buscar mensagens"));
                }
            }

            @Override
            public void onFailure(Call<List<Mensagem>> call, Throwable t) {
                Log.e("API Error", "Falha na chamada: " + t.getMessage());
                responseCallback.onFailure(t);
            }
        });
    }

    // Método para buscar mensagem por ID
    public void buscarMensagemPorId(Long idMensagem, MensagemCallback responseCallback) {
        Call<Mensagem> call = mensagemApi.buscarMensagemPorId(idMensagem);
        call.enqueue(new Callback<Mensagem>() {
            @Override
            public void onResponse(Call<Mensagem> call, Response<Mensagem> response) {
                if (response.isSuccessful() && response.body() != null) {
                    responseCallback.onSuccess(response.body());
                } else {
                    Log.e("API Error", "Erro ao buscar mensagem por ID: " + response.code());
                    responseCallback.onFailure(new Exception("Erro ao buscar mensagem por ID"));
                }
            }

            @Override
            public void onFailure(Call<Mensagem> call, Throwable t) {
                Log.e("API Error", "Falha na chamada: " + t.getMessage());
                responseCallback.onFailure(t);
            }
        });
    }

    // Método para enviar uma nova mensagem
    public void enviarMensagem(Mensagem mensagem, MensagemCallback responseCallback) {
        Log.d("API Call", "Enviando mensagem para o usuário " + mensagem.getUsuarioMensagem().getEmailEntrada());
        Call<Mensagem> call = mensagemApi.enviarMensagem(mensagem);
        call.enqueue(new Callback<Mensagem>() {
            @Override
            public void onResponse(Call<Mensagem> call, Response<Mensagem> response) {
                Log.d("API Response", "Código de resposta: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API Response", "Mensagem enviada com sucesso: " + response.body().toString());
                    responseCallback.onSuccess(response.body());
                } else {
                    if (response.errorBody() != null) {
                        try {
                            String errorBody = response.errorBody().string();
                            Log.e("API Error", "Corpo da resposta de erro: " + errorBody);
                        } catch (IOException e) {
                            Log.e("API Error", "Erro ao ler corpo da resposta de erro: " + e.getMessage());
                        }
                    } else {
                        Log.e("API Error", "Erro sem corpo de resposta");
                    }
                    responseCallback.onFailure(new Exception("Erro ao enviar mensagem: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Mensagem> call, Throwable t) {
                Log.e("API Error", "Falha ao enviar mensagem: " + t.getMessage());
                responseCallback.onFailure(t);
            }
        });
    }
}
