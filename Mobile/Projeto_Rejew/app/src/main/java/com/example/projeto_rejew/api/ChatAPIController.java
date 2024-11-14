package com.example.projeto_rejew.api;

import android.util.Log;

import com.example.projeto_rejew.entity.Chat;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatAPIController {

    private final ChatApi chatApi;

    public interface ChatCallback {
        void onSuccess(Chat chat);
        void onSuccessByte(byte[] bytes);
        void onSuccessList(List<Chat> chatList);
        void onFailure(Throwable t);
    }

    public ChatAPIController(RetrofitClient retrofitClient) {
        this.chatApi = retrofitClient.getRetrofit().create(ChatApi.class);
    }

    // Método para carregar todos os chats
    public void carregarChats(ChatCallback callback) {
        chatApi.listarTodosChats().enqueue(new Callback<List<Chat>>() {
            @Override
            public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccessList(response.body());
                } else {
                    Log.e("API Error", "Erro ao carregar chats: " + response.code());
                    callback.onFailure(new Exception("Erro ao carregar chats"));
                }
            }

            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {
                Log.e("API Error", "Falha na chamada: " + t.getMessage());
                callback.onFailure(t);
            }
        });
    }

    // Método para buscar um chat específico pelo ID
    public void buscarChatPorId(long idChat, ChatCallback callback) {
        chatApi.buscarChatPorId(idChat).enqueue(new Callback<Chat>() {
            @Override
            public void onResponse(Call<Chat> call, Response<Chat> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    Log.e("API Error", "Erro ao carregar o chat: " + response.code());
                    callback.onFailure(new Exception("Erro ao carregar o chat"));
                }
            }

            @Override
            public void onFailure(Call<Chat> call, Throwable t) {
                Log.e("API Error", "Falha na chamada: " + t.getMessage());
                callback.onFailure(t);
            }
        });
    }

    // Método para retornar imagem associada ao chat
    public void retornarImagem(String caminhoImgCapa, ChatCallback callback) {
        chatApi.retornarImagem(caminhoImgCapa).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        byte[] imageBytes = response.body().bytes();
                        callback.onSuccessByte(imageBytes);
                    } catch (Exception e) {
                        Log.e("API Error", "Erro ao processar a imagem: " + e.getMessage());
                        callback.onFailure(e);
                    }
                } else {
                    Log.e("API Error", "Erro ao baixar a imagem: " + response.code());
                    callback.onFailure(new Exception("Erro ao baixar a imagem"));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("API Error", "Falha na chamada: " + t.getMessage());
                callback.onFailure(t);
            }
        });
    }
}
