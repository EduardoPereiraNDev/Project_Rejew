package com.example.projeto_rejew.api;

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

    public void getLoginUser(String email, String password, UsuarioAPIController.UsuarioCallback responseCallback) {

        Usuario usuario = new Usuario(email, password );

        Call<Usuario> call = this.usuarioApi.loginUsuario(usuario);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                responseCallback.onSuccess(response.body());
            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                responseCallback.onFailure(new Exception("Request failed"));
            }
        });
    }


}
