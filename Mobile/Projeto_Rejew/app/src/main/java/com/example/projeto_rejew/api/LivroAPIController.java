package com.example.projeto_rejew.api;

import com.example.projeto_rejew.entity.Livro;

import java.util.List;

public class LivroAPIController {

    public interface LivroCallback {
        void onSuccess(Livro livro);
        void onSuccessList(List<Livro> LivroL);
        void onFailure(Throwable t);
    }
}
