-- Insire ENDERECO
INSERT INTO ENDERECO (ID, endereco)
VALUES (1, 'Avenida 9 de julho, 707'),
(2, 'Rua Josefa Dias Correia, 65'),
(3, 'Rua Castanholas, 140');

-- Insere CLIENTE
INSERT INTO CLIENTE (ID, endereco_id)
VALUES (1, 3),
(2,1),
(3,2);

-- Insire PRODUTO
INSERT INTO PRODUTO (ID, descricao, valor)
VALUES (1, 'mouse', 50.73),
(2, 'teclado', 89.99),
(3, 'monitor', 1583.49);
