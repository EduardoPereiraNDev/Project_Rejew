package br.com.spring.rejew.projectrejew.entity;

	import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.Column;
	import jakarta.persistence.Entity;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	    private String dataMensagem;
	    
	    @ManyToOne
	    @JoinColumn(name="usuario_Email_Entrada", nullable = false)
	    private Usuario usuarioMensagem;
	    
	    @ManyToOne
	    @JoinColumn(name="id_Chat", nullable = false)
	    private Chat chatMensagem;
	}
