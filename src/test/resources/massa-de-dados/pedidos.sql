-- Cliente: Maria da Silva
insert into pedido(numero, id_cliente, estado, valor_total_itens, desconto, valor_total_final, version, id)
           values (100, -1, 'F', 1000, 0, 1000, 0, 0);

-- Cliente: Bronze
insert into pedido(numero, id_cliente, estado, valor_total_itens, desconto, valor_total_final, version, id)
           values (200, -2, 'F', 1200, 0, 1200, 0, -1);

-- Cliente: Prata
insert into pedido(numero, id_cliente, estado, valor_total_itens, desconto, valor_total_final, version, id)
           values (300, -3, 'F', 2500, 0, 2500, 0, -2);
insert into pedido(numero, id_cliente, estado, valor_total_itens, desconto, valor_total_final, version, id)
           values (400, -3, 'F', 3500, 105, 3395, 0, -3);

-- Cliente: Ouro
insert into pedido(numero, id_cliente, estado, valor_total_itens, desconto, valor_total_final, version, id)
           values (500, -4, 'F', 6000, 0, 6000, 0, -4);
insert into pedido(numero, id_cliente, estado, valor_total_itens, desconto, valor_total_final, version, id)
           values (600, -4, 'F', 5000, 400, 4600, 0, -5);