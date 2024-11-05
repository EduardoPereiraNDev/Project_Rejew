package com.example.projeto_rejew.api;

import com.example.projeto_rejew.entity.Comentario;
import com.example.projeto_rejew.entity.Usuario;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ComentarioApi {
    @GET("comentarios")
    Call<List<Comentario>> listarComentarios();

    @GET("comentarios/{id}")
    Call<Comentario> buscarComentarioPorId(@Path("id") Long id);

    @GET("comentarios/usuario/comentario/{idComentario}")
    Call<Usuario> buscarUsuarioporComentario(@Path("idComentario") Long idComentario);

    @GET("comentarios/usuario/{usuarioComent}")
    Call<List<Comentario>> buscarComentariosPorUsuario(@Path("usuarioComent") String usuarioComent);

    @GET("comentarios/livro/{idLivro}")
    Call<List<Comentario>> buscarComentariosPorLivro(@Path("idLivro") Long idLivro);

    @POST("comentarios")
    Call<Comentario> adicionarComentario(@Body Comentario comentario);

    @PUT("comentarios/{id}")
    Call<Comentario> atualizarComentario(@Path("id") Long id, @Body Comentario comentario);

    @DELETE("comentarios/{id}")
    Call<ResponseBody> deletarComentario(@Path("id") Long id);
}
