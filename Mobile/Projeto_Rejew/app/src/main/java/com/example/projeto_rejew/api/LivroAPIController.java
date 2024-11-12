package com.example.projeto_rejew.api;

import android.util.Log;

import com.example.projeto_rejew.entity.Livro;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LivroAPIController {

    private RetrofitClient retrofitClient;
    private LivroApi livroApi;

    public interface LivroCallback {
        void onSuccess(Livro livro);
        void onSuccessList(List<Livro> livroList);
        void onSuccessByte(byte[] bytes);
        void onFailure(Throwable t);
    }

    public LivroAPIController(RetrofitClient retrofitClient) {
        this.retrofitClient = retrofitClient;
        this.livroApi = retrofitClient.getRetrofit().create(LivroApi.class);
    }

    public void carregarLivros(LivroCallback responseCallback) {
        Call<List<Livro>> call = livroApi.listarTodosLivros();
        call.enqueue(new Callback<List<Livro>>() {
            @Override
            public void onResponse(Call<List<Livro>> call, Response<List<Livro>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    responseCallback.onSuccessList(response.body());
                } else {
                    Log.e("API Error", "Erro ao carregar livros: " + response.code());
                    responseCallback.onFailure(new Exception("Erro ao carregar livros"));
                }
            }

            @Override
            public void onFailure(Call<List<Livro>> call, Throwable t) {
                Log.e("API Error", "Falha na chamada: " + t.getMessage());
                responseCallback.onFailure(t);
            }
        });
    }

    public void buscarLivroPorId( Long isbn , LivroCallback responseCallback) {
        Call<Livro> call = livroApi.buscarLivroPorId(isbn);
        call.enqueue(new Callback<Livro>() {
            @Override
            public void onResponse(Call<Livro> call, Response<Livro> response) {
                if (response.isSuccessful() && response.body() != null) {
                    responseCallback.onSuccess(response.body());
                } else {
                    Log.e("API Error", "Erro ao carregar livros: " + response.code());
                    responseCallback.onFailure(new Exception("Erro ao carregar livros"));
                }
            }
            @Override
            public void onFailure(Call<Livro> call, Throwable t) {
                Log.e("API Error", "Falha na chamada: " + t.getMessage());
                responseCallback.onFailure(t);
            }
        });
    }

    public void buscarLivrosPorAutor( String autorLivro , LivroCallback responseCallback) {
        Call <List<Livro>> call = livroApi.buscarLivroPorAutor(autorLivro);
        call.enqueue(new Callback<List<Livro>>() {
            @Override
            public void onResponse(Call<List<Livro>> call, Response<List<Livro>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    responseCallback.onSuccessList(response.body());
                } else {
                    Log.e("API Error", "Erro ao carregar livros: " + response.code());
                    responseCallback.onFailure(new Exception("Erro ao carregar livros"));
                }
            }
            @Override
            public void onFailure(Call<List<Livro>> call, Throwable t) {
                Log.e("API Error", "Falha na chamada: " + t.getMessage());
                responseCallback.onFailure(t);
            }
        });
    }

    public void buscarLivrosPorNome( String nomeLivro , LivroCallback responseCallback) {
        Call <List<Livro>> call = livroApi.buscarLivroPorNome(nomeLivro);
        call.enqueue(new Callback<List<Livro>>() {
            @Override
            public void onResponse(Call<List<Livro>> call, Response<List<Livro>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    responseCallback.onSuccessList(response.body());
                } else {
                    Log.e("API Error", "Erro ao carregar livros: " + response.code());
                    responseCallback.onFailure(new Exception("Erro ao carregar livros"));
                }
            }
            @Override
            public void onFailure(Call<List<Livro>> call, Throwable t) {
                Log.e("API Error", "Falha na chamada: " + t.getMessage());
                responseCallback.onFailure(t);
            }
        });
    }

    public void retornarImagem(LivroCallback callback, String caminhoImgCapa) {
        Call<ResponseBody> call = livroApi.retornarImagem(caminhoImgCapa);

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
}
