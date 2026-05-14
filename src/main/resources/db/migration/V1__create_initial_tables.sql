CREATE TABLE usuario (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    tipo VARCHAR(20) NOT NULL -- CLIENTE ou PRESTADOR
);

CREATE TABLE servico (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    duracao_minutos INTEGER NOT NULL
);

CREATE TABLE agendamento (
    id BIGSERIAL PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    prestador_id BIGINT NOT NULL,
    servico_id BIGINT NOT NULL,
    data_hora_inicio TIMESTAMP NOT NULL,
    data_hora_fim TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,

    CONSTRAINT fk_agendamento_cliente FOREIGN KEY (cliente_id) REFERENCES usuario(id),
    CONSTRAINT fk_agendamento_prestador FOREIGN KEY (prestador_id) REFERENCES usuario(id),
    CONSTRAINT fk_agendamento_servico FOREIGN KEY (servico_id) REFERENCES servico(id)
);