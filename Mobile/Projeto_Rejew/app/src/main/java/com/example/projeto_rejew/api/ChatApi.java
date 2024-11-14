package com.example.projeto_rejew.api;

import com.example.projeto_rejew.entity.Chat;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ChatApi {
    @GET("chats")
    Call<List<Chat>> listarTodosChats();

    @GET("chats/{id}")
    Call<Chat> buscarChatPorId(@Path("id") Long id);

    @GET("chats/imagemLogo/{caminho}")
    Call<ResponseBody> retornarImagem(@Path("caminho") String caminho);

    @GET("chats/imagemFundo/{caminho}")
    Call<ResponseBody> retornarImagemFundo(@Path("caminho") String caminho);

    @GET("chats/chat/{genero}")
    Call<List<Chat>> buscarChatPorGenero(@Path("genero") String genero);

    @Multipart
    @POST("chats")
    Call<String> adicionarChat(
            @Part("generoChat") RequestBody generoChat,
            @Part MultipartBody.Part caminhoImagemLogo,
            @Part MultipartBody.Part caminhoImagemFundoChat);

    @Multipart
    @PUT("chats/{id}")
    Call<Chat> atualizarChat(
            @Path("id") Long id,
            @Part("generoChat") RequestBody generoChat,
            @Part MultipartBody.Part caminhoImagemLogo,
            @Part MultipartBody.Part caminhoImagemFundoChat);

    @DELETE("chats/{id}")
    Call<Void> deletarChat(@Path("id") Long id);
}
