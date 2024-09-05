package com.example.projeto_rejew.entity;

public class Autor {
    private Long idAutor;
    private Long qtdLivros;
    private Long idUsuario;

    public Autor() {
    }

    public Autor(Long qtdLivros, Long idUsuario) {
        this.qtdLivros = qtdLivros;
        this.idUsuario = idUsuario;
    }

    public Long getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Long idAutor) {
        this.idAutor = idAutor;
    }

    public Long getQtdLivros() {
        return qtdLivros;
    }

    public void setQtdLivros(Long qtdLivros) {
        this.qtdLivros = qtdLivros;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}
