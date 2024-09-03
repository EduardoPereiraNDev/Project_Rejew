package com.example.projeto_rejew.api;

import com.example.projeto_rejew.entity.Autor;
import com.example.retrofit2.model.UserData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class APIConnection {

    interface RequestUser {

        @GET("autores")
        Call<Autor> getAutor(@Path("uid") String id);

        @GET("")
        Call<Autor> getChat(@Path("uid") String id);

        @GET("")
        Call<Autor> getComentario(@Path("uid") String id);

        @GET("")
        Call<Autor> getGenero(@Path("uid") String id);

        @GET("")
        Call<Autor> getLivro(@Path("uid") String id);

        @GET("")
        Call<Autor> getMensagem(@Path("uid") String id);

        @GET("")
        Call<Autor> getUsuario(@Path("uid") String id);

    }

    String urlBase = "https://127.0.0.1:3306";
    RequestUser requestUser;

    public APIConnection() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        requestUser = retrofit.create(RequestUser.class);
    }

    // UserData
    public interface UserDataCallback {
        void onSuccess(Autor autor);
        void onFailure(Throwable t);
    }
    public void getAutor(String id, UserDataCallback userDataCallback){
        requestUser.getUser(id).enqueue(new Callback<Autor>() {
            @Override
            public void onResponse(Call<Autor> call, Response<Autor> response)
            {   userDataCallback.onSuccess(response.body()); }
            @Override
            public void onFailure(Call<Autor> call, Throwable t)
            {   userDataCallback.onFailure(new Exception("Request failed")); }
        });
    }

}
