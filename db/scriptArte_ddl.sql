CREATE TABLE arte (
	id int AUTO_INCREMENT NOT NULL,
	nome_obra char(100),
	nome_artista char(100),
	data_criacao date,
	descricao char(100),
	PRIMARY KEY (id)
);