package br.com.devmedia.cleancode.modelo.pedido;

import br.com.devmedia.cleancode.modelo.comum.Dinheiro;
import br.com.devmedia.cleancode.modelo.comum.Quantidade;
import br.com.devmedia.cleancode.modelo.produto.Produto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static br.com.devmedia.cleancode.modelo.pedido.ItemPedido.newItemPedido;
import static br.com.devmedia.cleancode.modelo.pedido.ItensPedidoList.newItensPedidoList;
import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 *
 */
public class ItensPedidoListTest {

    private ItensPedidoList itensPedidoList;

    private final ItemPedido item1 = mock(ItemPedido.class);
    private final ItemPedido item2 = mock(ItemPedido.class);
    private final ItemPedido item3 = mock(ItemPedido.class);

    @Before
    public void setUp() {
        itensPedidoList = new ItensPedidoList();
        itensPedidoList.itens = newArrayList(item1, item2, item3);
    }

    @After
    public void tearDown() {
        reset(item1, item2, item3);
        itensPedidoList.clear();
    }

    @Test
    public void deve_ser_igual_ao_proprio() {
        assertItensPedidoListIgualA(itensPedidoList);
    }

    @Test
    public void deve_implementar_equals_consistente() {
        ItensPedidoList itensPedidoListIgual = new ItensPedidoList();
        itensPedidoListIgual.itens = itensPedidoList.itens;
        assertItensPedidoListIgualA(itensPedidoListIgual);
        assertItensPedidoListDiferenteDe(newItensPedidoList());
    }

    @Test
    public void deve_calcular_valor_total_dos_itens() {
        mockValorTotalDosItensIgualA6000();
        Dinheiro totalItens = itensPedidoList.calcularValorTotalItens();
        verifyExecucaoValorTotal();
        assertThat(totalItens).isNotNull().isEqualTo(Dinheiro.valueOf(6000));
    }

    @Test
    public void deve_encontrar_valor_item_maior_ou_igual_a_3000() {
        mockValorUnitarioDosItensIgualA6000();
        boolean possuiItemComValorMaiorOuIgualQue3000 = itensPedidoList.possuiItemComValorMaiorOuIgualQue3000();
        verifyExecucaoValorUnitarios();
        assertThat(possuiItemComValorMaiorOuIgualQue3000).isTrue();
    }

    @Test
    public void deve_NAO_encontrar_valor_item_maior_ou_igual_a_3000() {
        mockValorUnitarioDosItensIgualA3000();
        boolean possuiItemComValorMaiorOuIgualQue3000 = itensPedidoList.possuiItemComValorMaiorOuIgualQue3000();
        verifyExecucaoValorUnitarios();
        assertThat(possuiItemComValorMaiorOuIgualQue3000).isFalse();
    }

    @Test
    public void deve_adicionar_item() {
        ItemPedido item = getItemPedido();
        itensPedidoList.add(item);
        assertThat(itensPedidoList.itens.contains(item)).isTrue();
    }

    @Test
    public void deve_remover_item() {
        ItemPedido item = getItemPedido();
        itensPedidoList.itens.add(item);
        itensPedidoList.remove(item);
        assertThat(itensPedidoList.itens.contains(item)).isFalse();
    }

    private ItemPedido getItemPedido() {
        Produto produto = mock(Produto.class);
        when(produto.getPreco()).thenReturn(Dinheiro.valueOf(10));
        return newItemPedido(mock(Pedido.class), produto, Quantidade.UM);
    }

    private void verifyExecucaoValorUnitarios() {
        verify(item1).getValorUnitario();
        verify(item2).getValorUnitario();
        verify(item3).getValorUnitario();
    }

    private void mockValorUnitarioDosItensIgualA6000() {
        mockValorUnitarioItemIgualA(item1, Dinheiro.valueOf(1500));
        mockValorUnitarioItemIgualA(item2, Dinheiro.valueOf(2500));
        mockValorUnitarioItemIgualA(item3, Dinheiro.valueOf(3000));
    }

    private void mockValorUnitarioDosItensIgualA3000() {
        mockValorUnitarioItemIgualA(item1, Dinheiro.valueOf(1000));
        mockValorUnitarioItemIgualA(item2, Dinheiro.valueOf(1000));
        mockValorUnitarioItemIgualA(item3, Dinheiro.valueOf(1000));
    }

    private void verifyExecucaoValorTotal() {
        verify(item1).getValorTotal();
        verify(item2).getValorTotal();
        verify(item3).getValorTotal();
    }

    private void mockValorTotalDosItensIgualA6000() {
        mockValorTotalItemIgualA(item1, Dinheiro.valueOf(1000));
        mockValorTotalItemIgualA(item2, Dinheiro.valueOf(2000));
        mockValorTotalItemIgualA(item3, Dinheiro.valueOf(3000));
    }

    private void mockValorTotalItemIgualA(ItemPedido item, Dinheiro dinheiro) {
        when(item.getValorTotal()).thenReturn(dinheiro);
    }

    private void mockValorUnitarioItemIgualA(ItemPedido item, Dinheiro dinheiro) {
        when(item.getValorUnitario()).thenReturn(dinheiro);
    }

    private void assertItensPedidoListIgualA(ItensPedidoList itensPedidoListIgual) {
        assertThat(itensPedidoList).isEqualTo(itensPedidoListIgual);
        assertThat(itensPedidoList.hashCode()).isEqualTo(itensPedidoListIgual.hashCode());
    }

    private void assertItensPedidoListDiferenteDe(ItensPedidoList itemPedidosListDiferente) {
        assertThat(itensPedidoList).isNotEqualTo(itemPedidosListDiferente);
        assertThat(itensPedidoList.hashCode()).isNotEqualTo(itemPedidosListDiferente.hashCode());
    }
}