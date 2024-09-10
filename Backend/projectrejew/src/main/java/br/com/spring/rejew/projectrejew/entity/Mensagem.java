package br.com.spring.rejew.projectrejew.entity;

	import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Column;
	import jakarta.persistence.Entity;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
	import lombok.Data;

	@Entity
	@Data
	public class Mensagem implements Serializable {
	    private static final long serialVersionUID = 1L;
	    
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id_Mensagem")
	    private Long idMensagem;
	    
	    @Column(name = "texto_Mensagem")
	    private String textoMensagem;
	    
	    @Column(name = "data_Mensagem")
	    private Date dataMensagem;
	    
	    @Column(name = "usuario_Email_Entrada")
	    private String usuarioEmailEntrada;
	    
	    @Column(name = "chat_Mensagem")
	    private String chatMensagem;
	}
