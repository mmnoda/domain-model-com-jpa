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

package br.com.devmedia.cleancode.modelo.cliente;

import br.com.devmedia.cleancode.modelo.comum.Dinheiro;
import br.com.devmedia.cleancode.modelo.pedido.Pedido;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static br.com.devmedia.cleancode.modelo.cliente.PedidosSet.newPedidosSet;
import static br.com.devmedia.cleancode.modelo.pedido.StatusPedido.FATURADO;
import static com.google.common.collect.Sets.newHashSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class PedidosSetTest {

    private PedidosSet pedidosSet;

    private final Pedido pedido1 = mock(Pedido.class);
    private final Pedido pedido2 = mock(Pedido.class);
    private final Pedido pedido3 = mock(Pedido.class);
    private Dinheiro total;

    @Before
    public void setUp() {
        pedidosSet = new PedidosSet();
        pedidosSet.pedidos = newHashSet(pedido1, pedido2, pedido3);
    }

    @After
    public void tearDown() {
        reset(pedido1, pedido2, pedido3);
    }

    @Test
    public void deve_ser_igual_ao_proprio() {
        assertPedidosSetIgualA(pedidosSet);
    }

    @Test
    public void deve_implementar_equals_consistente() {
        PedidosSet pedidosSetIgual = new PedidosSet();
        pedidosSetIgual.pedidos = newHashSet(pedido1, pedido2, pedido3);
        assertPedidosSetIgualA(pedidosSetIgual);
        assertPedidosSetDiferenteDe(newPedidosSet());
    }

    @Test
    public void deve_calcular_total_zero() {
        calcularTotal();
        verificarExecucaoGetEstadoDosPedidos();
        assertThat(total).isNotNull().isEqualTo(Dinheiro.ZERO);
    }

    @Test
    public void deve_calcular_total_igual_a_1200() {
        mockPedidosEstadoFaturadoEValorTotalIgualA400();
        calcularTotal();
        verificarExecucaoGetEstadoDosPedidos();
        assertThat(total).isNotNull().isEqualTo(Dinheiro.valueOf(1200));

    }

    private void mockPedidosEstadoFaturadoEValorTotalIgualA400() {
        mockEstadoFaturadoEValorTotalIgualA400DoPedido(pedido1);
        mockEstadoFaturadoEValorTotalIgualA400DoPedido(pedido2);
        mockEstadoFaturadoEValorTotalIgualA400DoPedido(pedido3);
    }

    private void mockEstadoFaturadoEValorTotalIgualA400DoPedido(Pedido pedido) {
        when(pedido.getValorTotalFinal()).thenReturn(Dinheiro.valueOf(400));
        when(pedido.getEstado()).thenReturn(FATURADO);
    }

    private void calcularTotal() {
        total = pedidosSet.calcularTotal();
    }

    private void verificarExecucaoGetEstadoDosPedidos() {
        verificarExecucaoGetEstadoDo(pedido1);
        verificarExecucaoGetEstadoDo(pedido2);
        verificarExecucaoGetEstadoDo(pedido3);
    }

    private void verificarExecucaoGetEstadoDo(Pedido pedido) {
        verify(pedido).getEstado();
    }

    private void assertPedidosSetIgualA(PedidosSet pedidosSetIgual) {
        assertThat(pedidosSet).isEqualTo(pedidosSetIgual);
        assertThat(pedidosSet.hashCode()).isEqualTo(pedidosSetIgual.hashCode());
    }

    private void assertPedidosSetDiferenteDe(PedidosSet pedidosSetDiferente) {
        assertThat(pedidosSet).isNotEqualTo(pedidosSetDiferente);
        assertThat(pedidosSet.hashCode()).isNotEqualTo(pedidosSetDiferente.hashCode());
    }
}