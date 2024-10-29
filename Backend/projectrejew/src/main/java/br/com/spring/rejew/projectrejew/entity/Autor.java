package br.com.spring.rejew.projectrejew.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuario_Autor")
@Getter
@Setter
public class Autor {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Autor")
    private Long idAutor;
    
    @Column(name = "qtd_Livros")
    private Long qtdLivros;
    
    @Column(name = "usuario_Email_Entrada")
    private String usuarioEmailEntrada;

}