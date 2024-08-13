package com.example.projeto_rejew.Recycler;

public class Chat {
    private Long id;
    private String genero;

    public Chat() {}

    public Chat(Long id, String genero) {
        this.id = id;
        this.genero = genero;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}