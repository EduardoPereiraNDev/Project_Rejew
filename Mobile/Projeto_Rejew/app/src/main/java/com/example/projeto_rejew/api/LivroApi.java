package com.example.projeto_rejew.api;

import com.example.projeto_rejew.entity.Livro;

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

public interface LivroApi {
    @GET("livros")
    Call<List<Livro>> listarTodosLivros();

    @GET("livros/{isbn}")
    Call<Livro> buscarLivroPorId(@Path("isbn") Long isbn);

    @GET("livros/nome/{nome}")
    Call<List<Livro>> buscarLivroPorNome(@Path("nome") String nome);

    @GET("livros/imagem/{caminho}")
    Call<ResponseBody> retornarImagem(@Path("caminho") String caminho);

    @GET("livros/autor/{autor}")
    Call<List<Livro>> buscarLivroPorAutor(@Path("autor") String autor);
}
