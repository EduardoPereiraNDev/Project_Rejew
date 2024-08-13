package com.example.projeto_rejew.Recycler;

import java.util.Date;

public class Comentario {
    private Long id;
    private String nomeUsuario;
    private String nomeLivro;
    private Date dataRealizacao;

    public Comentario() {}

    public Comentario(Long id, String nomeU, String nomeL, Date dataR) {
        this.id = id;
        this.nomeUsuario = nomeU;
        this.nomeLivro = nomeL;
        this.dataRealizacao = dataR;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getNomeLivro() {
        return nomeLivro;
    }

    public void setNomeLivro(String nomeLivro) {
        this.nomeLivro = nomeLivro;
    }

    public Date getDataRealizacao() {
        return dataRealizacao;
    }

    public void setDataRealizacao(Date dataRealizacao) {
        this.dataRealizacao = dataRealizacao;
    }
}
