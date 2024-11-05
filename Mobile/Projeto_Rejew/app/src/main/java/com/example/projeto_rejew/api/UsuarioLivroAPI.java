package com.example.projeto_rejew.api;

import com.example.projeto_rejew.entity.Livro;
import com.example.projeto_rejew.entity.Usuario;
import com.example.projeto_rejew.entity.UsuarioLivroADTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UsuarioLivroAPI {

    @GET("usuarioLivro")
    Call<List<UsuarioLivroADTO>> listarTodasAvaliacoes();

    @GET("usuarioLivro/media/{idLivro}")
    Call<Double> calcularMedia(@Path("idLivro") Long idLivro);

    @GET("usuarioLivro/jaAvaliado/{emailUsuario}/{isbnLivro}")
    Call<Boolean> verificarJaAvaliado(@Path("emailUsuario") String emailUsuario, @Path("isbnLivro") Long isbnLivro);

    @POST("/avaliar/{email}/{isbnLivro}/{notaAvaliacao}")
    Call<UsuarioLivroADTO> inserirAvaliacao(@Path("email") String email,@Path("isbnLivro") Long isbnLivro , @Path("notaAvaliacao") double notaAvaliacao);
}
