package br.com.devmedia.cleancode.modelo.cliente;

import br.com.devmedia.cleancode.modelo.Dinheiro;
import br.com.devmedia.cleancode.modelo.Percentual;
import br.com.devmedia.cleancode.modelo.pedido.Pedido;

/**
 *
 */
public enum TipoCliente {

    BRONZE(0) {
        @Override
        public Percentual calcularPercentualDesconto(Pedido pedido) {
            return pedido.getValorTotalItens().compareTo(DescontoCliente.QUINHENTOS) >= 0 ?
                    DescontoCliente._3_PORCENTO : Percentual.ZERO;
        }

        @Override
        protected TipoCliente proximo() {
            return PRATA;
        }

        @Override
        protected boolean possuiRequerimentos(Cliente cliente) {
            return cliente.possuiTotalPedidosEntre(DescontoCliente.QUINHENTOS, DescontoCliente.TRES_MIL);
        }
    },

    PRATA(1) {
        @Override
        public Percentual calcularPercentualDesconto(Pedido pedido) {

            Dinheiro valorTotalItens = pedido.getValorTotalItens();

            Percentual percentualDesconto = valorTotalItens.compareTo(DescontoCliente.TRES_MIL) >= 0 ?
                    DescontoCliente._5_PORCENTO : DescontoCliente._3_PORCENTO;

            if (pedido.possuiItemComValorMaiorOuIgualQue3000()) {
                percentualDesconto = percentualDesconto.add(DescontoCliente._3_PORCENTO);
            }

            return percentualDesconto;
        }

        @Override
        protected TipoCliente proximo() {
            return OURO;
        }

        @Override
        protected boolean possuiRequerimentos(Cliente cliente) {
            return cliente.possuiTotalPedidosEntre(DescontoCliente.TRES_MIL, DescontoCliente.DEZ_MIL);
        }
    },

    OURO(2) {
        @Override
        public Percentual calcularPercentualDesconto(Pedido pedido) {
            return DescontoCliente._10_PORCENTO;
        }

        @Override
        protected TipoCliente proximo() {
            return SEM_CLASSIFICACAO;
        }

        @Override
        protected boolean possuiRequerimentos(Cliente cliente) {
            return cliente.possuiTotalPedidosMaiorQue(DescontoCliente.DEZ_MIL);
        }
    },

    SEM_CLASSIFICACAO(-1) {
        @Override
        public Percentual calcularPercentualDesconto(Pedido pedido) {
            return Percentual.ZERO;
        }

        @Override
        protected TipoCliente proximo() {
            throw new UnsupportedOperationException("");
        }

        @Override
        protected boolean possuiRequerimentos(Cliente cliente) {
            return true;
        }
    };

    private final int codigo;

    TipoCliente(int codigo) {
        this.codigo = codigo;
    }

    public final int getCodigo() {
        return codigo;
    }

    public final TipoCliente identificar(Cliente cliente) {
        return possuiRequerimentos(cliente) ? this : proximo().identificar(cliente);
    }

    public abstract Percentual calcularPercentualDesconto(Pedido pedido);

    protected abstract TipoCliente proximo();

    protected abstract boolean possuiRequerimentos(Cliente cliente);

}
