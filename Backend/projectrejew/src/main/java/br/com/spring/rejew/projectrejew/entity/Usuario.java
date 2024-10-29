package br.com.spring.rejew.projectrejew.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Getter
@Setter
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "nome_Usuario")
    private String nomeUsuario;

    @Column(name = "nome_Perfil")
    private String nomePerfil;

    @Id
    @Column(name = "email_Entrada", updatable = false)
    private String emailEntrada;

    @Column(name = "senha_Entrada")
    private String senhaEntrada;

    @Column(name = "data_Nascimento")
    private LocalDate dataNascimento;

    @Column(name = "caminho_Imagem")
    private String caminhoImagem;

    @Column(name = "caminho_Imagem_Fundo")
    private String caminhoImagemFundo;

    @Column(name = "recado_Perfil")
    private String recadoPerfil;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "livro_favoritado",
        joinColumns = @JoinColumn(name = "usuario_favoritar"),
        inverseJoinColumns = @JoinColumn(name = "isbn_Livro")
    )
    private Set<Livro> livros;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "livro_avaliado",
        joinColumns = @JoinColumn(name = "usuario_avaliar"),
        inverseJoinColumns = @JoinColumn(name = "isbn_Livro")
    )
    private Set<Livro> livrosA;

    @OneToMany(mappedBy = "usuarioComent", fetch = FetchType.EAGER)
    @JsonManagedReference(value = "usuario_comentario")
    private Set<Comentario> comentario;

    @OneToMany(mappedBy = "usuarioMensagem", fetch = FetchType.EAGER)
    @JsonManagedReference(value = "usuario_mensagem")
    private Set<Mensagem> mensagem;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(
        name = "usuarios_seguir",
        joinColumns = @JoinColumn(name = "usuario_seguido"),
        inverseJoinColumns = @JoinColumn(name = "usuario_seguindo")
    )
    private Set<Usuario> usuariosSeguindo;

    @ManyToMany(mappedBy = "usuariosSeguindo", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Usuario> usuariosSeguido;
}
