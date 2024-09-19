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

    public interface RequestUser {

        //////////////// AUTORES

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

        //////////////// GÊNEROS

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

        ///////////// LIVROS

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

        ///////////////////////////// CHAT

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

        /////////////////////////////////////// USUÁRIOS

        @GET("usuarios")
        Call<List<Usuario>> listarTodosUsuarios();

        @GET("usuarios/{email}")
        Call<Usuario> buscarUsuarioPorEmail(@Path("email") String email);

        @GET("usuarios/email/{emailEntrada}")
        Call<List<Usuario>> buscarUsuarioPorEmailLike(@Path("emailEntrada") String emailEntrada);

        @GET("usuarios/nome/{nome}")
        Call<List<Usuario>> buscarUsuarioPorNome(@Path("nome") String nome);

        @GET("usuarios/perfil/{nome}")
        Call<List<Usuario>> buscarUsuarioPorNomePerfil(@Path("nome") String nome);

        @Multipart
        @POST("usuarios")
        Call<String> criarUsuario(
                @Part("nomeUsuario") RequestBody nomeUsuario,
                @Part("nomePerfil") RequestBody nomePerfil,
                @Part("emailEntrada") RequestBody emailEntrada,
                @Part("senhaEntrada") RequestBody senhaEntrada,
                @Part("dataNascimento") RequestBody dataNascimento,
                @Part MultipartBody.Part caminhoImagem,
                @Part MultipartBody.Part caminhoImagemFundo,
                @Part("recadoPerfil") RequestBody recadoPerfil);

        @Multipart
        @PUT("usuarios/{emailUsuario}")
        Call<Usuario> atualizarUsuario(
                @Path("emailUsuario") String emailUsuario,
                @Part("nomeUsuario") RequestBody nomeUsuario,
                @Part("nomePerfil") RequestBody nomePerfil,
                @Part("emailEntrada") RequestBody emailEntrada,
                @Part("dataNascimento") RequestBody dataNascimento,
                @Part MultipartBody.Part caminhoImagem,
                @Part MultipartBody.Part caminhoImagemFundo,
                @Part("recadoPerfil") RequestBody recadoPerfil);

        @DELETE("usuarios/{emailUsuario}")
        Call<Void> deletarUsuario(@Path("emailUsuario") String emailUsuario);

        /////////////////////////////////////// MENSAGEM

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

        //////////////////////////////// COMENTÁRIO

        @GET("comentarios")
        Call<List<Comentario>> listarComentarios();

        @GET("comentarios/{id}")
        Call<Comentario> buscarComentarioPorId(@Path("id") Long id);

        @GET("comentarios/usuario/{usuarioComent}")
        Call<List<Comentario>> buscarComentariosPorUsuario(@Path("usuarioComent") String usuarioComent);

        @GET("comentarios/livro/{idLivro}")
        Call<List<Comentario>> buscarComentariosPorLivro(@Path("idLivro") Long idLivro);

        @POST("comentarios")
        Call<Comentario> adicionarComentario(@Body Comentario comentario);

        @PUT("comentarios/{id}")
        Call<Comentario> atualizarComentario(@Path("id") Long id, @Body Comentario comentario);

        @DELETE("comentarios/{id}")
        Call<Void> deletarComentario(@Path("id") Long id);

    }

    String urlBase = "https://127.0.0.1:3306/";
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
                    callback.onFailure(new Exception("Falha ao listar autores, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Autor>> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de autores", t));
            }
        });
    }

    public void buscarAutorPorId(Long id, AutorCallback callback) {
        requestUser.buscarAutorPorId(id).enqueue(new Callback<Autor>() {
            @Override
            public void onResponse(Call<Autor> call, Response<Autor> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Falha ao buscar autor, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Autor> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de autor", t));
            }
        });
    }

    public void buscarAutorPorEmailUsuario(String email, AutorCallback callback) {
        requestUser.buscarAutorEmailUsuario(email).enqueue(new Callback<List<Autor>>() {
            @Override
            public void onResponse(Call<List<Autor>> call, Response<List<Autor>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccessList(response.body());
                } else {
                    callback.onFailure(new Exception("Falha ao buscar autor por email, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Autor>> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de autor por email", t));
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
                    callback.onFailure(new Exception("Falha ao tornar-se autor, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Autor> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição para tornar-se autor", t));
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
                    callback.onFailure(new Exception("Falha ao atualizar autor, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Autor> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de atualização do autor", t));
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
                    callback.onFailure(new Exception("Falha ao deletar autor, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de deleção de autor", t));
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
                    callback.onFailure(new Exception("Falha ao listar gêneros, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Genero>> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de gêneros", t));
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
                    callback.onFailure(new Exception("Falha ao buscar gênero, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Genero> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de gênero", t));
            }
        });
    }

    public void buscarGeneroPorNome(String nome, GeneroCallback callback) {
        requestUser.buscarGeneroPorNome(nome).enqueue(new Callback<List<Genero>>() {
            @Override
            public void onResponse(Call<List<Genero>> call, Response<List<Genero>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccessList(response.body());
                } else {
                    callback.onFailure(new Exception("Falha ao buscar gênero por nome, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Genero>> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de gênero por nome", t));
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
                    callback.onFailure(new Exception("Falha ao salvar gênero, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Genero> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição para salvar gênero", t));
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
                    callback.onFailure(new Exception("Falha ao atualizar gênero, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Genero> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de atualização de gênero", t));
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
                    callback.onFailure(new Exception("Falha ao deletar gênero, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de deleção de gênero", t));
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
                    callback.onFailure(new Exception("Falha ao listar livros, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Livro>> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de livros", t));
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
                    callback.onFailure(new Exception("Falha ao buscar livro por ISBN, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Livro> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de livro por ISBN", t));
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
                    callback.onFailure(new Exception("Falha ao buscar livros por autor, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Livro>> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de livros por autor", t));
            }
        });
    }

    public void buscarLivroPorNome(String nome, LivroCallback callback) {
        requestUser.buscarLivroPorNome(nome).enqueue(new Callback<List<Livro>>() {
            @Override
            public void onResponse(Call<List<Livro>> call, Response<List<Livro>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccessList(response.body());
                } else {
                    callback.onFailure(new Exception("Falha ao buscar livros por nome, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Livro>> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de livros por nome", t));
            }
        });
    }



    public void adicionarLivro(RequestBody nomeLivro, RequestBody autorLivro, RequestBody sinopseLivro,
                               RequestBody numeroPag, RequestBody anoLancamento, RequestBody generoLivro,
                               RequestBody corPrimaria, MultipartBody.Part caminhoImgCapa, LivroCallback callback) {
        requestUser.adicionarLivro(nomeLivro, autorLivro, sinopseLivro, numeroPag, anoLancamento, generoLivro, corPrimaria, caminhoImgCapa)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            callback.onSuccess(null);
                        } else {
                            callback.onFailure(new Exception("Falha ao adicionar livro, ERRO: " + response.code()));
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        callback.onFailure(new Exception("Erro na requisição de adição de livro", t));
                    }
                });
    }

    public void atualizarLivro(Long isbn, RequestBody nomeLivro, RequestBody autorLivro,
                               RequestBody sinopseLivro, RequestBody numeroPag, RequestBody anoLancamento,
                               RequestBody generoLivro, RequestBody corPrimaria, MultipartBody.Part caminhoImgCapa, LivroCallback callback) {
        requestUser.atualizarLivro(isbn, nomeLivro, autorLivro, sinopseLivro, numeroPag, anoLancamento, generoLivro, corPrimaria, caminhoImgCapa)
                .enqueue(new Callback<Livro>() {
                    @Override
                    public void onResponse(Call<Livro> call, Response<Livro> response) {
                        if (response.isSuccessful()) {
                            callback.onSuccess(response.body());
                        } else {
                            callback.onFailure(new Exception("Falha ao atualizar livro, ERRO: " + response.code()));
                        }
                    }

                    @Override
                    public void onFailure(Call<Livro> call, Throwable t) {
                        callback.onFailure(new Exception("Erro na requisição de atualização de livro", t));
                    }
                });
    }


    public void deletarLivro(Long isbn, LivroCallback callback) {
        requestUser.deletarLivro(isbn).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onFailure(new Exception("Falha ao deletar livro, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de deleção de livro", t));
            }
        });
    }



    //////////////////////////////CHATS

    public void listarTodosChats(ChatCallback callback) {
        requestUser.listarTodosChats().enqueue(new Callback<List<Chat>>() {
            @Override
            public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccessList(response.body());
                } else {
                    callback.onFailure(new Exception("Falha ao listar chats, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de chats", t));
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
                    callback.onFailure(new Exception("Falha ao buscar chat por ID, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Chat> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de chat por ID", t));
            }
        });
    }

    public void buscarChatPorGenero(String genero, ChatCallback callback) {
        requestUser.buscarChatPorGenero(genero).enqueue(new Callback<List<Chat>>() {
            @Override
            public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccessList(response.body());
                } else {
                    callback.onFailure(new Exception("Falha ao buscar chats por gênero, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de chats por gênero", t));
            }
        });
    }


    public void adicionarChat(RequestBody generoChat, MultipartBody.Part caminhoImagemLogo,
                              MultipartBody.Part caminhoImagemFundoChat, ChatCallback callback) {
        requestUser.adicionarChat(generoChat, caminhoImagemLogo, caminhoImagemFundoChat).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onFailure(new Exception("Falha ao adicionar chat, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de adição de chat", t));
            }
        });
    }

    public void atualizarChat(Long id, RequestBody generoChat, MultipartBody.Part caminhoImagemLogo,
                              MultipartBody.Part caminhoImagemFundoChat, ChatCallback callback) {
        requestUser.atualizarChat(id, generoChat, caminhoImagemLogo, caminhoImagemFundoChat).enqueue(new Callback<Chat>() {
            @Override
            public void onResponse(Call<Chat> call, Response<Chat> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Falha ao atualizar chat, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Chat> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de atualização de chat", t));
            }
        });
    }

    public void deletarChat(Long id, ChatCallback callback) {
        requestUser.deletarChat(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onFailure(new Exception("Falha ao deletar chat, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de deleção de chat", t));
            }
        });
    }





    /////////////////////////////////////////////////////USUARIOS

    public void listarTodosUsuarios(UsuarioCallback callback) {
        requestUser.listarTodosUsuarios().enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccessList(response.body());
                } else {
                    callback.onFailure(new Exception("Falha ao listar usuários, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de usuários", t));
            }
        });
    }

    public void buscarUsuarioPorEmail(String email, UsuarioCallback callback) {
        requestUser.buscarUsuarioPorEmail(email).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Falha ao buscar usuário por email, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de usuário por email", t));
            }
        });
    }

    public void buscarUsuarioPorEmailLike(String emailEntrada, UsuarioCallback callback) {
        requestUser.buscarUsuarioPorEmailLike(emailEntrada).enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccessList(response.body());
                } else {
                    callback.onFailure(new Exception("Falha ao buscar usuários por email, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de usuários por email (LIKE)", t));
            }
        });
    }

    public void buscarUsuarioPorNome(String nome, UsuarioCallback callback) {
        requestUser.buscarUsuarioPorNome(nome).enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccessList(response.body());
                } else {
                    callback.onFailure(new Exception("Falha ao buscar usuários por nome, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de usuários por nome", t));
            }
        });
    }

    public void buscarUsuarioPorNomePerfil(String nome, UsuarioCallback callback) {
        requestUser .buscarUsuarioPorNomePerfil(nome).enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccessList(response.body());
                } else {
                    callback.onFailure(new Exception("Falha ao buscar usuários por nome de perfil, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de usuários por nome de perfil", t));
            }
        });
    }

    public void criarUsuario(RequestBody nomeUsuario, RequestBody nomePerfil, RequestBody emailEntrada,
                             RequestBody senhaEntrada, RequestBody dataNascimento, MultipartBody.Part caminhoImagem,
                             MultipartBody.Part caminhoImagemFundo, RequestBody recadoPerfil, UsuarioCallback callback) {
        requestUser.criarUsuario(nomeUsuario, nomePerfil, emailEntrada, senhaEntrada, dataNascimento, caminhoImagem, caminhoImagemFundo, recadoPerfil)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            callback.onSuccess(null);
                        } else {
                            callback.onFailure(new Exception("Falha ao criar usuário, ERRO: " + response.code()));
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        callback.onFailure(new Exception("Erro na requisição de criação de usuário", t));
                    }
                });
    }

    public void atualizarUsuario(String emailUsuario, RequestBody nomeUsuario, RequestBody nomePerfil,
                                 RequestBody emailEntrada, RequestBody dataNascimento, MultipartBody.Part caminhoImagem,
                                 MultipartBody.Part caminhoImagemFundo, RequestBody recadoPerfil, UsuarioCallback callback) {
        requestUser.atualizarUsuario(emailUsuario, nomeUsuario, nomePerfil, emailEntrada, dataNascimento, caminhoImagem, caminhoImagemFundo, recadoPerfil)
                .enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        if (response.isSuccessful()) {
                            callback.onSuccess(response.body());
                        } else {
                            callback.onFailure(new Exception("Falha ao atualizar usuário, ERRO: " + response.code()));
                        }
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {
                        callback.onFailure(new Exception("Erro na requisição de atualização de usuário", t));
                    }
                });
    }

    public void deletarUsuario(String emailUsuario, UsuarioCallback callback) {
        requestUser.deletarUsuario(emailUsuario).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onFailure(new Exception("Falha ao deletar usuário, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de deleção de usuário", t));
            }
        });
    }




    ////////////////////////////////////////MENSAGENS

    public void listarTodasMensagens(MensagemCallback callback) {
    requestUser.listarTodasMensagens().enqueue(new Callback<List<Mensagem>>() {
        @Override
        public void onResponse(Call<List<Mensagem>> call, Response<List<Mensagem>> response) {
            if (response.isSuccessful()) {
                callback.onSuccessList(response.body());
            } else {
                callback.onFailure(new Exception("Falha ao listar mensagens, ERRO: " + response.code()));
            }
        }

        @Override
        public void onFailure(Call<List<Mensagem>> call, Throwable t) {
            callback.onFailure(new Exception("Erro na requisição de mensagens", t));
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
                callback.onFailure(new Exception("Falha ao buscar mensagem, ERRO: " + response.code()));
            }
        }

        @Override
        public void onFailure(Call<Mensagem> call, Throwable t) {
            callback.onFailure(new Exception("Erro na requisição de mensagem", t));
        }
    });
}

    public void buscarMensagensPorChat(String chat, MensagemCallback callback) {
        requestUser.buscarMensagensPorChat(chat).enqueue(new Callback<List<Mensagem>>() {
            @Override
            public void onResponse(Call<List<Mensagem>> call, Response<List<Mensagem>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccessList(response.body());
                } else {
                    callback.onFailure(new Exception("Falha ao buscar mensagens por chat, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Mensagem>> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de mensagens por chat", t));
            }
        });
    }

    public void buscarMensagensPorUsuario(String usuario, MensagemCallback callback) {
        requestUser.buscarMensagensPorUsuario(usuario).enqueue(new Callback<List<Mensagem>>() {
            @Override
            public void onResponse(Call<List<Mensagem>> call, Response<List<Mensagem>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccessList(response.body());
                } else {
                    callback.onFailure(new Exception("Falha ao buscar mensagens por usuário, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Mensagem>> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de mensagens por usuário", t));
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
                callback.onFailure(new Exception("Falha ao salvar mensagem, ERRO: " + response.code()));
            }
        }

        @Override
        public void onFailure(Call<Mensagem> call, Throwable t) {
            callback.onFailure(new Exception("Erro na requisição para salvar mensagem", t));
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
                callback.onFailure(new Exception("Falha ao atualizar mensagem, ERRO: " + response.code()));
            }
        }

        @Override
        public void onFailure(Call<Mensagem> call, Throwable t) {
            callback.onFailure(new Exception("Erro na requisição de atualização de mensagem", t));
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
                callback.onFailure(new Exception("Falha ao deletar mensagem, ERRO: " + response.code()));
            }
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
            callback.onFailure(new Exception("Erro na requisição de deleção de mensagem", t));
        }
    });
}
    /////////////////////////////////////////COMENTARIOS
    public void listarComentarios(ComentarioCallback callback) {
        requestUser.listarComentarios().enqueue(new Callback<List<Comentario>>() {
            @Override
            public void onResponse(Call<List<Comentario>> call, Response<List<Comentario>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccessList(response.body());
                } else {
                    callback.onFailure(new Exception("Falha ao listar comentários, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Comentario>> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de comentários", t));
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
                    callback.onFailure(new Exception("Falha ao buscar comentário por ID, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Comentario> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de comentário por ID", t));
            }
        });
    }

    public void buscarComentariosPorUsuario(String usuarioComent, ComentarioCallback callback) {
        requestUser.buscarComentariosPorUsuario(usuarioComent).enqueue(new Callback<List<Comentario>>() {
            @Override
            public void onResponse(Call<List<Comentario>> call, Response<List<Comentario>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccessList(response.body());
                } else {
                    callback.onFailure(new Exception("Falha ao buscar comentários por usuário, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Comentario>> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de comentários por usuário", t));
            }
        });
    }

    public void buscarComentariosPorLivro(Long idLivro, ComentarioCallback callback) {
        requestUser.buscarComentariosPorLivro(idLivro).enqueue(new Callback<List<Comentario>>() {
            @Override
            public void onResponse(Call<List<Comentario>> call, Response<List<Comentario>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccessList(response.body());
                } else {
                    callback.onFailure(new Exception("Falha ao buscar comentários por livro, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Comentario>> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de comentários por livro", t));
            }
        });
    }

    public void adicionarComentario(Comentario comentario, ComentarioCallback callback) {
        requestUser.adicionarComentario(comentario).enqueue(new Callback<Comentario>() {
            @Override
            public void onResponse(Call<Comentario> call, Response<Comentario> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Falha ao adicionar comentário, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Comentario> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de adicionar comentário", t));
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
                    callback.onFailure(new Exception("Falha ao atualizar comentário, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Comentario> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de atualização de comentário", t));
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
                    callback.onFailure(new Exception("Falha ao deletar comentário, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de deleção de comentário", t));
            }
        });
    }


}
