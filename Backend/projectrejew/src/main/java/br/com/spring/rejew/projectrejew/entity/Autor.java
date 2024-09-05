package br.com.spring.rejew.projectrejew.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "usuario_autor")
@Data
public class Autor {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Autor")
    private Long idAutor;
    
    @Column(name = "qtd_Livros")
    private Long qtdLivros;
    
    @Column(name = "id_Usuario")
    private Long idUsuario;

}