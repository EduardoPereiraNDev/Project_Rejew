package com.example.projeto_rejew.api;

import com.example.projeto_rejew.entity.Comentario;
import com.example.projeto_rejew.entity.Mensagem;
import com.example.projeto_rejew.entity.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MensagemApi {
    @GET("mensagens")
    Call<List<Mensagem>> listarTodasMensagens();

    @GET("mensagens/{id}")
    Call<Mensagem> buscarMensagemPorId(@Path("id") Long id);

    @GET("mensagens/chat/{chat}")
    Call<List<Mensagem>> buscarMensagensPorChat(@Path("chat") String chat);

    @GET("mensagens/usuario/{usuario}")
    Call<List<Mensagem>> buscarMensagensPorUsuario(@Path("usuario") String usuario);

    @GET("mensagens/usuario/mensagem/{idMensagem}")
    Call<Usuario> buscarUsuarioPorMensagens(@Path("idMensagem") Long idMensagem);

    @POST("mensagens")
    Call<Mensagem> enviarMensagem(@Body Mensagem mensagem);

    @PUT("mensagens/{id}")
    Call<Mensagem> atualizarMensagem(@Path("id") Long id, @Body Mensagem mensagem);

    @DELETE("mensagens/{id}")
    Call<Void> deletarMensagem(@Path("id") Long idMensagem);
}
