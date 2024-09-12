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

        /////////// CHAT

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
/*
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

    public void buscarLivroPorId(Long id, LivroCallback callback) {
        requestUser.buscarLivroPorId(id).enqueue(new Callback<Livro>() {
            @Override
            public void onResponse(Call<Livro> call, Response<Livro> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Falha ao buscar livro, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Livro> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de livro", t));
            }
        });
    }

    public void buscarLivroPorTitulo(String titulo, LivroCallback callback) {
        requestUser.buscarLivroPorNome(titulo).enqueue(new Callback<List<Livro>>() {
            @Override
            public void onResponse(Call<List<Livro>> call, Response<List<Livro>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccessList(response.body());
                } else {
                    callback.onFailure(new Exception("Falha ao buscar livro por título, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Livro>> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de livro por título", t));
            }
        });
    }

    public void salvarLivro(Livro livro, LivroCallback callback) {
        requestUser.adicionarLivro(livro).enqueue(new Callback<Livro>() {
            @Override
            public void onResponse(Call<Livro> call, Response<Livro> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Falha ao salvar livro, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Livro> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição para salvar livro", t));
            }
        });
    }

    public void atualizarLivro(Long id, Livro livro, LivroCallback callback) {
        requestUser.atualizarLivro(id, livro).enqueue(new Callback<Livro>() {
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

    public void deletarLivro(Long id, LivroCallback callback) {
        requestUser.deletarLivro(id).enqueue(new Callback<Void>() {
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
                    callback.onFailure(new Exception("Falha ao buscar chat, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Chat> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição de chat", t));
            }
        });
    }

    public void salvarChat(Chat chat, ChatCallback callback) {
        requestUser.adicionarChat(chat).enqueue(new Callback<Chat>() {
            @Override
            public void onResponse(Call<Chat> call, Response<Chat> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Falha ao salvar chat, ERRO: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Chat> call, Throwable t) {
                callback.onFailure(new Exception("Erro na requisição para salvar chat", t));
            }
        });
    }


    //////////////////////////////CHATS

    public void listarTodosChats(ChatListCallback callback) {
    requestUser.listarTodosChats().enqueue(new Callback<List<Chat>>() {
        @Override
        public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
            if (response.isSuccessful()) {
                callback.onSuccess(response.body());
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
                callback.onFailure(new Exception("Falha ao buscar chat, ERRO: " + response.code()));
            }
        }

        @Override
        public void onFailure(Call<Chat> call, Throwable t) {
            callback.onFailure(new Exception("Erro na requisição de chat", t));
        }
    });
}

public void salvarChat(Chat chat, ChatCallback callback) {
    requestUser.salvarChat(chat).enqueue(new Callback<Chat>() {
        @Override
        public void onResponse(Call<Chat> call, Response<Chat> response) {
            if (response.isSuccessful()) {
                callback.onSuccess(response.body());
            } else {
                callback.onFailure(new Exception("Falha ao salvar chat, ERRO: " + response.code()));
            }
        }

        @Override
        public void onFailure(Call<Chat> call, Throwable t) {
            callback.onFailure(new Exception("Erro na requisição para salvar chat", t));
        }
    });
}


public void deletarChat(Long id, VoidCallback callback) {
    requestUser.deletarChat(id).enqueue(new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            if (response.isSuccessful()) {
                callback.onSuccess();
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


    ////////////////////////////////////////MENSAGENS

    public void listarTodasMensagens(MensagemListCallback callback) {
    requestUser.listarTodasMensagens().enqueue(new Callback<List<Mensagem>>() {
        @Override
        public void onResponse(Call<List<Mensagem>> call, Response<List<Mensagem>> response) {
            if (response.isSuccessful()) {
                callback.onSuccess(response.body());
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

public void deletarMensagem(Long id, VoidCallback callback) {
    requestUser.deletarMensagem(id).enqueue(new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            if (response.isSuccessful()) {
                callback.onSuccess();
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


*/
}
