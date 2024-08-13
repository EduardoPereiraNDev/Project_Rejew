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
public class Livro implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long isbn;
    private Long autor_id;
    @Column(name = "nome_livro")
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
    
    
   
}
