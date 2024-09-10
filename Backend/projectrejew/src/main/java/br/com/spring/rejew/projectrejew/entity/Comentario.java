package br.com.spring.rejew.projectrejew.entity;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Comentario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Comentario")
    private Long idComentario;

    @Column(name = "data_Comentario")
    private Date dataComentario;

    @Column(name = "conteudo_Coment")
    private String conteudoComent;
    
    @Column(name = "email_Usuario_Coment")
    private Long emailUsuarioComent;
    
    @Column(name = "id_Livro_Coment")
    private Long idLivroComent;
    
    @Column(name = "Usuario_Coment")
    private String usuarioComent;
}