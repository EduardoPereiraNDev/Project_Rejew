package com.example.projeto_rejew.entity;

public class Livro {
    private Long isbnLivro;
    private String nomeLivro;
    private String autorLivro;
    private String sinopseLivro;
    private int numeroPag;
    private int anoLancamento;
    private int NotaLivro;
    private String generoLivro;
    private int qtdComentario;
    private String corPrimaria;
    private String caminhoImgCapa;

    public Livro() {
    }

    public Livro(String nomeLivro, String autorLivro, int numeroPag, int anoLancamento, int notaLivro, String generoLivro, int qtdComentario, String corPrimaria, String caminhoImgCapa, String sinopseLivro) {
        this.nomeLivro = nomeLivro;
        this.autorLivro = autorLivro;
        this.numeroPag = numeroPag;
        this.anoLancamento = anoLancamento;
        NotaLivro = notaLivro;
        this.generoLivro = generoLivro;
        this.qtdComentario = qtdComentario;
        this.corPrimaria = corPrimaria;
        this.caminhoImgCapa = caminhoImgCapa;
        this.sinopseLivro = sinopseLivro;
    }

    public Long getIsbnLivro() {
        return isbnLivro;
    }

    public void setIsbnLivro(Long isbnLivro) {
        this.isbnLivro = isbnLivro;
    }

    public String getNomeLivro() {
        return nomeLivro;
    }

    public void setNomeLivro(String nomeLivro) {
        this.nomeLivro = nomeLivro;
    }

    public String getAutorLivro() {
        return autorLivro;
    }

    public void setAutorLivro(String autorLivro) {
        this.autorLivro = autorLivro;
    }

    public int getNumeroPag() {
        return numeroPag;
    }

    public void setNumeroPag(int numeroPag) {
        this.numeroPag = numeroPag;
    }

    public int getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public int getNotaLivro() {
        return NotaLivro;
    }

    public void setNotaLivro(int notaLivro) {
        NotaLivro = notaLivro;
    }

    public String getGeneroLivro() {
        return generoLivro;
    }

    public void setGeneroLivro(String generoLivro) {
        this.generoLivro = generoLivro;
    }

    public int getQtdComentario() {
        return qtdComentario;
    }

    public void setQtdComentario(int qtdComentario) {
        this.qtdComentario = qtdComentario;
    }

    public String getCorPrimaria() {
        return corPrimaria;
    }

    public void setCorPrimaria(String corPrimaria) {
        this.corPrimaria = corPrimaria;
    }

    public String getCaminhoImgCapa() {
        return caminhoImgCapa;
    }

    public void setCaminhoImgCapa(String caminhoImgCapa) {
        this.caminhoImgCapa = caminhoImgCapa;
    }

    public String getSinopseLivro(){return sinopseLivro; }

    public void setSinopseLivro(String sinopseLivro){ this.sinopseLivro = sinopseLivro;}
}
