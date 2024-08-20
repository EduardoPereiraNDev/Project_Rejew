package br.com.spring.rejew.projectrejew.entity;

import java.io.Serializable;
import java.util.Date;
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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Usuario")
    private Long idUsuario;

    @Column(name = "nome_Usuario")
    private String nomeUsuario;

    @Column(name = "nome_Perfil")
    private String nomePerfil;

    @Column(name = "email_Entrada")
    private String emailEntrada;

    @Column(name = "senha_Entrada")
    private String senhaEntrada;

    @Column(name = "data_Nascimento")
    private Date dataNascimento;

    @Column(name = "caminho_Imagem")
    private String caminhoImagem;

    @Column(name = "caminho_Imagem_Fundo")
    private String caminhoImagemFundo;

    @Column(name = "recado_Perfil")
    private String recadoPerfil;

    @Column(name = "tipo_Login")
    private String tipoLogin;
}