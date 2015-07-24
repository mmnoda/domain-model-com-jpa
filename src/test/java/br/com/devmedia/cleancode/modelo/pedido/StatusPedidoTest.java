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

import org.junit.After;
import org.junit.Test;

import static br.com.devmedia.cleancode.modelo.pedido.StatusPedido.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class StatusPedidoTest {

    private StatusPedido statusPedido;

    private final Pedido pedido = mock(Pedido.class);

    @After
    public void tearDown() {
        reset(pedido);
    }

    @Test
    public void deve_faturar_pedido_ABERTO() {
        setStatusAberto();
        statusPedido.faturar(pedido);
        verifyPedidoFaturado();
    }

    @Test(expected = IllegalStateException.class)
    public void deve_impedir_cancelamento_de_pedido_ABERTO() {
        setStatusAberto();
        statusPedido.cancelar(pedido);
    }

    @Test
    public void deve_permitir_inclusao_de_item_quando_pedido_ABERTO() {
        setStatusAberto();
        assertThat(statusPedido.podeIncluirNovoItem()).isTrue();
    }

    @Test
    public void deve_permitir_remocao_de_item_quando_pedido_ABERTO() {
        setStatusAberto();
        assertThat(statusPedido.podeRemoverItem()).isTrue();
    }

    @Test(expected = IllegalStateException.class)
    public void deve_impedir_faturamento_de_pedido_CANCELADO() {
        setStatusCancelado();
        statusPedido.faturar(pedido);
    }

    @Test(expected = IllegalStateException.class)
    public void deve_impedir_cancelamento_de_pedido_ja_CANCELADO() {
        setStatusCancelado();
        statusPedido.cancelar(pedido);
    }

    @Test
    public void deve_negar_a_inclusao_de_item_quando_pedido_CANCELADO() {
        setStatusCancelado();
        assertThat(statusPedido.podeIncluirNovoItem()).isFalse();
    }

    @Test
    public void deve_negar_a_remocao_de_item_quando_pedido_CANCELADO() {
        setStatusCancelado();
        assertThat(statusPedido.podeRemoverItem()).isFalse();
    }

    @Test(expected = IllegalStateException.class)
    public void deve_impedir_faturamento_de_pedido_ja_FATURADO() {
        setStatusFaturado();
        statusPedido.faturar(pedido);
    }

    @Test
    public void deve_cancelar_pedido_ja_FATURADO() {
        setStatusFaturado();
        statusPedido.cancelar(pedido);
        verify(pedido).setEstado(CANCELADO);
    }

    @Test
    public void deve_negar_a_inclusao_de_item_quando_pedido_FATURADO() {
        setStatusFaturado();
        assertThat(statusPedido.podeIncluirNovoItem()).isFalse();
    }

    @Test
    public void deve_negar_a_remocao_de_item_quando_pedido_FATURADO() {
        setStatusFaturado();
        assertThat(statusPedido.podeRemoverItem()).isFalse();
    }

    private void verifyPedidoFaturado() {
        verify(pedido).calcularValorTotalItens();
        verify(pedido).aplicarDesconto();
        verify(pedido).calcularValorFinal();
        verify(pedido).setEstado(FATURADO);
    }

    private void setStatusAberto() {
        statusPedido = ABERTO;
    }

    private void setStatusCancelado() {
        statusPedido = CANCELADO;
    }

    private void setStatusFaturado() {
        statusPedido = FATURADO;
    }
}