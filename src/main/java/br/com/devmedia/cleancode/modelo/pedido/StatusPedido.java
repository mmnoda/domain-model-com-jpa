package br.com.devmedia.cleancode.modelo.pedido;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

import java.util.Map;

/**
 *
 */
public enum StatusPedido {

    ABERTO('A') {
        @Override
        public void faturar(Pedido pedido) {
            pedido.calcularValorTotalItens();
            pedido.aplicarDesconto();
            pedido.calcularValorFinal();
            pedido.setEstado(FATURADO);
        }

        @Override
        public void cancelar(Pedido pedido) {
            throw new IllegalStateException("Pedido não está faturado");
        }

        @Override
        public boolean podeIncluirNovoItem() {
            return true;
        }

        @Override
        public boolean podeRemoverItem() {
            return true;
        }
    },

    CANCELADO('C') {
        @Override
        public void faturar(Pedido pedido) {
            throw new IllegalStateException("Pedido não está aberto");
        }

        @Override
        public void cancelar(Pedido pedido) {
            throw new IllegalStateException("Pedido já cancelado");
        }

        @Override
        public boolean podeIncluirNovoItem() {
            return false;
        }

        @Override
        public boolean podeRemoverItem() {
            return false;
        }
    },
    FATURADO('F') {
        @Override
        public void faturar(Pedido pedido) {
            throw new IllegalStateException("Pedido já faturado");
        }

        @Override
        public void cancelar(Pedido pedido) {
            pedido.setEstado(CANCELADO);
        }

        @Override
        public boolean podeIncluirNovoItem() {
            return false;
        }

        @Override
        public boolean podeRemoverItem() {
            return false;
        }
    };

    private final char prefixo;

    private final static Map<Character, StatusPedido> statusMap;

    static {
        Builder<Character, StatusPedido> builder = ImmutableMap.<Character, StatusPedido>builder();

        StatusPedido[] values = values();
        for (StatusPedido statusPedido : values) {
            builder.put(statusPedido.getPrefixo(), statusPedido);
        }
        statusMap = builder.build();
    }

    StatusPedido(char prefixo) {
        this.prefixo = prefixo;
    }

    public static StatusPedido byPrefixo(Character prefixo) {
        return statusMap.get(prefixo);
    }

    public final char getPrefixo() {
        return prefixo;
    }

    public abstract void faturar(Pedido pedido);

    public abstract void cancelar(Pedido pedido);

    public abstract boolean podeIncluirNovoItem();

    public abstract boolean podeRemoverItem();
}
