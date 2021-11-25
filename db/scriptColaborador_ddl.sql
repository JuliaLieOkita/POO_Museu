CREATE TABLE colaborador (
	id int AUTO_INCREMENT NOT NULL,
	nome_instituicao char(100),
	cnpj bigint,
	valor_doado float,
	data_doacao date,
	descricao char(100),
	PRIMARY KEY (id)
);