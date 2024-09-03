package com.example.projeto_rejew.entity;

import java.time.LocalDate;
import java.util.Date;

public class Usuario {

    private Long idUsuario;
    private String nomeUsuario;
    private String nomePerfil;
    private String emailEntrada;
    private String senhaEntrada;
    private LocalDate dataNascimento;
    private String caminhoImagem;
    private String caminhoImagemFundo;
    private String recadoPerfil;

    public Usuario() {

    }

    public Usuario(String nomeUsuario, String nomePerfil, String emailEntrada, String senhaEntrada,
                   LocalDate dataNascimentoU, String uniqueFilename, String uniqueFilename2, String recadoPerfil) {

        this.nomeUsuario = nomeUsuario;
        this.nomePerfil = nomePerfil;
        this.emailEntrada = emailEntrada;
        this.senhaEntrada = senhaEntrada;
        this.dataNascimento = dataNascimentoU;
        this.caminhoImagem = uniqueFilename;
        this.caminhoImagemFundo = uniqueFilename2;
        this.recadoPerfil = recadoPerfil;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
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

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
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
