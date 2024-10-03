package com.example.projeto_rejew.api;

import com.example.projeto_rejew.entity.Livro;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface LivroApi {
    @GET("livros")
    Call<List<Livro>> listarTodosLivros();

    @GET("livros/{isbn}")
    Call<Livro> buscarLivroPorId(@Path("isbn") Long isbn);

    @GET("livros/nome/{nome}")
    Call<List<Livro>> buscarLivroPorNome(@Path("nome") String nome);

    @GET("livros/imagem/{caminho}")
    Call<byte[]> retornarImagem(@Path("caminho") String caminho);

    @GET("livros/autor/{autor}")
    Call<List<Livro>> buscarLivroPorAutor(@Path("autor") String autor);

    @Multipart
    @POST("livros")
    Call<String> adicionarLivro(
            @Part("nomeLivro") RequestBody nomeLivro,
            @Part("autorLivro") RequestBody autorLivro,
            @Part("sinopseLivro") RequestBody sinopseLivro,
            @Part("numeroPag") RequestBody numeroPag,
            @Part("anoLancamento") RequestBody anoLancamento,
            @Part("generoLivro") RequestBody generoLivro,
            @Part("corPrimaria") RequestBody corPrimaria,
            @Part MultipartBody.Part caminhoImgCapa);

    @Multipart
    @PUT("livros/{isbn}")
    Call<Livro> atualizarLivro(
            @Path("isbn") Long isbn,
            @Part("nomeLivro") RequestBody nomeLivro,
            @Part("autorLivro") RequestBody autorLivro,
            @Part("sinopseLivro") RequestBody sinopseLivro,
            @Part("numeroPag") RequestBody numeroPag,
            @Part("anoLancamento") RequestBody anoLancamento,
            @Part("generoLivro") RequestBody generoLivro,
            @Part("corPrimaria") RequestBody corPrimaria,
            @Part MultipartBody.Part caminhoImgCapa);

    @DELETE("livros/{isbn}")
    Call<Void> deletarLivro(@Path("isbn") Long isbn);
}
