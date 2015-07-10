package br.com.devmedia.cleancode.modelo.pedido;

import br.com.devmedia.cleancode.modelo.cliente.Cliente;
import br.com.devmedia.cleancode.modelo.comum.Quantidade;
import br.com.devmedia.cleancode.modelo.produto.Produto;
import br.com.devmedia.cleancode.spring.TesteDeIntegracao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static br.com.devmedia.cleancode.modelo.pedido.ItemPedido.newItemPedido;
import static br.com.devmedia.cleancode.modelo.pedido.Pedido.newPedido;

/**
 *
 */
@TesteDeIntegracao
@RunWith(SpringJUnit4ClassRunner.class)
public class PedidoIT {

    @PersistenceContext
    private EntityManager em;

    private Pedido pedido;

    @Test
    @Sql({"/massa-de-dados/clientes.sql", "/massa-de-dados/numero-pedidos.sql", "/massa-de-dados/produtos.sql"})
    @Transactional
    public void deve_faturar_pedido() {
        Cliente cliente = em.find(Cliente.class, -1);
        NumeroPedido numeroPedido = em.find(NumeroPedido.class, 1000);
        Produto produto = em.find(Produto.class, 0);
        pedido = newPedido(cliente, numeroPedido);
        pedido.adicionar(newItemPedido(pedido, produto, Quantidade.valueOf(15)));
        pedido.faturar();
        em.persist(pedido);
    }
}
