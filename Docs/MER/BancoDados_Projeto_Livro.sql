-- DROP SCHEMA IF EXISTS teste_projeto;
CREATE SCHEMA IF NOT EXISTS teste_projeto;
USE teste_projeto;

INSERT INTO `teste_projeto`.`adm` (`credencial`, `senhaadm`) VALUES ('8713', '8713');
INSERT INTO `teste_projeto`.`adm` (`credencial`, `senhaadm`) VALUES ('3187', '3187');
INSERT INTO `teste_projeto`.`adm` (`credencial`, `senhaadm`) VALUES ('1378', '1378');
INSERT INTO `teste_projeto`.`adm` (`credencial`, `senhaadm`) VALUES ('7836', '7836');
 
 

CREATE TABLE IF NOT EXISTS usuario (
	email_Entrada VARCHAR(50) NOT NULL PRIMARY KEY,
    nome_Usuario VARCHAR(40) NOT NULL,
    nome_Perfil VARCHAR(15) NOT NULL,
    senha_Entrada VARCHAR(25) NOT NULL,
    data_Nascimento DATE NOT NULL,
    caminho_Imagem VARCHAR(255) ,
	caminho_Imagem_Fundo VARCHAR(255) ,
    recado_Perfil VARCHAR(75)
);

CREATE TABLE IF NOT EXISTS usuario_Autor (
    id_Autor BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_Email_Entrada varchar(50) NOT NULL,
    qtd_Livros BIGINT,
    FOREIGN KEY (usuario_Email_Entrada) REFERENCES usuario(email_Entrada)
);

CREATE TABLE IF NOT EXISTS usuario_seguir (
	id_Relacao BIGINT AUTO_INCREMENT PRIMARY KEY,
    email_Entrada_Seguido VARCHAR(50) REFERENCES usuario(email_Entrada),
    email_Entrada_Seguidor VARCHAR(50) REFERENCES usuario(email_Entrada)
);

CREATE TABLE IF NOT EXISTS chat(
	id_Chat BIGINT AUTO_INCREMENT PRIMARY KEY,
    caminho_Imagem_Fundo_Chat VARCHAR(255),
    caminho_Imagem_Logo VARCHAR(255),
	genero_Chat VARCHAR(25) NOT NULL
);

CREATE TABLE IF NOT EXISTS mensagem(
    id_Mensagem BIGINT AUTO_INCREMENT PRIMARY KEY,
	texto_Mensagem VARCHAR(350) NOT NULL,
	usuario_Email_Entrada VARCHAR(50) NOT NULL,
    chat_Mensagem BIGINT,
    FOREIGN KEY (usuario_Email_Entrada) REFERENCES usuario(email_Entrada),
	FOREIGN KEY (chat_Mensagem) REFERENCES chat(id_Chat),
	data_Mensagem DATE
);

CREATE TABLE IF NOT EXISTS livro (
    isbn_Livro BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_Usuario_autor VARCHAR(50) REFERENCES usuario_Autor(id_Autor),
    nome_Livro VARCHAR(40) NOT NULL,
    autor_Livro VARCHAR(15) NOT NULL,
	sinopse_Livro VARCHAR(255) NOT NULL,
    genero_Livro VARCHAR(20) NOT NULL,
    caminho_Imagem_Capa	VARCHAR(255),
    cor_Primaria VARCHAR(15),
    numero_Pag BIGINT,
    Nota_Livro BIGINT,
    qtd_Comentario BIGINT,
    ano_Lancamento DATE
);

CREATE TABLE IF NOT EXISTS genero (
    id_Genero BIGINT AUTO_INCREMENT PRIMARY KEY,
	isbn_Livro BIGINT REFERENCES livro(isbn_Livro)
);


CREATE TABLE IF NOT EXISTS livro_Favoritado(
    id_Relacao_Livro_F BIGINT AUTO_INCREMENT PRIMARY KEY,
	email_Entrada_Favoritou VARCHAR(50) REFERENCES usuario(email_Entrada),
    isbn_Livro_Favoritado BIGINT REFERENCES livro(isbn_Livro)
);

CREATE TABLE IF NOT EXISTS livro_Avaliado(
    id_Relacao_Livro BIGINT AUTO_INCREMENT PRIMARY KEY,
	usuario_Email_Avaliador VARCHAR(50) REFERENCES usuario(email_Entrada),
    isbn_Livro_Avaliado BIGINT REFERENCES livro(isbn_Livro),
    valor_Nota Double
);

CREATE TABLE IF NOT EXISTS comentario (
	id_Comentario BIGINT AUTO_INCREMENT PRIMARY KEY,
    email_Usuario_Coment VARCHAR(50) REFERENCES usuario(email_Entrada),
    isbn_Livro_Coment BIGINT REFERENCES livro(isbn_Livro),
	data_comentario DATE NOT NULL,
    conteudo_Coment VARCHAR(500)
);