package com.example.projeto_rejew.Recycler;

public class Livro {
    private Long isbn;
    private Long autor_id;
    private String nomeLivro;
    private String autor;
    private int numeroPag;
    private int qtdComent;
    private int qtdLikes;
    private String caminhoimgcapa;
    private int anoLancamento;
    private boolean favoritado;
    private String genero;
    private Double notaLivro;

    public Livro() {}

    public Livro(Long isbn, Long autorId, String nomeLivro, String autor, int numeroPag, int qtdComent, int qtdLikes, String caminhoimgcapa, int anoLancamento) {
        this.isbn = isbn;
        autor_id = autorId;
        this.nomeLivro = nomeLivro;
        this.autor = autor;
        this.numeroPag = numeroPag;
        this.qtdComent = qtdComent;
        this.qtdLikes = qtdLikes;
        this.caminhoimgcapa = caminhoimgcapa;
        this.anoLancamento = anoLancamento;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getNomeLivro() {
        return nomeLivro;
    }

    public void setNomeLivro(String nomeLivro) {
        this.nomeLivro = nomeLivro;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getNumeroPag() {
        return numeroPag;
    }

    public void setNumeroPag(int numeroPag) {
        this.numeroPag = numeroPag;
    }

    public int getQtdComent() {
        return qtdComent;
    }

    public void setQtdComent(int qtdComent) {
        this.qtdComent = qtdComent;
    }

    public int getQtdLikes() {
        return qtdLikes;
    }

    public void setQtdLikes(int qtdLikes) {
        this.qtdLikes = qtdLikes;
    }

    public int getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public boolean isFavoritado() {
        return favoritado;
    }

    public void setFavoritado(boolean favoritado) {
        this.favoritado = favoritado;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Double getNotaLivro() {
        return notaLivro;
    }

    public void setNotaLivro(Double notaLivro) {
        this.notaLivro = notaLivro;
    }

    public String getCaminhoimgcapa() {
        return caminhoimgcapa;
    }

    public void setCaminhoimgcapa(String caminhoimgcapa) {
        this.caminhoimgcapa = caminhoimgcapa;
    }

    public Long getAutor_id() {
        return autor_id;
    }

    public void setAutor_id(Long autor_id) {
        this.autor_id = autor_id;
    }
}
