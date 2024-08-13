package br.com.spring.rejew.projectrejew.entity;
import java.util.Date;
import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
@Data
public class Comentario implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    private String nomeUsuario;
    private String nomeLivro;
    private Date dataRealizacao;
    
    
   
}