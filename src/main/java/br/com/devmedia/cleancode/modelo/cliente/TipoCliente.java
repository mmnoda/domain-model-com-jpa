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

import br.com.devmedia.cleancode.modelo.comum.Percentual;
import br.com.devmedia.cleancode.modelo.pedido.Pedido;

import static br.com.devmedia.cleancode.modelo.cliente.DescontoClienteConstants.*;

public enum TipoCliente {

    BRONZE() {
        @Override
        public Percentual calcularPercentualDesconto(Pedido pedido) {
            return possuiValorTotalItensMaiorQue500(pedido) ?
                    _3_PORCENTO : Percentual.ZERO;
        }

        private boolean possuiValorTotalItensMaiorQue500(Pedido pedido) {
            return pedido.getValorTotalItens().compareTo(QUINHENTOS) >= 0;
        }

        @Override
        protected TipoCliente proximo() {
            return PRATA;
        }

        @Override
        protected boolean possuiRequerimentos(Cliente cliente) {
            return cliente.possuiTotalPedidosEntre(QUINHENTOS, TRES_MIL);
        }
    },

    PRATA() {
        @Override
        public Percentual calcularPercentualDesconto(Pedido pedido) {

            Percentual percentualDesconto = possuiValorTotalItensMaiorQue3000(pedido) ?
                    _5_PORCENTO : _3_PORCENTO;

            if (pedido.possuiItemComValorMaiorOuIgualQue3000()) {
                percentualDesconto = percentualDesconto.add(_3_PORCENTO);
            }

            return percentualDesconto;
        }

        private boolean possuiValorTotalItensMaiorQue3000(Pedido pedido) {
            return  pedido.getValorTotalItens().compareTo(TRES_MIL) >= 0;
        }

        @Override
        protected TipoCliente proximo() {
            return OURO;
        }

        @Override
        protected boolean possuiRequerimentos(Cliente cliente) {
            return cliente.possuiTotalPedidosEntre(TRES_MIL, DEZ_MIL);
        }
    },

    OURO() {
        @Override
        public Percentual calcularPercentualDesconto(Pedido pedido) {
            return _10_PORCENTO;
        }

        @Override
        protected TipoCliente proximo() {
            return SEM_CLASSIFICACAO;
        }

        @Override
        protected boolean possuiRequerimentos(Cliente cliente) {
            return cliente.possuiTotalPedidosMaiorQue(DEZ_MIL);
        }
    },

    SEM_CLASSIFICACAO() {
        @Override
        public Percentual calcularPercentualDesconto(Pedido pedido) {
            return Percentual.ZERO;
        }

        @Override
        protected TipoCliente proximo() {
            throw new UnsupportedOperationException();
        }

        @Override
        protected boolean possuiRequerimentos(Cliente cliente) {
            return true;
        }
    };

    public final TipoCliente identificar(Cliente cliente) {
        return possuiRequerimentos(cliente) ? this : proximo().identificar(cliente);
    }

    public abstract Percentual calcularPercentualDesconto(Pedido pedido);

    protected abstract TipoCliente proximo();

    protected abstract boolean possuiRequerimentos(Cliente cliente);

}
