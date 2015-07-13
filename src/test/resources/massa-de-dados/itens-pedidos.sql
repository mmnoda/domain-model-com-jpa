-- Pedido: 0 (Cliente: Maria da Silva)
insert into item_pedido(id_pedido, id_produto, quantidade, valor_unitario, valor_total, id) values (0, -1, 100, 5, 500, 0);
insert into item_pedido(id_pedido, id_produto, quantidade, valor_unitario, valor_total, id) values (0, -2, 50, 10, 500, -1);

-- Pedido: -1 (Cliente: Bronze)
insert into item_pedido(id_pedido, id_produto, quantidade, valor_unitario, valor_total, id) values (-1, -2, 200, 5, 1000, -2);
insert into item_pedido(id_pedido, id_produto, quantidade, valor_unitario, valor_total, id) values (-1, -3, 2, 100, 200, -3);

-- Cliente: Prata
-- Pedido: -2 (Valor itens = 2.500,00)
insert into item_pedido(id_pedido, id_produto, quantidade, valor_unitario, valor_total, id) values (-2, -2, 200, 10, 2000, -4);
insert into item_pedido(id_pedido, id_produto, quantidade, valor_unitario, valor_total, id) values (-2, -3, 5, 100, 500, -5);
-- Pedido: -3 (Valor itens = 3.500,00)
insert into item_pedido(id_pedido, id_produto, quantidade, valor_unitario, valor_total, id) values (-3, -2, 300, 10, 3000, -6);
insert into item_pedido(id_pedido, id_produto, quantidade, valor_unitario, valor_total, id) values (-3, -3, 5, 100, 500, -7);

-- Cliente: Ouro
-- Pedido: -4 (Valor itens = 6.000,00)
insert into item_pedido(id_pedido, id_produto, quantidade, valor_unitario, valor_total, id) values (-4, -2, 500, 10, 5000, -8);
insert into item_pedido(id_pedido, id_produto, quantidade, valor_unitario, valor_total, id) values (-4, -3, 5, 100, 500, -9);
insert into item_pedido(id_pedido, id_produto, quantidade, valor_unitario, valor_total, id) values (-4, -1, 100, 5, 500, -10);
-- Pedido: -5 (Valor itens = 5.000,00)
insert into item_pedido(id_pedido, id_produto, quantidade, valor_unitario, valor_total, id) values (-4, -2, 400, 10, 4000, -11);
insert into item_pedido(id_pedido, id_produto, quantidade, valor_unitario, valor_total, id) values (-4, -3, 10, 100, 1000, -12);
