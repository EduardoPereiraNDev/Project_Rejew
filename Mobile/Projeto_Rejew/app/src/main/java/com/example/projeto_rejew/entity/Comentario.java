package com.example.projeto_rejew.entity;

import java.util.Date;

public class Comentario {
    private Long idComentario;
    private String dataComentario;
    private String conteudoComent;
    private Usuario usuarioComent;
    private Livro livroComent;

    public Comentario() {
    }
    public Comentario(String dataComentario, String conteudoComent, Usuario usuarioComent, Livro livroComent) {
        this.dataComentario = dataComentario;
        this.conteudoComent = conteudoComent;
        this.usuarioComent = usuarioComent;
        this.livroComent = livroComent;
    }

    public Long getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(Long idComentario) {
        this.idComentario = idComentario;
    }

    public String getDataComentario() {
        return dataComentario;
    }

    public void setDataComentario(String dataComentario) {
        this.dataComentario = dataComentario;
    }

    public String getConteudoComent() {
        return conteudoComent;
    }

    public void setConteudoComent(String conteudoComent) {
        this.conteudoComent = conteudoComent;
    }

    public Usuario getUsuarioComent() {return usuarioComent;}

    public void setUsuarioComent(Usuario usuarioComent) {this.usuarioComent = usuarioComent;}

    public Livro getLivroComent() {return livroComent;}

    public void setLivroComent(Livro livroComent) {this.livroComent = livroComent;}


}
