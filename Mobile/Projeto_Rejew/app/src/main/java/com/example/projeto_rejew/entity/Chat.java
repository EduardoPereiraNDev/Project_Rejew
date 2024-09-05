package com.example.projeto_rejew.entity;

public class Chat {
    private Long idChat;
    private String generoChat;
    private String caminhoImagemFundoChat;
    private String caminhoImagemLogo;

    public Chat() {
    }

    public Chat(String generoChat, String caminhoImagemFundoChat, String caminhoImagemLogo) {
        this.generoChat = generoChat;
        this.caminhoImagemFundoChat = caminhoImagemFundoChat;
        this.caminhoImagemLogo = caminhoImagemLogo;
    }

    public Long getIdChat() {
        return idChat;
    }

    public void setIdChat(Long idChat) {
        this.idChat = idChat;
    }

    public String getGeneroChat() {
        return generoChat;
    }

    public void setGeneroChat(String generoChat) {
        this.generoChat = generoChat;
    }

    public String getCaminhoImagemFundoChat() {
        return caminhoImagemFundoChat;
    }

    public void setCaminhoImagemFundoChat(String caminhoImagemFundoChat) {
        this.caminhoImagemFundoChat = caminhoImagemFundoChat;
    }

    public String getCaminhoImagemLogo() {
        return caminhoImagemLogo;
    }

    public void setCaminhoImagemLogo(String caminhoImagemLogo) {
        this.caminhoImagemLogo = caminhoImagemLogo;
    }
}