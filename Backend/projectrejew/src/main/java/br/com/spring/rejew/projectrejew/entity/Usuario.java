package br.com.spring.rejew.projectrejew.entity;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
	
	public Usuario() {
		
	}

	public Usuario(String nomeUsuario, String nomePerfil, String emailEntrada, String senhaEntrada,
			LocalDate dataNascimentoU, String uniqueFilename, String uniqueFilename2, String recadoPerfil) {
		
		this.nomeUsuario = nomeUsuario;
		this.nomePerfil = nomePerfil;
		this.emailEntrada = emailEntrada;
		this.senhaEntrada = senhaEntrada;
		this.dataNascimento = dataNascimentoU;
		this.caminhoImagem = uniqueFilename;
		this.caminhoImagemFundo = uniqueFilename2;
		this.recadoPerfil = recadoPerfil;
	}

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
    
    

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getNomePerfil() {
		return nomePerfil;
	}

	public void setNomePerfil(String nomePerfil) {
		this.nomePerfil = nomePerfil;
	}

	public String getEmailEntrada() {
		return emailEntrada;
	}

	public void setEmailEntrada(String emailEntrada) {
		this.emailEntrada = emailEntrada;
	}

	public String getSenhaEntrada() {
		return senhaEntrada;
	}

	public void setSenhaEntrada(String senhaEntrada) {
		this.senhaEntrada = senhaEntrada;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCaminhoImagem() {
		return caminhoImagem;
	}

	public void setCaminhoImagem(String caminhoImagem) {
		this.caminhoImagem = caminhoImagem;
	}

	public String getCaminhoImagemFundo() {
		return caminhoImagemFundo;
	}

	public void setCaminhoImagemFundo(String caminhoImagemFundo) {
		this.caminhoImagemFundo = caminhoImagemFundo;
	}

	public String getRecadoPerfil() {
		return recadoPerfil;
	}

	public void setRecadoPerfil(String recadoPerfil) {
		this.recadoPerfil = recadoPerfil;
	}
    

}