package com.example.projeto_rejew.api;

import com.example.projeto_rejew.entity.Chat;
import com.example.projeto_rejew.entity.Genero;

import java.util.List;

public class ChatAPIController {

    public interface ChatCallback {
        void onSuccess(Chat chat);
        void onSuccessList(List<Chat> ChatL);
        void onFailure(Throwable t);
    }
}
