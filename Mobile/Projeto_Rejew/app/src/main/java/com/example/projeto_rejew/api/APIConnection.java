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

        @GET("autores")
        Call<List<Autor>> listarTodosAutores();

        @GET("autores/{id}")
        Call<Autor> buscarAutorPorId(@Path("id") Long id);

        @GET("autores/usuarios/{id}")
        Call<List<Autor>> buscarAutorIDusuario(@Path("id") Long id);

        @POST("autores")
        Call<Autor> tornarAutor(@Body Autor autor);

        @PUT("autores/{id}")
        Call<Autor> atualizarAutor(@Path("id") Long id, @Body Autor autor);

        @DELETE("autores/{id}")
        Call<Void> deletarAutor(@Path("id") Long id);

        //////////////// GENEROS

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

        /////////////LIVROS

        @GET("livros")
        Call<List<Livro>> listarTodosLivros();

        @GET("livros/{isbn}")
        Call<Livro> buscarLivroPorId(@Path("isbn") Long isbn);

        @GET("livros/nome/{nome}")
        Call<List<Livro>> buscarLivroPorNome(@Path("nome") String nome);

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

        ///////////CHAT

        @GET("chats")
        Call<List<Chat>> listarTodosChats();

        @GET("chats/{id}")
        Call<Chat> buscarChatPorId(@Path("id") Long id);

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

        ///////////////////////////////////////USUARIOS

        @GET("usuarios")
        Call<List<Usuario>> listarTodosUsuarios();

        @GET("usuarios/{id}")
        Call<Usuario> buscarUsuarioPorId(@Path("id") Long id);

        @GET("usuarios/nome/{nome}")
        Call<List<Usuario>> buscarUsuarioPorNome(@Path("nome") String nome);

        @GET("usuarios/perfil/{nome}")
        Call<List<Usuario>> buscarUsuarioPorNomePerfil(@Path("nome") String nome);

        @Multipart
        @POST("usuarios")
        Call<Usuario> adicionarUsuario(
                @Part("nomeUsuario") RequestBody nomeUsuario,
                @Part("nomePerfil") RequestBody nomePerfil,
                @Part("emailEntrada") RequestBody emailEntrada,
                @Part("senhaEntrada") RequestBody senhaEntrada,
                @Part("dataNascimento") RequestBody dataNascimento,
                @Part MultipartBody.Part caminhoImagem,
                @Part MultipartBody.Part caminhoImagemFundo,
                @Part("recadoPerfil") RequestBody recadoPerfil);

        @Multipart
        @PUT("usuarios/{idUsuario}")
        Call<Usuario> atualizarUsuario(
                @Path("idUsuario") Long idUsuario,
                @Part("nomeUsuario") RequestBody nomeUsuario,
                @Part("nomePerfil") RequestBody nomePerfil,
                @Part("emailEntrada") RequestBody emailEntrada,
                @Part("dataNascimento") RequestBody dataNascimento,
                @Part MultipartBody.Part caminhoImagem,
                @Part MultipartBody.Part caminhoImagemFundo,
                @Part("recadoPerfil") RequestBody recadoPerfil);

        @DELETE("usuarios/{id}")
        Call<Void> deletarUsuario(@Path("id") Long id);

        ///////////////////////////////////////MENSAGEM

        @GET("mensagens")
        Call<List<Mensagem>> listarTodasMensagens();

        @GET("mensagens/{id}")
        Call<Mensagem> buscarMensagemPorId(@Path("id") Long id);

        @GET("mensagens/chat/{chat}")
        Call<List<Mensagem>> buscarMensagensPorChat(@Path("chat") String chat);

        @GET("mensagens/usuario/{usuario}")
        Call<List<Mensagem>> buscarMensagensPorUsuario(@Path("usuario") String usuario);

        @POST("mensagens")
        Call<Mensagem> salvarMensagem(@Body Mensagem mensagem);

        @PUT("mensagens/{id}")
        Call<Mensagem> atualizarMensagem(@Path("id") Long id, @Body Mensagem mensagem);

        @DELETE("mensagens/{id}")
        Call<Void> deletarMensagem(@Path("id") Long id);

        ////////////////////////////////COMENTARIO

        @GET("comentarios")
        Call<List<Comentario>> listarComentarios();

        @GET("comentarios/{id}")
        Call<Comentario> buscarComentarioPorId(@Path("id") Long id);

        @GET("comentarios/usuario/{usuarioComent}")
        Call<List<Comentario>> buscarComentariosPorUsuario(@Path("usuarioComent") String usuarioComent);

        @GET("comentarios/livro/{idLivro}")
        Call<List<Comentario>> buscarComentariosPorLivro(@Path("idLivro") Long idLivro);

        @PUT("comentarios/{id}")
        Call<Comentario> atualizarComentario(@Path("id") Long id, @Body Comentario comentario);

        @DELETE("comentarios/{id}")
        Call<Void> deletarComentario(@Path("id") Long id);
    }

    String urlBase = "https://127.0.0.1:3306/"; // Certifique-se de incluir a barra final
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
        void onSuccessList(List<Autor> autorL);
        void onFailure(Throwable t);
    }

    public interface GeneroCallback {
        void onSuccess(Genero genero);
        void onSuccessList(List<Genero> generoL);
        void onFailure(Throwable t);
    }

    public interface ChatCallback {
        void onSuccess(Chat chat);
        void onSuccessList(List<Chat> ChatL);
        void onFailure(Throwable t);
    }

    public interface ComentarioCallback {
        void onSuccess(Comentario comentario);
        void onSuccessList(List<Comentario> ComentarioL);
        void onFailure(Throwable t);
    }

    public interface LivroCallback {
        void onSuccess(Livro livro);
        void onSuccessList(List<Livro> LivroL);
        void onFailure(Throwable t);
    }

    public interface MensagemCallback {
        void onSuccess(Mensagem mensagem);
        void onSuccessList(List<Mensagem> MensagemL);
        void onFailure(Throwable t);
    }

    public interface UsuarioCallback {
        void onSuccess(Usuario usuario);
        void onSuccessList(List<Usuario> UsuarioL);
        void onFailure(Throwable t);
    }

/////////////////////////////////////FUNÇÔES////////////////////////////////////////////////

    ///////////////////////////////////AUTORES
    public void listarTodosAutores(AutorCallback callback) {
        requestUser.listarTodosAutores().enqueue(new Callback<List<Autor>>() {
            @Override
            public void onResponse(Call<List<Autor>> call, Response<List<Autor>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccessList(response.body());
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Autor>> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void buscarAutorIDusuario(Long id, AutorCallback callback) {
        requestUser.buscarAutorIDusuario(id).enqueue(new Callback<List<Autor>>() {
            @Override
            public void onResponse(Call<List<Autor>> call, Response<List<Autor>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccessList(response.body());
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Autor>> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void tornarAutor(Autor autor, AutorCallback callback) {
        requestUser.tornarAutor(autor).enqueue(new Callback<Autor>() {
            @Override
            public void onResponse(Call<Autor> call, Response<Autor> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Autor> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void atualizarAutor(Long id, Autor autor, AutorCallback callback) {
        requestUser.atualizarAutor(id, autor).enqueue(new Callback<Autor>() {
            @Override
            public void onResponse(Call<Autor> call, Response<Autor> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Autor> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void deletarAutor(Long id, AutorCallback callback) {
        requestUser.deletarAutor(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    //////////////////////////////////////////////////GENEROS

    public void listarTodosGeneros(GeneroCallback callback) {
        requestUser.listarTodosGeneros().enqueue(new Callback<List<Genero>>() {
            @Override
            public void onResponse(Call<List<Genero>> call, Response<List<Genero>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccessList(response.body());
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
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
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
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
                    callback.onSuccessList( response.body());
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
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
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
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
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
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
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
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
                    callback.onSuccessList(response.body());
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
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
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
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
                    callback.onSuccessList( response.body());
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
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
                    callback.onSuccessList(response.body());
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Livro>> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void adicionarLivro(RequestBody nomeLivro, RequestBody autorLivro, RequestBody sinopseLivro, RequestBody numeroPag, RequestBody anoLancamento, RequestBody generoLivro, RequestBody corPrimaria, MultipartBody.Part caminhoImgCapa, LivroCallback callback) {
        requestUser.adicionarLivro(nomeLivro, autorLivro, sinopseLivro, numeroPag, anoLancamento, generoLivro, corPrimaria, caminhoImgCapa).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void atualizarLivro(Long isbn, RequestBody nomeLivro, RequestBody autorLivro, RequestBody sinopseLivro, RequestBody numeroPag, RequestBody anoLancamento, RequestBody generoLivro, RequestBody corPrimaria, MultipartBody.Part caminhoImgCapa, LivroCallback callback) {
        requestUser.atualizarLivro(isbn, nomeLivro, autorLivro, sinopseLivro, numeroPag, anoLancamento, generoLivro, corPrimaria, caminhoImgCapa).enqueue(new Callback<Livro>() {
            @Override
            public void onResponse(Call<Livro> call, Response<Livro> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
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
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }


    //////////////////////////////CHATS
    public void listarTodosChats(ChatCallback callback) {
        requestUser.listarTodosChats().enqueue(new Callback<List<Chat>>() {
            @Override
            public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess((Chat) response.body());
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void buscarChatPorId(Long id, ChatCallback callback) {
        requestUser.buscarChatPorId(id).enqueue(new Callback<Chat>() {
            @Override
            public void onResponse(Call<Chat> call, Response<Chat> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Chat> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void buscarChatPorGenero(String genero, ChatCallback callback) {
        requestUser.buscarChatPorGenero(genero).enqueue(new Callback<List<Chat>>() {
            @Override
            public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess((Chat) response.body());
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void adicionarChat(RequestBody generoChat, MultipartBody.Part caminhoImagemLogo, MultipartBody.Part caminhoImagemFundoChat, ChatCallback callback) {
        requestUser.adicionarChat(generoChat, caminhoImagemLogo, caminhoImagemFundoChat).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void atualizarChat(Long id, RequestBody generoChat, MultipartBody.Part caminhoImagemLogo, MultipartBody.Part caminhoImagemFundoChat, ChatCallback callback) {
        requestUser.atualizarChat(id, generoChat, caminhoImagemLogo, caminhoImagemFundoChat).enqueue(new Callback<Chat>() {
            @Override
            public void onResponse(Call<Chat> call, Response<Chat> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Chat> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void deletarChat(Long id, ChatCallback callback) {
        requestUser.deletarChat(id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    /////////////////////////////////////////////////////USUARIOS
    public void listarTodosUsuarios(UsuarioCallback callback) {
        requestUser.listarTodosUsuarios().enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess((Usuario) response.body());
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void buscarUsuarioPorId(Long id, UsuarioCallback callback) {
        requestUser.buscarUsuarioPorId(id).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void buscarUsuarioPorNome(String nome, UsuarioCallback callback) {
        requestUser.buscarUsuarioPorNome(nome).enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess((Usuario) response.body());
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void buscarUsuarioPorNomePerfil(String nome, UsuarioCallback callback) {
        requestUser.buscarUsuarioPorNomePerfil(nome).enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess((Usuario) response.body());
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void adicionarUsuario(RequestBody nomeUsuario, RequestBody nomePerfil, RequestBody emailEntrada, RequestBody senhaEntrada, RequestBody dataNascimento, MultipartBody.Part caminhoImagem, MultipartBody.Part caminhoImagemFundo, RequestBody recadoPerfil, UsuarioCallback callback) {
        requestUser.adicionarUsuario(nomeUsuario, nomePerfil, emailEntrada, senhaEntrada, dataNascimento, caminhoImagem, caminhoImagemFundo, recadoPerfil).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void atualizarUsuario(Long idUsuario, RequestBody nomeUsuario, RequestBody nomePerfil, RequestBody emailEntrada, RequestBody dataNascimento, MultipartBody.Part caminhoImagem, MultipartBody.Part caminhoImagemFundo, RequestBody recadoPerfil, UsuarioCallback callback) {
        requestUser.atualizarUsuario(idUsuario, nomeUsuario, nomePerfil, emailEntrada, dataNascimento, caminhoImagem, caminhoImagemFundo, recadoPerfil).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void deletarUsuario(Long id, UsuarioCallback callback) {
        requestUser.deletarUsuario(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    ////////////////////////////////////////MENSAGENS
    public void listarTodasMensagens(MensagemCallback callback) {
        requestUser.listarTodasMensagens().enqueue(new Callback<List<Mensagem>>() {
            @Override
            public void onResponse(Call<List<Mensagem>> call, Response<List<Mensagem>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess((Mensagem) response.body());
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Mensagem>> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void buscarMensagemPorId(Long id, MensagemCallback callback) {
        requestUser.buscarMensagemPorId(id).enqueue(new Callback<Mensagem>() {
            @Override
            public void onResponse(Call<Mensagem> call, Response<Mensagem> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Mensagem> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void buscarMensagensPorChat(String chat, MensagemCallback callback) {
        requestUser.buscarMensagensPorChat(chat).enqueue(new Callback<List<Mensagem>>() {
            @Override
            public void onResponse(Call<List<Mensagem>> call, Response<List<Mensagem>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Mensagem>> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void buscarMensagensPorUsuario(String usuario, MensagemCallback callback) {
        requestUser.buscarMensagensPorUsuario(usuario).enqueue(new Callback<List<Mensagem>>() {
            @Override
            public void onResponse(Call<List<Mensagem>> call, Response<List<Mensagem>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Mensagem>> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void salvarMensagem(Mensagem mensagem, MensagemCallback callback) {
        requestUser.salvarMensagem(mensagem).enqueue(new Callback<Mensagem>() {
            @Override
            public void onResponse(Call<Mensagem> call, Response<Mensagem> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Mensagem> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void atualizarMensagem(Long id, Mensagem mensagem, MensagemCallback callback) {
        requestUser.atualizarMensagem(id, mensagem).enqueue(new Callback<Mensagem>() {
            @Override
            public void onResponse(Call<Mensagem> call, Response<Mensagem> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Mensagem> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void deletarMensagem(Long id, MensagemCallback callback) {
        requestUser.deletarMensagem(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    /////////////////////////////////////////COMENTARIOS
    public void listarComentarios(ComentarioCallback callback) {
        requestUser.listarComentarios().enqueue(new Callback<List<Comentario>>() {
            @Override
            public void onResponse(Call<List<Comentario>> call, Response<List<Comentario>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Comentario>> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void buscarComentarioPorId(Long id, ComentarioCallback callback) {
        requestUser.buscarComentarioPorId(id).enqueue(new Callback<Comentario>() {
            @Override
            public void onResponse(Call<Comentario> call, Response<Comentario> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Comentario> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void buscarComentariosPorUsuario(String usuarioComent, ComentarioCallback callback) {
        requestUser.buscarComentariosPorUsuario(usuarioComent).enqueue(new Callback<List<Comentario>>() {
            @Override
            public void onResponse(Call<List<Comentario>> call, Response<List<Comentario>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Comentario>> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void buscarComentariosPorLivro(Long idLivro, ComentarioCallback callback) {
        requestUser.buscarComentariosPorLivro(idLivro).enqueue(new Callback<List<Comentario>>() {
            @Override
            public void onResponse(Call<List<Comentario>> call, Response<List<Comentario>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Comentario>> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void atualizarComentario(Long id, Comentario comentario, ComentarioCallback callback) {
        requestUser.atualizarComentario(id, comentario).enqueue(new Callback<Comentario>() {
            @Override
            public void onResponse(Call<Comentario> call, Response<Comentario> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Comentario> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

    public void deletarComentario(Long id, ComentarioCallback callback) {
        requestUser.deletarComentario(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onFailure(new Exception("Falha na requisição dos dados, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onFailure(new Exception("Request failed", t));
            }
        });
    }

}
