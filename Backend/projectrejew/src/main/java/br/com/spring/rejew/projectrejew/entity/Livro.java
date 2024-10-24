package br.com.spring.rejew.projectrejew.entity;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;


@Entity
@Data
public class Livro implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "isbn_Livro")
    private Long isbnLivro; 

    @Column(name = "nome_Livro")
    private String nomeLivro;

    @Column(name = "autor_Livro")
    private String autorLivro;
    
    @Column(length = 10000 , name = "sinopse_Livro")
    private String sinopseLivro; 

    @Column(name = "numero_Pag")
    private int numeroPag; 

    @Column(name = "ano_Lancamento")
    private int anoLancamento; 
    
    @Column(name = "Nota_Livro")
    private int NotaLivro; 

    @Column(name = "genero_Livro")
    private String generoLivro;
    
    @Column(name = "qtd_Comentario")
    private int qtdComentario;
    
    @Column(name = "cor_Primaria")
    private String corPrimaria;

    @Column(name = "caminho_Imagem_Capa")
    private String caminhoImgCapa;
    
    @OneToMany(mappedBy = "livroComent")
    private Set<Comentario> comentario;
    
    @ManyToMany(mappedBy = "livrosA")
    Set<Usuario> usuarioA ;
    
    @ManyToMany(mappedBy = "livros")
    Set<Usuario> usuario ;
    
    public Livro() {}

    public Livro(String nomeLivro2, String autorLivro2, String sinopseLivro2, int numeroPag2, int anoLancamento2,String generoLivro2, String corPrimaria2, String caminhoImgCapa2) {
    	this.nomeLivro = nomeLivro2;
    	this.autorLivro = autorLivro2;
    	this.sinopseLivro = sinopseLivro2;
    	this.numeroPag = numeroPag2;
    	this.anoLancamento = anoLancamento2;
    	this.generoLivro = generoLivro2;
    	this.corPrimaria = corPrimaria2;
    	this.caminhoImgCapa = caminhoImgCapa2;
	}

    
}