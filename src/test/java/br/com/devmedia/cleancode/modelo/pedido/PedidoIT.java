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

    private NumeroPedido numeroPedido;

    private Cliente cliente;

    private Produto produto;

    private Pedido pedido;

    @Test
    @Transactional
    public void deve_faturar_novo_pedido() {
        setClienteJoseDosSantos();
        buildPedido();
        adicionarItemArrozAoPedido(Quantidade.valueOf(15));
        faturar();
        salvar();
        assertPedidoFaturadoComSucessoComValorTotalDosItensIgualA(Dinheiro.valueOf(90));
    }

    @Test
    @Transactional
    public void deve_cancelar_pedido_faturado() {
        carregarPedidoFaturado();
        cancelar();
        assertEstadoPedidoIgualA(CANCELADO);
    }

    @Test
    @Transactional
    public void deve_faturar_pedido_com_desconto_para_cliente_BRONZE() {
        setClienteBronze();
        buildPedido();
        adicionarItemPedidoClienteBronze();
        faturar();
        salvar();
        assertPedidoFaturadoComSucessoComValorTotalDosItensIgualA(Dinheiro.valueOf(500));
        assertValorDescontoIgualA(Dinheiro.valueOf(15));
        assertValorTotalFinalIgualA(Dinheiro.valueOf(485));
    }

    @Test
    @Transactional
    public void deve_faturar_pedido_com_desconto_de_8_porcento_para_cliente_PRATA() {
        setClientePrata();
        buildPedido();
        adicionarItensPedidoClientePrata();
        faturar();
        salvar();
        assertPedidoFaturadoComSucessoComValorTotalDosItensIgualA(Dinheiro.valueOf(3090));
        assertValorDescontoIgualA(Dinheiro.valueOf(247.20));
        assertValorTotalFinalIgualA(Dinheiro.valueOf(2842.80));
    }

    @Test
    @Transactional
    public void deve_faturar_pedido_com_desconto_para_cliente_OURO() {
        setClienteOuro();
        buildPedido();
        adicionarItensPedidoClienteOuro();
        faturar();
        salvar();
        assertPedidoFaturadoComSucessoComValorTotalDosItensIgualA(Dinheiro.valueOf(11518));
        assertValorDescontoIgualA(Dinheiro.valueOf(1151.80));
        assertValorTotalFinalIgualA(Dinheiro.valueOf(10366.20));
    }

    @Test
    @Transactional
    public void deve_excluir_pedido_e_seus_respectivos_itens() {
        carregarPedidoFaturado();
        assertPedidoFaturadoCom2Itens();
        em.remove(pedido);
        assertPedidoExcluido();
    }

    private void setClienteBronze() {
        cliente = ambienteCliente.getClienteBronze();
    }

    private void setClientePrata() {
        cliente = ambienteCliente.getClientePrata();
    }

    private void setClienteOuro() {
        cliente = ambienteCliente.getClienteOuro();
    }

    private void setClienteJoseDosSantos() {
        cliente = ambienteCliente.getJoseDosSantos();
    }

    private void assertPedidoFaturadoComSucessoComValorTotalDosItensIgualA(Dinheiro valorEsperado) {
        assertPedidoSalvo();
        assertEstadoPedidoIgualA(FATURADO);
        assertValorTotalItensIgualA(valorEsperado);
    }

    private void adicionarItensPedidoClienteOuro() {
        adicionarItemAbcAoPedido(Quantidade.valueOf(25));
        adicionarItemTvAoPedido(Quantidade.valueOf(3));
        adicionarItemArrozAoPedido(Quantidade.valueOf(3));
    }

    private void adicionarItensPedidoClientePrata() {
        adicionarItemTvAoPedido(Quantidade.UM);
        adicionarItemArrozAoPedido(Quantidade.valueOf(15));
    }

    private void adicionarItemPedidoClienteBronze() {
        adicionarItemAbcAoPedido(Quantidade.valueOf(5));
    }

    private void cancelar() {
        pedido.cancelar();
    }

    private void assertPedidoFaturadoCom2Itens() {
        ItensPedidoList itens = pedido.getItens();
        assertThat(itens).isNotNull().isNotEmpty().hasSize(2);
        itens.forEach(item -> assertThat(item.getId()).isNotNull());
    }

    private void faturar() {
        pedido.faturar();
    }

    private void carregarPedidoFaturado() {
        pedido = ambientePedido.getPedidoFaturado();
    }

    private void adicionarItemArrozAoPedido(Quantidade quantidade) {
        produto = ambienteProduto.getArroz();
        pedido.adicionar(newItemPedido(pedido, produto, quantidade));
    }

    private void adicionarItemAbcAoPedido(Quantidade quantidade) {
        produto = ambienteProduto.getAbc();
        pedido.adicionar(newItemPedido(pedido, produto, quantidade));
    }

    private void adicionarItemTvAoPedido(Quantidade quantidade) {
        produto = ambienteProduto.getTv();
        pedido.adicionar(newItemPedido(pedido, produto, quantidade));
    }

    private void assertEstadoPedidoIgualA(StatusPedido cancelado) {
        assertThat(pedido.getEstado()).isEqualTo(cancelado);
    }

    private void assertPedidoSalvo() {
        assertThat(pedido.getId()).isNotNull().isGreaterThan(0);
        assertThat(pedido.version).isNotNull().isEqualTo(0);
        assertThat(pedido.getNumero()).isNotNull().isEqualTo(numeroPedido);
        assertThat(pedido.getCliente()).isNotNull().isEqualTo(cliente);
    }

    private void assertPedidoExcluido() {
        assertThat(ambientePedido.getPedidoFaturado()).isNull();
    }

    private void buildPedido() {
        numeroPedido = ambienteNumeroPedido.getNumero1000();
        pedido = newPedido(cliente, numeroPedido);
    }

    private void salvar() {
        em.persist(pedido);
    }

    private void assertValorTotalItensIgualA(Dinheiro valorEsperado) {
        assertThat(pedido.getValorTotalItens()).isEqualTo(valorEsperado);
    }

    private void assertValorDescontoIgualA(Dinheiro valorEsperado) {
        assertThat(pedido.getDesconto()).isEqualTo(valorEsperado);
    }

    private void assertValorTotalFinalIgualA(Dinheiro valorEsperado) {
        assertThat(pedido.getValorTotalFinal()).isEqualTo(valorEsperado);
    }
}
