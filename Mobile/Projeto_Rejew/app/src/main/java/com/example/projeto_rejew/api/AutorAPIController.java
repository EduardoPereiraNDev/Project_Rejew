package com.example.projeto_rejew.api;

import com.example.projeto_rejew.entity.Autor;

import java.util.List;

public class AutorAPIController {

    public interface AutorCallback {
        void onSuccess(Autor autor);
        void onSuccessList(List<Autor> autorL);
        void onFailure(Throwable t);
    }
}
