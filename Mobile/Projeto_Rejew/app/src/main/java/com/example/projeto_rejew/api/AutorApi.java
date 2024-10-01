package com.example.projeto_rejew.api;

import com.example.projeto_rejew.entity.Autor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AutorApi {
    @GET("autores")
    Call<List<Autor>> listarTodosAutores();

    @GET("autores/{id}")
    Call<Autor> buscarAutorPorId(@Path("id") Long id);

    @GET("autores/usuarios/{usuarioEmailEntrada}")
    Call<List<Autor>> buscarAutorEmailUsuario(@Path("usuarioEmailEntrada") String usuarioEmailEntrada);

    @POST("autores")
    Call<Autor> tornarAutor(@Body Autor autor);

    @PUT("autores/{id}")
    Call<Autor> atualizarAutor(@Path("id") Long id, @Body Autor autor);

    @DELETE("autores/{id}")
    Call<Void> deletarAutor(@Path("id") Long id);
}
