/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Márcio M. Noda
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package br.com.devmedia.cleancode.modelo.pedido;

import br.com.devmedia.cleancode.modelo.comum.Dinheiro;
import br.com.devmedia.cleancode.modelo.comum.Quantidade;
import br.com.devmedia.cleancode.modelo.produto.Produto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static br.com.devmedia.cleancode.modelo.pedido.ItemPedido.newItemPedido;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ItemPedidoTest {

    private final Dinheiro PRECO_PADRAO_PRODUTO = Dinheiro.valueOf(49.99);

    private ItemPedido itemPedido;

    private final Pedido pedido = mock(Pedido.class);
    private final Produto produtoA = mock(Produto.class);
    private final Produto produtoB = mock(Produto.class);

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
        assertItemPedidoIgual(itemPedido);
    }

    @Test
    public void deve_implementar_equals_consistente() {
        assertItemPedidoIgual(newItemPedido(pedido, produtoA, Quantidade.valueOf(23)));
        assertItemPedidoDiferente();
    }

    @Test
    public void deve_calcular_valor_total_consistente_no_construtor() {
        assertThat(itemPedido.getValorUnitario()).isEqualTo(PRECO_PADRAO_PRODUTO);
        assertThat(itemPedido.getValorTotal()).isEqualTo(Dinheiro.valueOf(499.90));
    }

    @Test
    public void deve_manter_valor_total_consistente_apos_alterar_a_quantidade_de_itens() {
        itemPedido.setQuantidade(Quantidade.UM);
        assertThat(itemPedido.getQuantidade()).isEqualTo(Quantidade.UM);
        assertThat(itemPedido.getValorTotal()).isEqualTo(PRECO_PADRAO_PRODUTO);
    }

    private void assertItemPedidoDiferente() {
        ItemPedido outroDiferente = newItemPedido(pedido, produtoB, Quantidade.valueOf(12));
        assertThat(itemPedido).isNotEqualTo(outroDiferente);
        assertThat(itemPedido.hashCode()).isNotEqualTo(outroDiferente.hashCode());
    }

    private void assertItemPedidoIgual(ItemPedido outroIgual) {
        assertThat(itemPedido).isEqualTo(outroIgual);
        assertThat(itemPedido.hashCode()).isEqualTo(outroIgual.hashCode());
    }
}