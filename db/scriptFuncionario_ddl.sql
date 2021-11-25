CREATE TABLE funcionario (
	id int AUTO_INCREMENT NOT NULL,
	nome char(100),
	cpf bigint,
	senha char(100),
	contato bigint,
	email char(100),
	cargo char(100),
	turno char(12),
	PRIMARY KEY (id)
);