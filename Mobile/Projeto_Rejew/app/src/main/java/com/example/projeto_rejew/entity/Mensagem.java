package com.example.projeto_rejew.entity;

import java.util.Date;

public class Mensagem {

    private Long idMensagem;
    private String textoMensagem;
    private String dataMensagem;
    private Usuario usuarioMensagem;
    private Chat chatMensagem;

    public Mensagem() {
    }

    public Mensagem(String textoMensagem, String dataMensagem, Usuario usuarioMensagem, Chat chatMensagem) {
        this.textoMensagem = textoMensagem;
        this.dataMensagem = dataMensagem;
        this.usuarioMensagem = usuarioMensagem;
        this.chatMensagem = chatMensagem;
    }

    public Long getIdMensagem() {
        return idMensagem;
    }

    public void setIdMensagem(Long idMensagem) {
        this.idMensagem = idMensagem;
    }

    public String getTextoMensagem() {
        return textoMensagem;
    }

    public void setTextoMensagem(String textoMensagem) {
        this.textoMensagem = textoMensagem;
    }

    public String getDataMensagem() {
        return dataMensagem;
    }

    public void setDataMensagem(String dataMensagem) {
        this.dataMensagem = dataMensagem;
    }

    public Usuario getUsuarioMensagem() {
        return usuarioMensagem;
    }

    public void setUsuarioMensagem(Usuario usuarioMensagem) {
        this.usuarioMensagem = usuarioMensagem;
    }

    public Chat getChatMensagem() {
        return chatMensagem;
    }

    public void setChatMensagem(Chat chatMensagem) {
        this.chatMensagem = chatMensagem;
    }
}

