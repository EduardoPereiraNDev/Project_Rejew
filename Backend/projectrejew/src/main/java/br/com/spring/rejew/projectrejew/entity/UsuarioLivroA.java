package br.com.spring.rejew.projectrejew.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UsuarioLivroA implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_UsuarioLivro")
    private Long idUsuarioLivro;
    
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="usuario_livro_avaliado", nullable = false)
    @JsonBackReference(value="usuario_Livro_A")
	private Usuario usuarioA;
	
	@ManyToOne
	@JoinColumn(name="livro_avaliado_isbn", nullable=false)
    @JsonBackReference(value="Livro_Avaliado") 
	private Livro livroA;
	
	@Column(name = "NotaLivro")
    private double NotaLivro;
 
    
}