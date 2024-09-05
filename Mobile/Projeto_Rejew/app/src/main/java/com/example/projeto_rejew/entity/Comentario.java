package com.example.projeto_rejew.entity;

import java.util.Date;

public class Comentario {
    private Long idComentario;
    private Date dataComentario;
    private String conteudoComent;
    private Long usuarioComent;

    public Comentario() {
    }
    public Comentario(Date dataComentario, String conteudoComent, Long usuarioComent) {
        this.dataComentario = dataComentario;
        this.conteudoComent = conteudoComent;
        this.usuarioComent = usuarioComent;
    }

    public Long getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(Long idComentario) {
        this.idComentario = idComentario;
    }

    public Date getDataComentario() {
        return dataComentario;
    }

    public void setDataComentario(Date dataComentario) {
        this.dataComentario = dataComentario;
    }

    public String getConteudoComent() {
        return conteudoComent;
    }

    public void setConteudoComent(String conteudoComent) {
        this.conteudoComent = conteudoComent;
    }

    public Long getUsuarioComent() {
        return usuarioComent;
    }

    public void setUsuarioComent(Long usuarioComent) {
        this.usuarioComent = usuarioComent;
    }
}
