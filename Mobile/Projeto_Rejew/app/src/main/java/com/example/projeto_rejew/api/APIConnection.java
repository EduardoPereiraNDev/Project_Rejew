package com.example.projeto_rejew.api;

import androidx.annotation.NonNull;

import com.example.projeto_rejew.entity.Autor;
import com.example.projeto_rejew.entity.Chat;
import com.example.projeto_rejew.entity.Comentario;
import com.example.projeto_rejew.entity.Genero;
import com.example.projeto_rejew.entity.Livro;
import com.example.projeto_rejew.entity.Mensagem;
import com.example.projeto_rejew.entity.Usuario;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


public class APIConnection {

    interface RequestUser {

        ////////////////AUTORES

        // listar autores
        @GET("autores")
        Call<List<Autor>> listarTodosAutores();

        // Buscar autor id
        @GET("autores/{id}")
        Call<Autor> buscarAutoresPorId(@Path("id") Long id);

        // Buscar autor
        @GET("autores/usuarios/{id}")
        Call<List<Autor>> buscarAutorIDusuario(@Path("id") Long id);

        // Salvar autoe
        @POST("autores")
        Call<Autor> tornarAutor(@Body Autor autor);

        // Atualizar autor
        @PUT("autores/{id}")
        Call<Autor> atualizarAutor(@Path("id") Long id, @Body Autor autor);

        // Deletar autor
        @DELETE("autores/{id}")
        Call<Void> deletarAutor(@Path("id") Long id);

        //////////////// GENEROS

        @GET("/generos")
        Call<List<Genero>> listarTodosGeneros();

        @GET("/generos/{id}")
        Call<Genero> buscarGeneroPorId(@Path("id") Long id);

        @GET("/generos/genero/{genero}")
        Call<List<Genero>> buscarGeneroPorNome(@Path("genero") String genero);

        @POST("/generos")
        Call<Genero> salvarGenero(@Body Genero genero);

        @PUT("/generos/{id}")
        Call<Genero> atualizarGenero(@Path("id") Long id, @Body Genero genero);

        @DELETE("/generos/{id}")
        Call<Void> deletarGenero(@Path("id") Long id);

        /////////////LIVROS

        @GET("/livros")
        Call<List<Livro>> listarTodosLivros();

        @GET("/livros/{isbn}")
        Call<Livro> buscarLivroPorId(@Path("isbn") Long isbn);

        @GET("/livros/nome/{nome}")
        Call<List<Livro>> buscarLivroPorNome(@Path("nome") String nome);

        @GET("/livros/autor/{autor}")
        Call<List<Livro>> buscarLivroPorAutor(@Path("autor") String autor);

        @POST("/livros")
        @Multipart
        Call<String> adicionarLivro(
                @Part("nomeLivro") RequestBody nomeLivro,
                @Part("autorLivro") RequestBody autorLivro,
                @Part("sinopseLivro") RequestBody sinopseLivro,
                @Part("numeroPag") RequestBody numeroPag,
                @Part("anoLancamento") RequestBody anoLancamento,
                @Part("generoLivro") RequestBody generoLivro,
                @Part("corPrimaria") RequestBody corPrimaria,
                @Part("caminhoImgCapa")MultipartBody.Part caminhoImgCapa);

        @PUT("/livros/{isbn}")
        @Multipart
        Call<Livro> atualizarLivro(
                @Path("isbn") Long isbn,
                @Part("nomeLivro") RequestBody nomeLivro,
                @Part("autorLivro") RequestBody autorLivro,
                @Part("sinopseLivro") RequestBody sinopseLivro,
                @Part("numeroPag") RequestBody numeroPag,
                @Part("anoLancamento") RequestBody anoLancamento,
                @Part("generoLivro") RequestBody generoLivro,
                @Part("corPrimaria") RequestBody corPrimaria,
                @Part("caminhoImgCapa")MultipartBody.Part caminhoImgCapa);

        @DELETE("/livros/{isbn}")
        Call<String> deletarLivro(@Path("isbn") Long isbn);


        ///////////CHAT


    }

    String urlBase = "https://127.0.0.1:3306";
    RequestUser requestUser;

    public APIConnection() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        requestUser = retrofit.create(RequestUser.class);
    }

    ////////////////////////////////////////////////INTERFACES CALLBACK
    public interface AutorCallback {
        void onSuccess(Autor autor);
        void onFailure(Throwable t);}

    public interface GeneroCallback {
        void onSuccess(Genero genero);
        void onFailure(Throwable t);
    }

    public interface ChatCallback {
        void onSuccess(Chat chat);
        void onFailure(Throwable t);
    }

    public interface ComentarioCallback {
        void onSuccess(Comentario comentario);
        void onFailure(Throwable t);
    }

    public interface LivroCallback {
        void onSuccess(Livro livro);
        void onFailure(Throwable t);
    }

    public interface MensagemCallback {
        void onSuccess(Mensagem mensagem);
        void onFailure(Throwable t);
    }

    public interface UsuarioCallback {
        void onSuccess(Usuario usuario);
        void onFailure(Throwable t);
    }

/////////////////////////////////////FUNÇÔES/////////////////////////////////////////////////
public void listarTodosAutores(AutorCallback userDataCallback) {
    requestUser.listarTodosAutores().enqueue(new Callback<List<Autor>>() {
        @Override
        public void onResponse(Call<List<Autor>> call, Response<List<Autor>> response) {
            if (response.isSuccessful()) {
                userDataCallback.onSuccess((Autor) response.body());
            } else {
                userDataCallback.onFailure(new Exception("Request failed with status: " + response.code()));
            }
        }

        @Override
        public void onFailure(Call<List<Autor>> call, Throwable t) {
            userDataCallback.onFailure(new Exception("Request failed", t));
        }
    });
}

    public void buscarAutorIDusuario(Long id, AutorCallback userDataCallback) {
        requestUser.buscarAutorIDusuario(id).enqueue(new Callback<List<Autor>>() {
            @Override
            public void onResponse(Call<List<Autor>>    call, Response<List<Autor>> response) {
                if (response.isSuccessful()) {
                    userDataCallback.onSuccess((Autor) response.body());
                } else {
                    userDataCallback.onFailure(new Exception("Request failed with status: " + response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Autor>> call, Throwable t) {
                userDataCallback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void tornarAutor(Autor autor, AutorCallback userDataCallback) {
        requestUser.tornarAutor(autor).enqueue(new Callback<Autor>() {
            @Override
            public void onResponse(Call<Autor> call, Response<Autor> response) {
                if (response.isSuccessful()) {
                    userDataCallback.onSuccess(response.body());
                } else {
                    userDataCallback.onFailure(new Exception("Request failed with status: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Autor> call, Throwable t) {
                userDataCallback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void atualizarAutor(Long id, Autor autor, AutorCallback userDataCallback) {
        requestUser.atualizarAutor(id, autor).enqueue(new Callback<Autor>() {
            @Override
            public void onResponse(Call<Autor> call, Response<Autor> response) {
                if (response.isSuccessful()) {
                    userDataCallback.onSuccess(response.body());
                } else {
                    userDataCallback.onFailure(new Exception("Request failed with status: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Autor> call, Throwable t) {
                userDataCallback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void deletarAutor(Long id, AutorCallback userDataCallback) {
        requestUser.deletarAutor(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    userDataCallback.onSuccess(null);
                } else {
                    userDataCallback.onFailure(new Exception("Request failed with status: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                userDataCallback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    //////////////////////////////////////////////////GENEROS

    public void listarTodosGeneros(GeneroCallback callback) {
        requestUser.listarTodosGeneros().enqueue(new Callback<List<Genero>>() {
            @Override
            public void onResponse(Call<List<Genero>> call, Response<List<Genero>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess((Genero) response.body());
                } else {
                    callback.onFailure(new Exception("Request failed with status: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Genero>> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void buscarGeneroPorId(Long id, GeneroCallback callback) {
        requestUser.buscarGeneroPorId(id).enqueue(new Callback<Genero>() {
            @Override
            public void onResponse(Call<Genero> call, Response<Genero> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Request failed with status: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Genero> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void buscarGeneroPorNome(String nome, GeneroCallback callback) {
        requestUser.buscarGeneroPorNome(nome).enqueue(new Callback<List<Genero>>() {
            @Override
            public void onResponse(Call<List<Genero>> call, Response<List<Genero>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess((Genero) response.body());
                } else {
                    callback.onFailure(new Exception("Request failed with status: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Genero>> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void salvarGenero(Genero genero, GeneroCallback callback) {
        requestUser.salvarGenero(genero).enqueue(new Callback<Genero>() {
            @Override
            public void onResponse(Call<Genero> call, Response<Genero> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Request failed with status: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Genero> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void atualizarGenero(Long id, Genero genero, GeneroCallback callback) {
        requestUser.atualizarGenero(id, genero).enqueue(new Callback<Genero>() {
            @Override
            public void onResponse(Call<Genero> call, Response<Genero> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Request failed with status: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Genero> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void deletarGenero(Long id, GeneroCallback callback) {
        requestUser.deletarGenero(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onFailure(new Exception("Request failed with status: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    ////////////////////////////////////////////LIVROS

    public void listarTodosLivros(LivroCallback callback) {
        requestUser.listarTodosLivros().enqueue(new Callback<List<Livro>>() {
            @Override
            public void onResponse(Call<List<Livro>> call, Response<List<Livro>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess((Livro) response.body());
                } else {
                    callback.onFailure(new Exception("Request failed with status: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Livro>> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void buscarLivroPorId(Long isbn, LivroCallback callback) {
        requestUser.buscarLivroPorId(isbn).enqueue(new Callback<Livro>() {
            @Override
            public void onResponse(Call<Livro> call, Response<Livro> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Request failed with status: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Livro> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void buscarLivroPorNome(String nome, LivroCallback callback) {
        requestUser.buscarLivroPorNome(nome).enqueue(new Callback<List<Livro>>() {
            @Override
            public void onResponse(Call<List<Livro>> call, Response<List<Livro>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess((Livro) response.body());
                } else {
                    callback.onFailure(new Exception("Request failed with status: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Livro>> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void buscarLivroPorAutor(String autor, LivroCallback callback) {
        requestUser.buscarLivroPorAutor(autor).enqueue(new Callback<List<Livro>>() {
            @Override
            public void onResponse(Call<List<Livro>> call, Response<List<Livro>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess((Livro) response.body());
                } else {
                    callback.onFailure(new Exception("Request failed with status: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Livro>> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void adicionarLivro(
            String nomeLivro, String autorLivro, String sinopseLivro,
            int numeroPag, int anoLancamento, String generoLivro,
            String corPrimaria, MultipartBody.Part caminhoImgCapa,
            LivroCallback callback) {

        RequestBody nomeLivroBody = RequestBody.create(MultipartBody.FORM, nomeLivro);
        RequestBody autorLivroBody = RequestBody.create(MultipartBody.FORM, autorLivro);
        RequestBody sinopseLivroBody = RequestBody.create(MultipartBody.FORM, sinopseLivro);
        RequestBody numeroPagBody = RequestBody.create(MultipartBody.FORM, String.valueOf(numeroPag));
        RequestBody anoLancamentoBody = RequestBody.create(MultipartBody.FORM, String.valueOf(anoLancamento));
        RequestBody generoLivroBody = RequestBody.create(MultipartBody.FORM, generoLivro);
        RequestBody corPrimariaBody = RequestBody.create(MultipartBody.FORM, corPrimaria);

        requestUser.adicionarLivro(nomeLivroBody, autorLivroBody, sinopseLivroBody, numeroPagBody,
                anoLancamentoBody, generoLivroBody, corPrimariaBody, caminhoImgCapa).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                   // callback.onSuccess(response.body();
                } else {
                    callback.onFailure(new Exception("Request failed with status: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void atualizarLivro(
            Long isbn, String nomeLivro, String autorLivro, String sinopseLivro,
            int numeroPag, int anoLancamento, String generoLivro,
            String corPrimaria, MultipartBody.Part caminhoImgCapa,
            LivroCallback callback) {

        RequestBody nomeLivroBody = RequestBody.create(MultipartBody.FORM, nomeLivro);
        RequestBody autorLivroBody = RequestBody.create(MultipartBody.FORM, autorLivro);
        RequestBody sinopseLivroBody = RequestBody.create(MultipartBody.FORM, sinopseLivro);
        RequestBody numeroPagBody = RequestBody.create(MultipartBody.FORM, String.valueOf(numeroPag));
        RequestBody anoLancamentoBody = RequestBody.create(MultipartBody.FORM, String.valueOf(anoLancamento));
        RequestBody generoLivroBody = RequestBody.create(MultipartBody.FORM, generoLivro);
        RequestBody corPrimariaBody = RequestBody.create(MultipartBody.FORM, corPrimaria);

        requestUser.atualizarLivro(isbn, nomeLivroBody, autorLivroBody, sinopseLivroBody, numeroPagBody,
                anoLancamentoBody, generoLivroBody, corPrimariaBody, caminhoImgCapa).enqueue(new Callback<Livro>() {
            @Override
            public void onResponse(Call<Livro> call, Response<Livro> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Request failed with status: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Livro> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void deletarLivro(Long isbn, LivroCallback callback) {
        requestUser.deletarLivro(isbn).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onFailure(new Exception("Request failed with status: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

}
