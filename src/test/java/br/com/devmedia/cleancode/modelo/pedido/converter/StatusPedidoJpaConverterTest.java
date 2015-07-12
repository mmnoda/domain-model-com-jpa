package br.com.devmedia.cleancode.modelo.pedido.converter;

import br.com.devmedia.cleancode.modelo.pedido.StatusPedido;
import org.junit.Before;
import org.junit.Test;

import static br.com.devmedia.cleancode.modelo.pedido.StatusPedido.ABERTO;
import static br.com.devmedia.cleancode.modelo.pedido.StatusPedido.CANCELADO;
import static br.com.devmedia.cleancode.modelo.pedido.StatusPedido.FATURADO;
import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class StatusPedidoJpaConverterTest {

    private StatusPedidoJpaConverter statusPedidoJpaConverter;

    private Character prefixo;

    private StatusPedido statusPedido;

    @Before
    public void setUp() {
        statusPedidoJpaConverter = new StatusPedidoJpaConverter();
    }

    @Test
    public void deve_converter_status_ABERTO_para_prefixo() {
        prefixo = statusPedidoJpaConverter.convertToDatabaseColumn(ABERTO);
        assertPrefixoIgualA('A');
    }

    @Test
    public void deve_converter_status_FATURADO_para_prefixo() {
        prefixo = statusPedidoJpaConverter.convertToDatabaseColumn(FATURADO);
        assertPrefixoIgualA('F');
    }

    @Test
    public void deve_converter_status_CANCELADO_para_prefixo() {
        prefixo = statusPedidoJpaConverter.convertToDatabaseColumn(CANCELADO);
        assertPrefixoIgualA('C');
    }

    @Test
    public void deve_converter_prefixo_A_para_status_ABERTO() {
        statusPedido = statusPedidoJpaConverter.convertToEntityAttribute('A');
        assertThat(this.statusPedido).isNotNull().isEqualTo(ABERTO);
    }

    @Test
    public void deve_converter_prefixo_F_para_status_FATURADO() {
        statusPedido = statusPedidoJpaConverter.convertToEntityAttribute('F');
        assertThat(this.statusPedido).isNotNull().isEqualTo(FATURADO);
    }

    @Test
    public void deve_converter_prefixo_C_para_status_CANCELADO() {
        statusPedido = statusPedidoJpaConverter.convertToEntityAttribute('C');
        assertStatusPedidoIgualA(CANCELADO);
    }

    private void assertStatusPedidoIgualA(StatusPedido cancelado) {
        assertThat(this.statusPedido).isNotNull().isEqualTo(cancelado);
    }

    private void assertPrefixoIgualA(char prefixo) {
        assertThat(this.prefixo).isNotNull().isEqualTo(prefixo);
    }
}