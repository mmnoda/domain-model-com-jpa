package br.com.devmedia.cleancode.modelo.pedido;

import br.com.devmedia.cleancode.modelo.cliente.Cliente;
import br.com.devmedia.cleancode.modelo.comum.Dinheiro;
import br.com.devmedia.cleancode.modelo.comum.Quantidade;
import br.com.devmedia.cleancode.modelo.produto.Produto;
import br.com.devmedia.cleancode.spring.TesteDeIntegracao;
import br.com.devmedia.cleancode.spring.ambiente.AmbienteCliente;
import br.com.devmedia.cleancode.spring.ambiente.AmbienteNumeroPedido;
import br.com.devmedia.cleancode.spring.ambiente.AmbientePedido;
import br.com.devmedia.cleancode.spring.ambiente.AmbienteProduto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static br.com.devmedia.cleancode.modelo.pedido.ItemPedido.newItemPedido;
import static br.com.devmedia.cleancode.modelo.pedido.Pedido.newPedido;
import static br.com.devmedia.cleancode.modelo.pedido.StatusPedido.CANCELADO;
import static br.com.devmedia.cleancode.modelo.pedido.StatusPedido.FATURADO;
import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
@TesteDeIntegracao
@RunWith(SpringJUnit4ClassRunner.class)
public class PedidoIT {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private AmbienteCliente ambienteCliente;

    @Autowired
    private AmbienteNumeroPedido ambienteNumeroPedido;

    @Autowired
    private AmbienteProduto ambienteProduto;

    @Autowired
    private AmbientePedido ambientePedido;

    private Produto produto;

    private Pedido pedido;

    @Test
    @Transactional
    public void deve_faturar_novo_pedido() {
        buildPedido();
        adicionarItemPedido();
        faturar();
        em.persist(pedido);
        assertPedidoSalvo();
        assertEstadoPedidoIgualA(FATURADO);
        assertThat(pedido.getValorTotalItens()).isEqualTo(Dinheiro.valueOf(90));
    }

    @Test
    @Transactional
    public void deve_cancelar_pedido_faturado() {
        carregarPedidoFaturado();
        pedido.cancelar();
        assertEstadoPedidoIgualA(CANCELADO);
    }

    @Test
    @Transactional
    public void deve_excluir_pedido_e_seus_respectivos_itens() {
        carregarPedidoFaturado();
        assertPedidoFaturadoCom2Itens();
        em.remove(pedido);
        assertPedidoExcluido();
    }

    private void assertPedidoFaturadoCom2Itens() {
        assertThat(pedido.getItens()).isNotNull().isNotEmpty().hasSize(2);
    }

    private void faturar() {
        pedido.faturar();
    }

    private void carregarPedidoFaturado() {
        pedido = ambientePedido.getPedidoFaturado();
    }

    private void adicionarItemPedido() {
        pedido.adicionar(newItemPedido(pedido, produto, Quantidade.valueOf(15)));
    }

    private void assertEstadoPedidoIgualA(StatusPedido cancelado) {
        assertThat(pedido.getEstado()).isEqualTo(cancelado);
    }

    private void assertPedidoSalvo() {
        assertThat(pedido.getId()).isNotNull().isGreaterThan(0);
        assertThat(pedido.version).isEqualTo(0);
    }

    private void assertPedidoExcluido() {
        assertThat(ambientePedido.getPedidoFaturado()).isNull();
    }

    private void buildPedido() {
        Cliente cliente = ambienteCliente.getJoseDosSantos();
        NumeroPedido numeroPedido = ambienteNumeroPedido.getNumero1000();
        produto = ambienteProduto.getArroz();
        pedido = newPedido(cliente, numeroPedido);
    }
}
