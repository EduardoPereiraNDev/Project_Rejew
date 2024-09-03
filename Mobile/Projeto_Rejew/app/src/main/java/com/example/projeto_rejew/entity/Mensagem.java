package com.example.projeto_rejew.entity;

import java.util.Date;

public class Mensagem {

    private Long idMensagem;
    private String textoMensagem;
    private Date dataMensagem;
    private String usuarioMensagem;
    private String chatMensagem;

    public Mensagem() {
    }

    public Mensagem(String textoMensagem, Date dataMensagem, String usuarioMensagem, String chatMensagem) {
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

    public Date getDataMensagem() {
        return dataMensagem;
    }

    public void setDataMensagem(Date dataMensagem) {
        this.dataMensagem = dataMensagem;
    }

    public String getUsuarioMensagem() {
        return usuarioMensagem;
    }

    public void setUsuarioMensagem(String usuarioMensagem) {
        this.usuarioMensagem = usuarioMensagem;
    }

    public String getChatMensagem() {
        return chatMensagem;
    }

    public void setChatMensagem(String chatMensagem) {
        this.chatMensagem = chatMensagem;
    }
}

