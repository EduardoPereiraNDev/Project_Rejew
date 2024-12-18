package com.example.projeto_rejew.api;

import android.util.Log;

import com.example.projeto_rejew.entity.Comentario;
import com.example.projeto_rejew.entity.Usuario;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComentarioAPIController {

    private RetrofitClient retrofitClient;
    private String status;
    private ComentarioApi comentarioApi;

    public interface ComentarioCallback {
        void onSuccess(Comentario comentario);
        void onSuccessString(String string);
        void onSuccessResponse(ResponseBody responseBody);
        void onSuccessList(List<Comentario> ComentarioL);
        void onSuccessUsuario(Usuario usuario);
        void onFailure(Throwable t);
    }

    public ComentarioAPIController(RetrofitClient retrofitClient) {
        this.retrofitClient = retrofitClient;
        this.comentarioApi = RetrofitClient.getRetrofit().create(ComentarioApi.class);
        this.status = "";
    }


    public void adicionarComentario(Comentario comentario, ComentarioAPIController.ComentarioCallback responseCallback) {
        Log.d("API Call", "realizando comentario " + comentario.getUsuarioComent().getEmailEntrada());
        Call<Comentario> call = this.comentarioApi.adicionarComentario(comentario);
        call.enqueue(new Callback<Comentario>() {
            @Override
            public void onResponse(Call<Comentario> call, Response<Comentario> response) {
                Log.d("API Response", "Código de resposta: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API Response", "Comentario adicionado: " + response.body().toString());
                    responseCallback.onSuccess(response.body());
                } else {
                    // Adicione uma verificação para imprimir o corpo da resposta de erro
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
            public void onFailure(Call<Comentario> call, Throwable t) {
                Log.e("API Error", "Falha na chamada: " + t.getMessage());
                responseCallback.onFailure(t);
            }
        });
    }

    public void BuscarComentarioLivro(Long idLivro, ComentarioAPIController.ComentarioCallback responseCallback) {
        Log.d("API Call", "realizando requisição comentarios " + idLivro);
        Call <List<Comentario>> call = this.comentarioApi.buscarComentariosPorLivro(idLivro);
        call.enqueue(new Callback <List<Comentario>>() {
            @Override
            public void onResponse(Call<List<Comentario>> call, Response<List<Comentario>> response) {
                Log.d("API Response", "Código de resposta: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API Response", "Comentarios buscados: " + response.body().toString());
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
            public void onFailure(Call<List<Comentario>> call, Throwable t) {
                Log.e("API Error", "Falha na chamada: " + t.getMessage());
                responseCallback.onFailure(t);
            }
        });
    }

    public void buscarComentarioPorUsuario(String email, ComentarioAPIController.ComentarioCallback responseCallback) {
        Log.d("API Call", "realizando requisição comentarios " + email);
        Call <List<Comentario>> call = this.comentarioApi.buscarComentariosPorUsuario(email);
        call.enqueue(new Callback <List<Comentario>>() {
            @Override
            public void onResponse(Call<List<Comentario>> call, Response<List<Comentario>> response) {
                Log.d("API Response", "Código de resposta: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API Response", "Comentarios buscados: " + response.body().toString());
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
            public void onFailure(Call<List<Comentario>> call, Throwable t) {
                Log.e("API Error", "Falha na chamada: " + t.getMessage());
                responseCallback.onFailure(t);
            }
        });
    }

    public void buscarUsuarioPorComentario(Long idComentario, ComentarioAPIController.ComentarioCallback responseCallback) {
        Log.d("API Call", "Realizando requisição para buscar usuário do comentário: " + idComentario);
        Call<Usuario> call = this.comentarioApi.buscarUsuarioporComentario(idComentario);

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                Log.d("API Response", "Código de resposta: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API Response", "Usuário buscado: " + response.body().toString());
                    responseCallback.onSuccessUsuario(response.body());
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
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("API Error", "Falha na chamada: " + t.getMessage());
                responseCallback.onFailure(t);
            }
        });
    }

    public void deletarComentario(Long idComentario, ComentarioAPIController.ComentarioCallback responseCallback) {
        Log.d("API Call", "realizando requisição comentarios para deletar" + idComentario);
        Call <ResponseBody> call = this.comentarioApi.deletarComentario(idComentario);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("API Response", "Código de resposta: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API Response", "Comentarios buscados: " + response.body().toString());
                    responseCallback.onSuccessResponse(response.body());
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
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("API Error", "Falha na chamada: " + t.getMessage());
                responseCallback.onFailure(t);
            }

        });
    }
}
