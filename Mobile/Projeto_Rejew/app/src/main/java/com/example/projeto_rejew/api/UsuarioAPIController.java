package com.example.projeto_rejew.api;

import android.util.Log;

import com.example.projeto_rejew.entity.Usuario;

import java.util.List;

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

}
