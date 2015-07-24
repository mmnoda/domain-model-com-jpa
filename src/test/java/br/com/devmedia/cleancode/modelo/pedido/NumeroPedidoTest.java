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

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NumeroPedidoTest {

    private NumeroPedido numeroPedido;

    @Test
    public void deve_ser_igual_ao_proprio() {
        numeroPedido = NumeroPedido.valueOf(10);
        assertNumeroPedidoIgualA(numeroPedido);
    }

    @Test
    public void deve_implementar_equals_consistente() {
        numeroPedido = NumeroPedido.valueOf(123);
        assertNumeroPedidoIgualA(NumeroPedido.valueOf(123));
        assertNumeroPedidoDiferenteDe(NumeroPedido.valueOf(456));
    }

    private void assertNumeroPedidoDiferenteDe(NumeroPedido numeroPedidoDiferente) {
        assertThat(numeroPedido).isNotEqualTo(numeroPedidoDiferente);
        assertThat(numeroPedido.hashCode()).isNotEqualTo(numeroPedidoDiferente.hashCode());
    }

    private void assertNumeroPedidoIgualA(NumeroPedido numeroPedidoIgual) {
        assertThat(numeroPedido.hashCode()).isEqualTo(numeroPedidoIgual.hashCode());
    }

}