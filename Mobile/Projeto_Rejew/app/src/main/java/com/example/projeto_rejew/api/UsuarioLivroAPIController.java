package com.example.projeto_rejew.api;

import android.util.Log;

import com.example.projeto_rejew.entity.Livro;
import com.example.projeto_rejew.entity.Usuario;
import com.example.projeto_rejew.entity.UsuarioLivroADTO;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioLivroAPIController {

    private  UsuarioLivroAPI usuarioLivroAPI;
    private RetrofitClient retrofitClient;
    private String status;

    public interface UsuarioLivroCallback {
        void onSuccess(UsuarioLivroADTO usuarioLivroADTO);
        void onSuccessList(List<UsuarioLivroADTO> usuariosLivroADTO);
        void onSuccessBoolean(Boolean booleano);
        void onSuccessDouble(Double doble);
        void onFailure(Throwable t);
    }

    public UsuarioLivroAPIController(RetrofitClient retrofitClient) {
        this.retrofitClient = retrofitClient;
        this.usuarioLivroAPI = RetrofitClient.getRetrofit().create(UsuarioLivroAPI.class);
        this.status = "";
    }

    public void adicionarAvaliacao(String emailEntrada, Long isbnLivro, Double nota , UsuarioLivroAPIController.UsuarioLivroCallback responseCallback) {
        Log.d("API Call", "realizando comentario ");
        Call<UsuarioLivroADTO> call = this.usuarioLivroAPI.inserirAvaliacao(emailEntrada,isbnLivro,nota);
        call.enqueue(new Callback<UsuarioLivroADTO>() {
            @Override
            public void onResponse(Call<UsuarioLivroADTO> call, Response<UsuarioLivroADTO> response) {
                Log.d("API Response", "Código de resposta: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API Response", "Comentario adicionado: " + response.body().toString());
                    responseCallback.onSuccess(response.body());
                } else {
                    if (response.errorBody() != null) {
                        try {
                            String errorBody = response.errorBody().string();
                            Log.e("API Error", "Corpo da resposta de erro: " + errorBody);
                        } catch (IOException e) {
                            Log.e("API Error", "Erro ao ler corpo da resposta de erro: " + e.getMessage());
                        }
                    } else {
                        Log.e("API Error", "Resposta sem corpo de erro");
                    }
                    responseCallback.onFailure(new Exception("Erro: " + response.code()));
                }
            }
            @Override
            public void onFailure(Call<UsuarioLivroADTO> call, Throwable t) {
                Log.e("API Error", "Falha na chamada: " + t.getMessage());
                responseCallback.onFailure(t);
            }
        });
    }

    public void listarTodasAvaliacoes(UsuarioLivroAPIController.UsuarioLivroCallback responseCallback) {
        Log.d("API Call", "realizando comentario ");
        Call <List<UsuarioLivroADTO>> call = this.usuarioLivroAPI.listarTodasAvaliacoes();
        call.enqueue(new Callback<List<UsuarioLivroADTO>>() {
            @Override
            public void onResponse(Call<List<UsuarioLivroADTO>> call, Response<List<UsuarioLivroADTO>> response) {
                Log.d("API Response", "Código de resposta: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API Response", "Comentario adicionado: " + response.body().toString());
                    responseCallback.onSuccessList(response.body());
                } else {
                    if (response.errorBody() != null) {
                        try {
                            String errorBody = response.errorBody().string();
                            Log.e("API Error", "Corpo da resposta de erro: " + errorBody);
                        } catch (IOException e) {
                            Log.e("API Error", "Erro ao ler corpo da resposta de erro: " + e.getMessage());
                        }
                    } else {
                        Log.e("API Error", "Resposta sem corpo de erro");
                    }
                    responseCallback.onFailure(new Exception("Erro: " + response.code()));
                }
            }
            @Override
            public void onFailure(Call<List<UsuarioLivroADTO>> call, Throwable t) {
                Log.e("API Error", "Falha na chamada: " + t.getMessage());
                responseCallback.onFailure(t);
            }
        });
    }

    public void calcularMedia(Long isbnLivro , UsuarioLivroAPIController.UsuarioLivroCallback responseCallback) {
        Log.d("API Call", "realizando comentario ");
        Call <Double> call = this.usuarioLivroAPI.calcularMedia(isbnLivro);
        call.enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                Log.d("API Response", "Código de resposta: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API Response", "Comentario adicionado: " + response.body().toString());
                    responseCallback.onSuccessDouble(response.body());
                } else {
                    if (response.errorBody() != null) {
                        try {
                            String errorBody = response.errorBody().string();
                            Log.e("API Error", "Corpo da resposta de erro: " + errorBody);
                        } catch (IOException e) {
                            Log.e("API Error", "Erro ao ler corpo da resposta de erro: " + e.getMessage());
                        }
                    } else {
                        Log.e("API Error", "Resposta sem corpo de erro");
                    }
                    responseCallback.onFailure(new Exception("Erro: " + response.code()));
                }
            }
            @Override
            public void onFailure(Call<Double> call, Throwable t) {
                Log.e("API Error", "Falha na chamada: " + t.getMessage());
                responseCallback.onFailure(t);
            }
        });
    }

    public void verificarJaAvaliado(String emailEntrada, Long isbnLivro , UsuarioLivroAPIController.UsuarioLivroCallback responseCallback) {
        Log.d("API Call", "realizando comentario ");
        Call <Boolean> call = this.usuarioLivroAPI.verificarJaAvaliado(emailEntrada, isbnLivro);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Log.d("API Response", "Código de resposta: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API Response", "Comentario adicionado: " + response.body().toString());
                    responseCallback.onSuccessBoolean(response.body());
                } else {
                    if (response.errorBody() != null) {
                        try {
                            String errorBody = response.errorBody().string();
                            Log.e("API Error", "Corpo da resposta de erro: " + errorBody);
                        } catch (IOException e) {
                            Log.e("API Error", "Erro ao ler corpo da resposta de erro: " + e.getMessage());
                        }
                    } else {
                        Log.e("API Error", "Resposta sem corpo de erro");
                    }
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


}
