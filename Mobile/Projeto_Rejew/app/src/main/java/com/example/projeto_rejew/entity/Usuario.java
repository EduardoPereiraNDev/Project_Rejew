package com.example.projeto_rejew.entity;

import java.time.LocalDate;
import java.util.Date;

public class Usuario {

    private String nomeUsuario;
    private String nomePerfil;
    private String emailEntrada;
    private String senhaEntrada;
    private String dataNascimento;
    private String caminhoImagem;
    private String caminhoImagemFundo;
    private String recadoPerfil;

    public Usuario() {

    }

    public Usuario( String emailEntrada, String senhaEntrada) {
        this.emailEntrada = emailEntrada;
        this.senhaEntrada = senhaEntrada;
    }

    public Usuario(String nome, String nomeUsuario, String email, String senha) {
        this.nomePerfil = nome;
        this.nomeUsuario = nomeUsuario;
        this.emailEntrada = email;
        this.senhaEntrada = senha;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getNomePerfil() {
        return nomePerfil;
    }

    public void setNomePerfil(String nomePerfil) {
        this.nomePerfil = nomePerfil;
    }

    public String getEmailEntrada() {
        return emailEntrada;
    }

    public void setEmailEntrada(String emailEntrada) {
        this.emailEntrada = emailEntrada;
    }

    public String getSenhaEntrada() {
        return senhaEntrada;
    }

    public void setSenhaEntrada(String senhaEntrada) {
        this.senhaEntrada = senhaEntrada;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCaminhoImagem() {
        return caminhoImagem;
    }

    public void setCaminhoImagem(String caminhoImagem) {
        this.caminhoImagem = caminhoImagem;
    }

    public String getCaminhoImagemFundo() {
        return caminhoImagemFundo;
    }

    public void setCaminhoImagemFundo(String caminhoImagemFundo) {
        this.caminhoImagemFundo = caminhoImagemFundo;
    }

    public String getRecadoPerfil() {
        return recadoPerfil;
    }

    public void setRecadoPerfil(String recadoPerfil) {
        this.recadoPerfil = recadoPerfil;
    }





}
