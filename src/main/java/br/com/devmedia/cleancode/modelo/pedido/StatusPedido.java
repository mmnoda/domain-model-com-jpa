package br.com.devmedia.cleancode.modelo.pedido;

/**
 *
 */
public enum StatusPedido {
    ABERTO(0), CANCELADO(1), FATURADO(2);

    private final int codigo;

    StatusPedido(int codigo) {
        this.codigo = codigo;
    }

    public final int getCodigo() {
        return codigo;
    }
}
