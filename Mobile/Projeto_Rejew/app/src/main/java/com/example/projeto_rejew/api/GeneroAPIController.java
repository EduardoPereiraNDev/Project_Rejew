package com.example.projeto_rejew.api;

import com.example.projeto_rejew.entity.Genero;

import java.util.List;

public class GeneroAPIController {

    public interface GeneroCallback {
        void onSuccess(Genero genero);
        void onSuccessList(List<Genero> generoL);
        void onFailure(Throwable t);
    }
}
