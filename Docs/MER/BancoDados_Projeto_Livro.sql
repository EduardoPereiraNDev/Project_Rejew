DROP SCHEMA projeto_App;
CREATE SCHEMA projeto_App;
USE projeto_App;

CREATE TABLE usuario (
    id_Usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome_Usuario VARCHAR(40) NOT NULL,
    nome VARCHAR(15) NOT NULL,
    email_Entrada VARCHAR(25) NOT NULL,
    senha_Entrada VARCHAR(25) NOT NULL,
    dataNascimento DATE NOT NULL,
    imagem_Usuario LONGBLOB ,
	imagem_Fundo LONGBLOB ,
    recado_Perfil VARCHAR(75),
    tipo_login ENUM('usuario_Autor', 'usuario_ADM')
);

CREATE TABLE usuario_Autor (
    id_Autor BIGINT AUTO_INCREMENT PRIMARY KEY,
    qtd_livros BIGINT,
    FOREIGN KEY (id_Autor) REFERENCES usuario(id_Usuario)
);

CREATE TABLE usuario_seguir (
	id_Relacao BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_Usuario_seguido BIGINT REFERENCES usuario(id_Usuario),
    id_Usuario_seguidor BIGINT REFERENCES usuario(id_Usuario)
);

CREATE TABLE chat(
	id_Chat BIGINT AUTO_INCREMENT PRIMARY KEY,
	genero_Chat VARCHAR(20) NOT NULL
);

CREATE TABLE mensagem_Chat(
    id_Mensagem BIGINT AUTO_INCREMENT PRIMARY KEY,
	texto_Mensagem VARCHAR(350) NOT NULL,
	FOREIGN KEY (id_Mensagem) REFERENCES chat(id_Chat),
	data_Mensagem DATE
);

CREATE TABLE livro (
    id_Livro BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_Usuario_autor BIGINT REFERENCES usuario_Autor(id_Autor),
    nome_Livro VARCHAR(40) NOT NULL,
    autor_Livro VARCHAR(15) NOT NULL,
    genero_Livro VARCHAR(20) NOT NULL,
    numero_Pag VARCHAR(25),
    qtd_Comentario BIGINT,
    ano_lancamento DATE
);

CREATE TABLE class_genero (
    id_Genero BIGINT AUTO_INCREMENT PRIMARY KEY,
	id_Livro BIGINT REFERENCES livro(id_Livro)
);


CREATE TABLE livro_Favoritado(
    id_Relacao_Livro_F BIGINT AUTO_INCREMENT PRIMARY KEY,
	id_Usuario_Favoritou BIGINT REFERENCES usuario(id_Usuario),
    id_Livro_Favoritado BIGINT REFERENCES livro(id_Livro)
);

CREATE TABLE livro_Avaliado(
    id_Relacao_Livro BIGINT AUTO_INCREMENT PRIMARY KEY,
	id_Usuario_Avaliador BIGINT REFERENCES usuario(id_Usuario),
    id_Livro_Avaliado BIGINT REFERENCES livro(id_Livro),
    valor_NotaGeral Double
);

CREATE TABLE comentario (
	id_Comentario BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_Usuario_Coment BIGINT REFERENCES usuario(id_Usuario),
    id_Livro_Coment BIGINT REFERENCES livro(id_Livro),
	data_comentario DATE NOT NULL,
    conteudo_coment VARCHAR(500)
);