package com.example.projeto_rejew.api;

import android.util.Log;

import com.example.projeto_rejew.entity.Usuario;

import java.util.List;

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
        void onSuccessByte(byte[] bytes);
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




}
