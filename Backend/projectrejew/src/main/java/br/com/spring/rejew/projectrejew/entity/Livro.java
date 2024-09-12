package br.com.spring.rejew.projectrejew.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    
    @Column(name = "sinopse_Livro")
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

    public Livro() {}

	public Livro(String nomeLivro, String autorLivro, String sinopseLivro , int numeroPag, int anoLancamento,
			 String generoLivro, String corPrimaria, String caminhoImgCapa) {
		this.nomeLivro = nomeLivro;
		this.autorLivro = autorLivro;
		this.numeroPag = numeroPag;
		this.sinopseLivro = sinopseLivro;
		this.anoLancamento = anoLancamento;
		this.generoLivro = generoLivro;
		this.corPrimaria = corPrimaria;
		this.caminhoImgCapa = caminhoImgCapa;
	}

    
}