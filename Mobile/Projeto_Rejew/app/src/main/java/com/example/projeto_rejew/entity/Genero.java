package com.example.projeto_rejew.entity;

import java.util.Date;

public class Genero {
    private Long idGenero;
    private String generoLivro;

    public Genero() {
    }

    public Genero(String generoLivro) {
        this.generoLivro = generoLivro;
    }

    public Long getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(Long idGenero) {
        this.idGenero = idGenero;
    }

    public String getGeneroLivro() {
        return generoLivro;
    }

    public void setGeneroLivro(String generoLivro) {
        this.generoLivro = generoLivro;
    }
}