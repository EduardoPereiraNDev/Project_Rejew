package br.com.spring.rejew.projectrejew.entity;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Chat implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Chat") 
    private Long idChat;

    @Column(name = "genero_Chat")
    private String generoChat;

    @Column(name = "caminho_Imagem_Fundo_Chat")
    private String caminhoImagemFundoChat;

    @Column(name = "caminho_Imagem_Logo")
    private String caminhoImagemLogo;
    
    public Chat() {
    	
   	}
    
    public Chat(String generoChat, String caminhoImagemLogo, String caminhoImagemFundoChat) {
    	this.generoChat = generoChat;
    	this.caminhoImagemLogo = caminhoImagemLogo;
    	this.caminhoImagemFundoChat = caminhoImagemFundoChat;
   	}

}