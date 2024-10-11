package br.com.spring.rejew.projectrejew.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
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
public class Comentario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Comentario")
    private Long idComentario;

    @Column(name = "data_Comentario")
    private LocalDate dataComentario;

    @Column(name = "conteudo_Coment")
    private String conteudoComent;
    
    @ManyToOne
    @JoinColumn(name="email_Usuario_Coment", nullable = false)
    private Usuario usuarioComent;
    
    @ManyToOne
    @JoinColumn(name="isbn_Livro_Coment", nullable = false)
    private Livro livroComent;
}