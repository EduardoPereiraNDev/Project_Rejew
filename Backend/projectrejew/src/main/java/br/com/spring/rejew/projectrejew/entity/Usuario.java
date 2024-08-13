package br.com.spring.rejew.projectrejew.entity;

import java.util.Date;
import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
    private String nomeUsuario;
    private String nome;
    private String email;
    private String senha;
    private Date dataNascimento;
    private String caminhoimg;
    private String caminhoimgFundo;
    private String recado;
    private int qtdComent;
    private int qtdSeguidores;
    private int qtdSeguindo;
    
    
   
}
