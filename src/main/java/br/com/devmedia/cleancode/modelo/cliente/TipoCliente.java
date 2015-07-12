package br.com.devmedia.cleancode.modelo.cliente;

import br.com.devmedia.cleancode.modelo.comum.Dinheiro;
import br.com.devmedia.cleancode.modelo.comum.Percentual;
import br.com.devmedia.cleancode.modelo.pedido.Pedido;

import static br.com.devmedia.cleancode.modelo.cliente.DescontoClienteConstants.*;

/**
 *
 */
public enum TipoCliente {

    BRONZE() {
        @Override
        public Percentual calcularPercentualDesconto(Pedido pedido) {
            return pedido.getValorTotalItens().compareTo(QUINHENTOS) >= 0 ?
                    _3_PORCENTO : Percentual.ZERO;
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
            Dinheiro valorTotalItens = pedido.getValorTotalItens();

            Percentual percentualDesconto = (valorTotalItens.compareTo(TRES_MIL) >= 0) ?
                    _5_PORCENTO : _3_PORCENTO;

            if (pedido.possuiItemComValorMaiorOuIgualQue3000()) {
                percentualDesconto = percentualDesconto.add(_3_PORCENTO);
            }

            return percentualDesconto;
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
