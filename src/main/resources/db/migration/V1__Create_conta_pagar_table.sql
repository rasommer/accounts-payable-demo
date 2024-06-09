CREATE TABLE conta_pagar (
    id SERIAL PRIMARY KEY,
    data_vencimento DATE NOT NULL,
    data_pagamento DATE,
    valor NUMERIC(10, 2) NOT NULL,
    descricao VARCHAR(255),
    situacao VARCHAR(50) NOT NULL
);