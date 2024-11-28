package br.com.spring.rejew.projectrejew.entity;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Comentario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Comentario")
    private Long idComentario;

    
    @Column(name = "data_Comentario")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dataComentario;

    @Column(name = "conteudo_Coment")
    private String conteudoComent;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference(value = "usuario_comentario")
    @JoinColumn(name = "email_Usuario_Coment", nullable = false)
    private Usuario usuarioComent;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference(value = "livro_comentario")  
    @JoinColumn(name = "isbn_Livro_Coment", nullable = false)
    private Livro livroComent;
    
    
}
