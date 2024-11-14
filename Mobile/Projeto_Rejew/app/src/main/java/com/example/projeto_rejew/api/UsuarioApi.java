package com.example.projeto_rejew.api;

import com.example.projeto_rejew.entity.Autor;
import com.example.projeto_rejew.entity.Chat;
import com.example.projeto_rejew.entity.Comentario;
import com.example.projeto_rejew.entity.Genero;
import com.example.projeto_rejew.entity.Livro;
import com.example.projeto_rejew.entity.Mensagem;
import com.example.projeto_rejew.entity.Usuario;

import java.util.List;
import java.util.Set;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UsuarioApi {
    @GET("usuarios")
    Call<List<Usuario>> listarTodosUsuarios();

    @GET("usuarios/{email}")
    Call<Usuario> buscarUsuarioPorEmail(@Path("email") String email);

    @GET("usuarios/nome/{nome}")
    Call<List<Usuario>> buscarUsuarioPorNome(@Path("nome") String nome);

    @GET("usuarios/perfil/{nome}")
    Call<List<Usuario>> buscarUsuarioPorNomePerfil(@Path("nome") String nome);

    @POST("usuarios/login")
    Call<Usuario> loginUsuario(@Body Usuario usuario);

    @POST("usuarios")
    Call<Usuario> criarUsuario(@Body Usuario usuario);

    @POST("usuarios/{emailEntrada}/favoritar/{isbnLivro}")
    Call <ResponseBody> favoritarLivro(
            @Path("emailEntrada") String emailEntrada,
            @Path("isbnLivro") long isbnLivro);

    @Multipart
    @PUT("usuarios/atualizar/{emailEntrada}")
    Call<ResponseBody> atualizarUsuario( @Path("emailEntrada") String emailEntrada,
                                         @Part MultipartBody.Part imagemPerfil,
                                         @Part MultipartBody.Part imagemFundo,
                                         @Part("recadoPerfil") String recadoPerfil,
                                         @Part("nomeUsuario") String nomeUsuario,
                                         @Part("nomePerfil") String nomePerfil,
                                         @Part("senhaEntrada") String senhaEntrada,
                                         @Part("dataNascimento") String dataNascimento );

    @GET("usuarios/{emailEntrada}/verfavoritado/{isbnLivro}")
    Call <Boolean> verfavoritarLivro(
            @Path("emailEntrada") String emailEntrada,
            @Path("isbnLivro") long isbnLivro);

    @DELETE("usuarios/{emailEntrada}/desfavoritar/{isbnLivro}")
    Call <ResponseBody> desfavoritarLivro(
            @Path("emailEntrada") String emailEntrada,
            @Path("isbnLivro") long isbnLivro);

    @GET("usuarios/{emailEntrada}/verfavoritados")
    Call <List<Livro>> verFavoritados(
            @Path("emailEntrada") String emailEntrada);

    @GET("usuarios/{emailEntrada}/quantidadeSeguidores")
    Call <Integer> qtdSeguidores(
            @Path("emailEntrada") String emailEntrada);

    @GET("usuarios/{emailEntrada}/quantidadeSeguindo")
    Call <Integer> qtdSeguindo(
            @Path("emailEntrada") String emailEntrada);

    @POST("usuarios/seguir/{usuarioSeguindoEmail}/{usuarioSeguidoEmail}")
    Call<ResponseBody> seguirUsuario(@Path("usuarioSeguindoEmail") String usuarioSeguindoEmail,
                             @Path("usuarioSeguidoEmail") String usuarioSeguidoEmail);

    @POST("usuarios/deixarSeguir/{usuarioSeguindoEmail}/{usuarioSeguidoEmail}")
    Call<ResponseBody> deixarSeguirUsuario(@Path("usuarioSeguindoEmail") String usuarioSeguindoEmail,
                                   @Path("usuarioSeguidoEmail") String usuarioSeguidoEmail);

    @GET("usuarios/estaSeguindo/{usuarioSeguindoEmail}/{usuarioSeguidoEmail}")
    Call<Boolean> estaSeguindo(@Path("usuarioSeguindoEmail") String usuarioSeguindoEmail,
                               @Path("usuarioSeguidoEmail") String usuarioSeguidoEmail);

    @GET("usuarios/imagem/fundo/{caminho}")
    Call<ResponseBody> buscarImagemFundo(@Path("caminho") String caminho);
    @GET("usuarios/imagem/{caminho}")
    Call<ResponseBody> buscarImagemPerfil(@Path("caminho") String caminho);

    @DELETE("usuarios/{emailUsuario}")
    Call<ResponseBody> deletarUsuario(@Path("emailUsuario") String emailUsuario);

}
