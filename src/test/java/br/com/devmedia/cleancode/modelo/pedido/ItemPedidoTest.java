package br.com.devmedia.cleancode.modelo.pedido;

import br.com.devmedia.cleancode.modelo.produto.Produto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import static br.com.devmedia.cleancode.infraestrutura.ArredondamentoConstants.ARREDONDAMENTO_PADRAO;
import static br.com.devmedia.cleancode.infraestrutura.ArredondamentoConstants.CASAS_DECIMAIS;

/**
 *
 */
public class ItemPedidoTest {

    private final BigDecimal PRECO_PADRAO_PRODUTO = BigDecimal.valueOf(49.99).setScale(CASAS_DECIMAIS,
            ARREDONDAMENTO_PADRAO);

    private ItemPedido itemPedido;

    private Pedido pedido = mock(Pedido.class);
    private Produto produto = mock(Produto.class);

    @Before
    public void setUp() {
        when(produto.getPreco()).thenReturn(PRECO_PADRAO_PRODUTO);
        itemPedido = new ItemPedido(pedido, produto, BigInteger.valueOf(10));
    }

    @After
    public void tearDown() {
        reset(pedido, produto);
    }

    @Test
    public void deve_ser_igual_ao_proprio() {
        itemPedido.setId(1);
        assertThat(itemPedido).isEqualTo(itemPedido);
    }

    @Test
    public void deve_implementar_equals_consistente() {
        itemPedido.setId(1);
        assertItemPedidoIgual();
        assertItemPedidoDiferente();
    }

    private void assertItemPedidoDiferente() {
        ItemPedido outroDiferente = new ItemPedido();
        outroDiferente.setId(2);
        assertThat(itemPedido).isNotEqualTo(outroDiferente);
    }

    private void assertItemPedidoIgual() {
        ItemPedido outroIgual = new ItemPedido();
        outroIgual.setId(1);
        assertThat(itemPedido).isEqualTo(outroIgual);
    }

    @Test
    public void deve_calcular_valor_total_consistente_no_construtor() {
        assertThat(itemPedido.getValorUnitario()).isEqualTo(PRECO_PADRAO_PRODUTO);
        assertThat(itemPedido.getValorTotal()).isEqualTo(BigDecimal.valueOf(499.90).setScale(CASAS_DECIMAIS,
                ARREDONDAMENTO_PADRAO));
    }

    @Test
    public void deve_manter_valor_total_consistente_apos_alterar_a_quantidade_de_itens() {
        itemPedido.setQuantidade(BigInteger.ONE);
        assertThat(itemPedido.getValorTotal()).isEqualTo(PRECO_PADRAO_PRODUTO);
    }
}