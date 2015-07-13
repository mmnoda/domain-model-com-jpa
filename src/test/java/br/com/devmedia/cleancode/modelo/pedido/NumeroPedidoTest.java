package br.com.devmedia.cleancode.modelo.pedido;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
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