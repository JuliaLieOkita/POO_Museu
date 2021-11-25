CREATE TABLE reserva (
	id int AUTO_INCREMENT NOT NULL,
	nome char(100),
	rg bigint,
	contato bigint,
	qtd_pessoas int,
	data date,
	hora_inicio char(100),
	PRIMARY KEY (id)
);