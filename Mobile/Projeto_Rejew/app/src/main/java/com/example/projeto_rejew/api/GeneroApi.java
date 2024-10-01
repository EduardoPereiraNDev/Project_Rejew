package com.example.projeto_rejew.api;

import com.example.projeto_rejew.entity.Genero;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GeneroApi {
    @GET("generos")
    Call<List<Genero>> listarTodosGeneros();

    @GET("generos/{id}")
    Call<Genero> buscarGeneroPorId(@Path("id") Long id);

    @GET("generos/genero/{genero}")
    Call<List<Genero>> buscarGeneroPorNome(@Path("genero") String genero);

    @POST("generos")
    Call<Genero> salvarGenero(@Body Genero genero);

    @PUT("generos/{id}")
    Call<Genero> atualizarGenero(@Path("id") Long id, @Body Genero genero);

    @DELETE("generos/{id}")
    Call<Void> deletarGenero(@Path("id") Long id);
}
