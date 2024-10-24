package br.com.spring.rejew.projectrejew.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "usuario")
@Data
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
    
    @ManyToMany
    @JoinTable(name="livro_favoritado",
    joinColumns=@JoinColumn(name="usuario_favoritar"),
    inverseJoinColumns = @JoinColumn(name="isbn_Livro"))
    private Set<Livro> livros;
    
    @ManyToMany
    @JoinTable(name="livro_avaliado",
    joinColumns=@JoinColumn(name="usuario_avaliar"),
    inverseJoinColumns = @JoinColumn(name="isbn_Livro"))
    private Set<Livro> livrosA;
    
    @OneToMany(mappedBy = "usuarioComent")
    private Set<Comentario> comentario;
    
    @OneToMany(mappedBy = "usuarioMensagem")
    private Set<Mensagem> mensagem;
    
    @ManyToMany
    @JoinTable(name="usuarios_seguir",
    joinColumns = @JoinColumn(name = "usuario_seguido"),
    inverseJoinColumns = @JoinColumn(name="usuario_seguindo"))
    private Set<Usuario> usuariosSeguindo;
    
    @ManyToMany(mappedBy = "usuariosSeguindo")
    private Set<Usuario> usuariosSeguido;

    

}