package br.com.devmedia.cleancode.modelo.cliente;

import br.com.devmedia.cleancode.modelo.pedido.Pedido;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.OngoingStubbing;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

import static br.com.devmedia.cleancode.modelo.cliente.DescontoCliente.CLIENTE_BRONZE;
import static br.com.devmedia.cleancode.modelo.cliente.DescontoCliente.CLIENTE_OURO;
import static br.com.devmedia.cleancode.modelo.cliente.DescontoCliente.CLIENTE_PRATA;
import static br.com.devmedia.cleancode.modelo.pedido.Pedido.ABERTO;
import static br.com.devmedia.cleancode.modelo.pedido.Pedido.CANCELADO;
import static br.com.devmedia.cleancode.modelo.pedido.Pedido.FATURADO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

/**
 *
 */
public class ClienteTest {

    private Cliente cliente;

    private Pedido pedidoFaturado1 = mock(Pedido.class);
    private Pedido pedidoFaturado2 = mock(Pedido.class);
    private Pedido pedidoCancelado = mock(Pedido.class);
    private Pedido pedidoAberto = mock(Pedido.class);

    private Set<Pedido> pedidos = new LinkedHashSet<>();

    @Before
    public void setUp() {
        cliente = new Cliente();
        vincularPedidosAoCliente();
    }

    private void vincularPedidosAoCliente() {
        cliente.setPedidos(pedidos);
        adicionarPedido(pedidoFaturado1, FATURADO);
        adicionarPedido(pedidoFaturado2, FATURADO);
        adicionarPedido(pedidoCancelado, CANCELADO);
        adicionarPedido(pedidoAberto, ABERTO);
    }

    private void adicionarPedido(Pedido pedidoMock, int faturado) {
        when(pedidoMock.getEstado()).thenReturn(faturado);
        pedidos.add(pedidoMock);
    }

    @After
    public void tearDown() {
        pedidos.clear();
        reset(pedidoFaturado1, pedidoFaturado2, pedidoCancelado, pedidoAberto);
    }

    @Test
    public void deve_ser_igual_ao_proprio() {
        cliente.setId(1);
        assertThat(cliente).isEqualTo(cliente);
    }

    @Test
    public void deve_implementar_equals_consistente() {
        cliente.setId(1);
        assertClienteIgual();
        assertClienteDiferente();
    }

    private void assertClienteDiferente() {
        Cliente outroDiferente = new Cliente();
        outroDiferente.setId(2);
        assertThat(cliente).isNotEqualTo(outroDiferente);
    }

    private void assertClienteIgual() {
        Cliente outroIgual = new Cliente();
        outroIgual.setId(1);
        assertThat(cliente).isEqualTo(outroIgual);
    }

    @Test
    public void deve_calcular_tipo_de_cliente_BRONZE_com_base_em_seus_pedidos_faturados() {
        mockValorTotalDoPedidoFaturado1Como350();
        mockValorTotalDoPedidoFaturado2Como(BigDecimal.valueOf(150));
        assertThat(cliente.calcularTipoCliente()).isNotNull().isEqualTo(CLIENTE_BRONZE);
    }

    private OngoingStubbing<BigDecimal> mockValorTotalDoPedidoFaturado2Como(BigDecimal valorTotalFinal) {
        return when(pedidoFaturado2.getValorTotalFinal()).thenReturn(valorTotalFinal);
    }

    private OngoingStubbing<BigDecimal> mockValorTotalDoPedidoFaturado1Como350() {
        return when(pedidoFaturado1.getValorTotalFinal()).thenReturn(BigDecimal.valueOf(350));
    }

    @Test
    public void deve_calcular_tipo_de_cliente_PRATA_com_base_em_seus_pedidos_faturados() {
        mockValorTotalDoPedidoFaturado1Como350();
        mockValorTotalDoPedidoFaturado2Como(BigDecimal.valueOf(2650));
        assertThat(cliente.calcularTipoCliente()).isNotNull().isEqualTo(CLIENTE_PRATA);
    }

    @Test
    public void deve_calcular_tipo_de_cliente_OURO_com_base_em_seus_pedidos_faturados() {
        when(pedidoFaturado1.getValorTotalFinal()).thenReturn(BigDecimal.valueOf(3500));
        mockValorTotalDoPedidoFaturado2Como(BigDecimal.valueOf(7500));
        assertThat(cliente.calcularTipoCliente()).isNotNull().isEqualTo(CLIENTE_OURO);
    }
}