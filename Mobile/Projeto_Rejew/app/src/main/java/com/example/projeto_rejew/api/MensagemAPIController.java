package com.example.projeto_rejew.api;

import com.example.projeto_rejew.entity.Mensagem;

import java.util.List;

public class MensagemAPIController {

    public interface MensagemCallback {
        void onSuccess(Mensagem mensagem);
        void onSuccessList(List<Mensagem> MensagemL);
        void onFailure(Throwable t);
    }
}
