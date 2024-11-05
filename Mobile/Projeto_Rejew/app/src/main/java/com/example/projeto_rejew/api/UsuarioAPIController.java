package com.example.projeto_rejew.api;

import android.util.Log;

import com.example.projeto_rejew.entity.Livro;
import com.example.projeto_rejew.entity.Usuario;

import java.util.List;
import java.util.Set;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioAPIController {

    private RetrofitClient retrofitClient;
    private List<Usuario> usuarioL;
    private String status;
    private Usuario usuario;
    private UsuarioApi usuarioApi;

    public interface UsuarioCallback {
        void onSuccess(Usuario usuario);
        void onSuccessBoolean(Boolean booleano);
        void onSuccessInt(Integer integer);
        void onSuccessByte(byte[] bytes);
        void onSuccessList(List<Usuario> usuarios);
        void onSuccessListL(List<Livro> livros);
        void onSuccessResponse(ResponseBody body);
        void onSuccessString(String string);
        void onFailure(Throwable t);
    }

    public UsuarioAPIController(RetrofitClient retrofitClient) {
        this.retrofitClient = retrofitClient;
        this.usuarioApi = RetrofitClient.getRetrofit().create(UsuarioApi.class);
        this.status = "";
    }

    public void getLoginUser(String emailEntrada, String senhaEntrada, UsuarioAPIController.UsuarioCallback responseCallback) {
        Usuario usuario = new Usuario(emailEntrada, senhaEntrada);

        Call<Usuario> call = this.usuarioApi.loginUsuario(usuario);
        Log.d("API Call", "Fazendo chamada de login para: " + emailEntrada);

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                Log.d("API Response", "Código de resposta: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API Response", "Usuário retornado: " + response.body().toString());
                    responseCallback.onSuccess(response.body());
                } else {
                    Log.e("API Error", "Erro ao fazer login: " + response.errorBody());
                    responseCallback.onFailure(new Exception("Erro: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("API Error", "Falha na chamada: " + t.getMessage());
                responseCallback.onFailure(t);
            }
        });
    }

    public void listarUsuarios(UsuarioAPIController.UsuarioCallback responseCallback) {

        Call <List<Usuario>> call = this.usuarioApi.listarTodosUsuarios();
        Log.d("API Call", "Fazendo chamada de login para todos");

        call.enqueue(new Callback <List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                Log.d("API Response", "Código de resposta: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API Response", "Usuários retornado: " + response.body().toString());
                    responseCallback.onSuccessList(response.body());
                } else {
                    Log.e("API Error", "Erro ao fazer login: " + response.errorBody());
                    responseCallback.onFailure(new Exception("Erro: " + response.code()));
                }
            }
            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.e("API Error", "Falha na chamada: " + t.getMessage());
                responseCallback.onFailure(t);
            }
        });
    }

    public void carregarImagemPerfil(String caminhoImgPerfil, UsuarioAPIController.UsuarioCallback callback) {
        Call<ResponseBody> call = usuarioApi.buscarImagemPerfil(caminhoImgPerfil);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        byte[] imageBytes = response.body().bytes();
                        callback.onSuccessByte(imageBytes);
                    } catch (Exception e) {
                        callback.onFailure(e);
                    }
                } else {
                    callback.onFailure(new Exception("Erro ao baixar a imagem: " + response.code()));
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void carregarImagemFundo(String caminhoImgFundo, UsuarioAPIController.UsuarioCallback callback) {
        Call<ResponseBody> call = usuarioApi.buscarImagemFundo(caminhoImgFundo);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        byte[] imageBytes = response.body().bytes();
                        callback.onSuccessByte(imageBytes);
                    } catch (Exception e) {
                        callback.onFailure(e);
                    }
                } else {
                    callback.onFailure(new Exception("Erro ao baixar a imagem: " + response.code()));
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void cadastrarUsuario(Usuario usuario, UsuarioAPIController.UsuarioCallback responseCallback) {
        Log.d("API Call", "Fazendo chamada de cadastro para: " + usuario.getEmailEntrada());
        Call<Usuario> call = this.usuarioApi.criarUsuario(usuario);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                Log.d("API Response", "Código de resposta: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API Response", "Usuário cadastrado: " + response.body().toString());
                    responseCallback.onSuccess(response.body());
                } else {
                    Log.e("API Error", "Erro ao cadastrar usuário: " + response.errorBody());
                    responseCallback.onFailure(new Exception("Erro: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("API Error", "Falha na chamada: " + t.getMessage());
                responseCallback.onFailure(t);
            }
        });
    }

    public void atualizarUsuario(Usuario usuario,String emailUsuario, UsuarioAPIController.UsuarioCallback responseCallback) {
        Log.d("API Call", "Fazendo chamada de Atualização para: " + emailUsuario);
        Call<Usuario> call = this.usuarioApi.atualizarUsuario(emailUsuario, usuario);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                Log.d("API Response", "Código de resposta: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API Response", "Usuário atualizado: " + response.body().toString());
                    responseCallback.onSuccess(response.body());
                } else {
                    Log.e("API Error", "Erro ao atualizar usuário: " + response.errorBody());
                    responseCallback.onFailure(new Exception("Erro: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("API Error", "Falha na chamada: " + t.getMessage());
                responseCallback.onFailure(t);
            }
        });
    }

    public void buscarUsuario(String emailEntrada, UsuarioAPIController.UsuarioCallback responseCallback) {
        Log.d("API Call", "Fazendo chamada para buscar usuário com email: " + emailEntrada);
        Call<Usuario> call = this.usuarioApi.buscarUsuarioPorEmail(emailEntrada);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                Log.d("API Response", "Código de resposta: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API Response", "Usuário encontrado: " + response.body().toString());
                    responseCallback.onSuccess(response.body());
                } else {
                    Log.e("API Error", "Erro ao encontrar usuário: " + response.errorBody());
                    responseCallback.onFailure(new Exception("Erro: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("API Error", "Falha na chamada: " + t.getMessage());
                responseCallback.onFailure(t);
            }
        });
    }

    public void buscarUsuariosNome(String pesquisaNome, UsuarioAPIController.UsuarioCallback responseCallback) {
        Log.d("API Call", "Fazendo chamada para buscar usuário com nome Usuario: " + pesquisaNome);
        Call<List<Usuario>> call = this.usuarioApi.buscarUsuarioPorNome(pesquisaNome);
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                Log.d("API Response", "Código de resposta: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API Response", "Usuário encontrado: " + response.body().toString());
                    responseCallback.onSuccessList(response.body());
                } else {
                    Log.e("API Error", "Erro ao encontrar usuário: " + response.errorBody());
                    responseCallback.onFailure(new Exception("Erro: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.e("API Error", "Falha na chamada: " + t.getMessage());
                responseCallback.onFailure(t);
            }
        });
    }

    public void buscarUsuariosNomeP(String pesquisaNome, UsuarioAPIController.UsuarioCallback responseCallback) {
        Log.d("API Call", "Fazendo chamada para buscar usuários com nome: " + pesquisaNome);
        Call<List<Usuario>> call = this.usuarioApi.buscarUsuarioPorNomePerfil(pesquisaNome);
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                Log.d("API Response", "Código de resposta: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API Response", "Usuário encontrado: " + response.body().toString());
                    responseCallback.onSuccessList(response.body());
                } else {
                    Log.e("API Error", "Erro ao encontrar usuário: " + response.errorBody());
                    responseCallback.onFailure(new Exception("Erro: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.e("API Error", "Falha na chamada: " + t.getMessage());
                responseCallback.onFailure(t);
            }
        });
    }

    public void favoritarLivro(String emailEntrada, Long isbnLivro, UsuarioAPIController.UsuarioCallback responseCallback) {
        Log.d("API Call", "Fazendo chamada para favoritar livro com email: " + isbnLivro + emailEntrada);
        Call<ResponseBody> call = this.usuarioApi.favoritarLivro(emailEntrada, isbnLivro);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("API Response", "Código de resposta: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API Response", "Usuário encontrado: " + response.body().toString());
                    responseCallback.onSuccessResponse(response.body());
                } else {
                    Log.e("API Error", "Erro ao encontrar usuário: " + response.errorBody());
                    responseCallback.onFailure(new Exception("Erro: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("API Error", "Falha na chamada: " + t.getMessage());
                responseCallback.onFailure(t);
            }
        });
    }


    public void desfavoritarLivro(String emailEntrada, Long isbnLivro, UsuarioAPIController.UsuarioCallback responseCallback) {
        Log.d("API Call", "Fazendo chamada para favoritar livro com email: " + isbnLivro + emailEntrada);
        Call<ResponseBody> call = this.usuarioApi.desfavoritarLivro(emailEntrada, isbnLivro);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("API Response", "Código de resposta: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API Response", "Usuário encontrado: " + response.body().toString());
                    responseCallback.onSuccessResponse(response.body());
                } else {
                    Log.e("API Error", "Erro ao encontrar usuário: " + response.errorBody());
                    responseCallback.onFailure(new Exception("Erro: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("API Error", "Falha na chamada: " + t.getMessage());
                responseCallback.onFailure(t);
            }
        });
    }

    public void verFavoritadoLivro(String emailEntrada, long isbnLivro, UsuarioAPIController.UsuarioCallback responseCallback) {
        Call<Boolean> call = usuarioApi.verfavoritarLivro(emailEntrada, isbnLivro);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null) {
                    responseCallback.onSuccessBoolean(response.body());
                } else {
                    responseCallback.onSuccessBoolean(false);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                responseCallback.onFailure(t);
            }
        });
    }

    public void verFavoritados(String emailEntrada, UsuarioAPIController.UsuarioCallback responseCallback) {
        Call<List<Livro>> call = usuarioApi.verFavoritados(emailEntrada);
        call.enqueue(new Callback<List<Livro>>() {
            @Override
            public void onResponse(Call<List<Livro>> call, Response<List<Livro>> response) {
                Log.d("API Response", "Código de resposta: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API Response", "Usuário encontrado: " + response.body().toString());
                    responseCallback.onSuccessListL(response.body());
                } else {
                    Log.e("API Error", "Erro ao encontrar usuário: " + response.errorBody());
                    responseCallback.onFailure(new Exception("Erro: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Livro>> call, Throwable t) {
                responseCallback.onFailure(t);
            }
        });
    }

    public void seguirUsuario(String usuarioSeguindoEmail, String usuarioSeguidoEmail, UsuarioAPIController.UsuarioCallback responseCallback) {

        Call <ResponseBody> call = this.usuarioApi.seguirUsuario(usuarioSeguindoEmail, usuarioSeguidoEmail);
        Log.d("API Call", "Fazendo chamada de seguir para"+ usuarioSeguindoEmail + usuarioSeguidoEmail);

        call.enqueue(new Callback <ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("API Response", "Código de resposta: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API Response", "Usuários retornado: " + response.body().toString());
                    responseCallback.onSuccessResponse(response.body());
                } else {
                    Log.e("API Error", "Erro ao fazer login: " + response.errorBody());
                    responseCallback.onFailure(new Exception("Erro: " + response.code()));
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("API Error", "Falha na chamada: " + t.getMessage());
                responseCallback.onFailure(t);
            }

        });
    }

    public void deixarSeguirUsuario(String usuarioSeguindoEmail, String usuarioSeguidoEmail, UsuarioAPIController.UsuarioCallback responseCallback) {

        Call <ResponseBody> call = this.usuarioApi.deixarSeguirUsuario(usuarioSeguindoEmail, usuarioSeguidoEmail);
        Log.d("API Call", "Fazendo chamada de seguir para"+ usuarioSeguindoEmail + usuarioSeguidoEmail);

        call.enqueue(new Callback <ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("API Response", "Código de resposta: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API Response", "Usuários retornado: " + response.body().toString());
                    responseCallback.onSuccessResponse(response.body());
                } else {
                    Log.e("API Error", "Erro ao fazer login: " + response.errorBody());
                    responseCallback.onFailure(new Exception("Erro: " + response.code()));
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("API Error", "Falha na chamada: " + t.getMessage());
                responseCallback.onFailure(t);
            }

        });
    }

    public void estaSeguindoUsuario(String usuarioSeguindoEmail, String usuarioSeguidoEmail, UsuarioAPIController.UsuarioCallback responseCallback) {

        Call <Boolean> call = this.usuarioApi.estaSeguindo(usuarioSeguindoEmail, usuarioSeguidoEmail);
        Log.d("API Call", "Fazendo chamada de seguir para"+ usuarioSeguindoEmail + usuarioSeguidoEmail);

        call.enqueue(new Callback <Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Log.d("API Response", "Código de resposta: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API Response", "Usuários retornado: " + response.body().toString());
                    responseCallback.onSuccessBoolean(response.body());
                } else {
                    Log.e("API Error", "Erro ao fazer login: " + response.errorBody());
                    responseCallback.onFailure(new Exception("Erro: " + response.code()));
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("API Error", "Falha na chamada: " + t.getMessage());
                responseCallback.onFailure(t);
            }

        });
    }

    public void qtdSeguidores(String emailEntrada, UsuarioAPIController.UsuarioCallback responseCallback) {

        Call<Integer> call = this.usuarioApi.qtdSeguidores(emailEntrada);
        Log.d("API Call", "Fazendo chamada de seguir para" + emailEntrada);

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Log.d("API Response", "Código de resposta: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API Response", "Usuários retornado: " + response.body().toString());
                    responseCallback.onSuccessInt(response.body());
                } else {
                    Log.e("API Error", "Erro ao fazer login: " + response.errorBody());
                    responseCallback.onFailure(new Exception("Erro: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("API Error", "Falha na chamada: " + t.getMessage());
                responseCallback.onFailure(t);
            }

        });
    }
    public void qtdSeguindo(String emailEntrada, UsuarioAPIController.UsuarioCallback responseCallback) {

        Call<Integer> call = this.usuarioApi.qtdSeguindo(emailEntrada);
        Log.d("API Call", "Fazendo chamada de seguir para" + emailEntrada);

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Log.d("API Response", "Código de resposta: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API Response", "Usuários retornado: " + response.body().toString());
                    responseCallback.onSuccessInt(response.body());
                } else {
                    Log.e("API Error", "Erro ao fazer login: " + response.errorBody());
                    responseCallback.onFailure(new Exception("Erro: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("API Error", "Falha na chamada: " + t.getMessage());
                responseCallback.onFailure(t);
            }

        });
    }

}
