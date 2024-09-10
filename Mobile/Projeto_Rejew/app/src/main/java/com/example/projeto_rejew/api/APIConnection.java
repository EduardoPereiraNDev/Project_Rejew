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
        Call<Autor> criarAutor(@Body Autor autor);

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

        @GET("usuarios/{id}")
        Call<Usuario> buscarUsuarioPorId(@Path("id") Long id);

        @GET("usuarios/nome/{nome}")
        Call<List<Usuario>> buscarUsuarioPorNome(@Path("nome") String nome);

        @GET("usuarios/perfil/{nome}")
        Call<List<Usuario>> buscarUsuarioPorNomePerfil(@Path("nome") String nome);

        @GET("usuarios/email/{email}")
        Call<Usuario> buscarUsuarioPorEmail(@Path("email") String email);

        @GET("usuarios/email/{emailEntrada}")
        Call<List<Usuario>> buscarUsuarioPorEmailLike(@Path("emailEntrada") String emailEntrada);

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

    //////////////////////////////////////////////////GENEROS

    ////////////////////////////////////////////LIVROS

    //////////////////////////////CHATS

    /////////////////////////////////////////////////////USUARIOS


    ////////////////////////////////////////MENSAGENS

    /////////////////////////////////////////COMENTARIOS
   


}
