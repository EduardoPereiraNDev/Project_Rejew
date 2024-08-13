package com.example.projeto_rejew.Recycler;

import java.util.Date;

public class Usuario {
    private Long id;
    private String nomeUsuario;
    private String nome;
    private String email;
    private String senha;
    private Date dataNascimento;
    private String caminhoimg;
    private String caminhoimgFundo;
    private String recado;
    private int qtdComent;
    private int qtdSeguidores;
    private int qtdSeguindo;

    public Usuario() {}

    public Usuario(Long id, String caminhoimg, String nome, String nomeUsuario, Date dataNascimento, String email, String telefone, String senha, String caminhoimgFundo, String recado) {
        this.id = id;
        this.caminhoimg = caminhoimg;
        this.nome = nome;
        this.nomeUsuario = nomeUsuario;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.senha = senha;
        this.caminhoimgFundo = caminhoimgFundo;
        this.recado = recado;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCaminhoimg() {
        return caminhoimg;
    }

    public void setCaminhoimg(String caminhoimg) {
        this.caminhoimg = caminhoimg;
    }
    public String getCaminhoimgFundo() {
        return caminhoimgFundo;
    }

    public void setCaminhoimgFundo(String caminhoimgFundo) {
        this.caminhoimgFundo = caminhoimgFundo;
    }

    public String getRecado() {
        return recado;
    }

    public void setRecado(String recado) {
        this.recado = recado;
    }

    public int getQtdComent() {
        return qtdComent;
    }

    public void setQtdComent(int qtdComent) {
        this.qtdComent = qtdComent;
    }

    public int getQtdSeguidores() {
        return qtdSeguidores;
    }

    public void setQtdSeguidores(int qtdSeguidores) {
        this.qtdSeguidores = qtdSeguidores;
    }

    public int getQtdSeguindo() {
        return qtdSeguindo;
    }

    public void setQtdSeguindo(int qtdSeguindo) {
        this.qtdSeguindo = qtdSeguindo;
    }
}
