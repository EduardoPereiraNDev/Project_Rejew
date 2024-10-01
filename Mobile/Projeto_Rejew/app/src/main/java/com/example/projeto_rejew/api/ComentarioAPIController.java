package com.example.projeto_rejew.api;

import com.example.projeto_rejew.entity.Comentario;

import java.util.List;

public class ComentarioAPIController {

    public interface ComentarioCallback {
        void onSuccess(Comentario comentario);
        void onSuccessList(List<Comentario> ComentarioL);
        void onFailure(Throwable t);
    }
}
