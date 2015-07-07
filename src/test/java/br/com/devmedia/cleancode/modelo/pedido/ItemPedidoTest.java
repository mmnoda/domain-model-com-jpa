package br.com.devmedia.cleancode.modelo.pedido;

import br.com.devmedia.cleancode.modelo.Dinheiro;
import br.com.devmedia.cleancode.modelo.Quantidade;
import br.com.devmedia.cleancode.modelo.produto.Produto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static br.com.devmedia.cleancode.modelo.pedido.ItemPedido.newItemPedido;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 *
 */
public class ItemPedidoTest {

    private final Dinheiro PRECO_PADRAO_PRODUTO = Dinheiro.valueOf(49.99);

    private ItemPedido itemPedido;

    private Pedido pedido = mock(Pedido.class);
    private Produto produtoA = mock(Produto.class);
    private Produto produtoB = mock(Produto.class);

    @Before
    public void setUp() {
        when(produtoA.getPreco()).thenReturn(PRECO_PADRAO_PRODUTO);
        when(produtoB.getPreco()).thenReturn(PRECO_PADRAO_PRODUTO);
        itemPedido = newItemPedido(pedido, produtoA, Quantidade.valueOf(10));
    }

    @After
    public void tearDown() {
        reset(pedido, produtoA, produtoB);
    }

    @Test
    public void deve_ser_igual_ao_proprio() {
        assertThat(itemPedido).isEqualTo(itemPedido);
    }

    @Test
    public void deve_implementar_equals_consistente() {
        assertItemPedidoIgual();
        assertItemPedidoDiferente();
    }

    private void assertItemPedidoDiferente() {
        ItemPedido outroDiferente = newItemPedido(pedido, produtoB, Quantidade.valueOf(12));
        assertThat(itemPedido).isNotEqualTo(outroDiferente);
    }

    private void assertItemPedidoIgual() {
        ItemPedido outroIgual = newItemPedido(pedido, produtoA, Quantidade.valueOf(23));
        assertThat(itemPedido).isEqualTo(outroIgual);
    }

    @Test
    public void deve_calcular_valor_total_consistente_no_construtor() {
        assertThat(itemPedido.getValorUnitario()).isEqualTo(PRECO_PADRAO_PRODUTO);
        assertThat(itemPedido.getValorTotal()).isEqualTo(Dinheiro.valueOf(499.90));
    }

    @Test
    public void deve_manter_valor_total_consistente_apos_alterar_a_quantidade_de_itens() {
        itemPedido.setQuantidade(Quantidade.UM);
        assertThat(itemPedido.getValorTotal()).isEqualTo(PRECO_PADRAO_PRODUTO);
    }
}