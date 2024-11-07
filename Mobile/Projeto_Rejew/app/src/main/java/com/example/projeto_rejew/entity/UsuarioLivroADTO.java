package com.example.projeto_rejew.entity;

import java.util.Date;

public class UsuarioLivroADTO {
    private Long idUsuarioLivro;
    private Long livroISBN;
    private String livroNome;
    private String emailUsuario;
    private String nomeUsuario;
    private double notaLivro;

    public UsuarioLivroADTO() {
    }

    public UsuarioLivroADTO(Long idUsuarioLivro, Long livroISBN, String livroNome, String emailUsuario,
                            String nomeUsuario, double notaLivro) {
        this.idUsuarioLivro = idUsuarioLivro;
        this.livroISBN = livroISBN;
        this.livroNome = livroNome;
        this.emailUsuario = emailUsuario;
        this.nomeUsuario = nomeUsuario;
        this.notaLivro = notaLivro;
    }

    public Long getIdUsuarioLivro() {
        return idUsuarioLivro;
    }
    public void setIdUsuarioLivro(Long idUsuarioLivro) {
        this.idUsuarioLivro = idUsuarioLivro;
    }
    public Long getLivroISBN() {
        return livroISBN;
    }
    public void setLivroISBN(Long livroISBN) {
        this.livroISBN = livroISBN;
    }
    public String getLivroNome() {
        return livroNome;
    }
    public void setLivroNome(String livroNome) {
        this.livroNome = livroNome;
    }
    public String getEmailUsuario() {
        return emailUsuario;
    }
    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }
    public String getNomeUsuario() {
        return nomeUsuario;
    }
    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }
    public double getNotaLivro() {
        return notaLivro;
    }
    public void setNotaLivro(double notaLivro) {
        this.notaLivro = notaLivro;
    }


}
