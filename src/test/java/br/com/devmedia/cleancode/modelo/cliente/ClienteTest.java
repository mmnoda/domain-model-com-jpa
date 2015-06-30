package br.com.devmedia.cleancode.modelo.cliente;

import br.com.devmedia.cleancode.infraestrutura.DateTimeUtils;
import br.com.devmedia.cleancode.modelo.pedido.Pedido;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.OngoingStubbing;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import static br.com.devmedia.cleancode.modelo.cliente.DescontoCliente.*;
import static br.com.devmedia.cleancode.modelo.pedido.Pedido.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 *
 */
public class ClienteTest {

    private Cliente cliente;

    private final DateTimeUtils dateTimeUtils = DateTimeUtils.getInstance();

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
        dateTimeUtils.setPadrao();
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

    @Test
    public void deve_calcular_tipo_de_cliente_BRONZE_com_base_em_seus_pedidos_faturados() {
        mockValorTotalDoPedidoFaturado1Como350();
        mockValorTotalDoPedidoFaturado2Como(BigDecimal.valueOf(150));
        assertThat(cliente.calcularTipoCliente()).isNotNull().isEqualTo(CLIENTE_BRONZE);
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

    @Test
    public void deve_verificar_se_cliente_MAIOR_de_idade() {
        cliente.setNascimento(LocalDate.of(1997, 6, 30));
        dateTimeUtils.fixar(getData30Junho2015());
        assertIdadeClienteIgualA(18);
        assertClienteMaiorDeIdade();
    }

    @Test
    public void deve_verificar_se_cliente_MENOR_de_idade() {
        cliente.setNascimento(LocalDate.of(2000, 10, 15));
        dateTimeUtils.fixar(getData30Junho2015());
        assertIdadeClienteIgualA(14);
        assertClienteMenorDeIdade();
    }

    @Test
    public void deve_manter_o_nome_do_cliente_maiusculo_sem_espacos_no_inicio_ou_no_final() {
        cliente.setNome(" João da Silva ");
        assertThat(cliente.getNome()).isNotNull().isEqualTo("JOÃO DA SILVA");
    }

    private void assertClienteMenorDeIdade() {
        assertThat(cliente.isMaiorIdade()).isFalse();
    }

    private void assertIdadeClienteIgualA(int anos) {
        assertThat(cliente.getIdade()).isEqualTo(anos);
    }

    private void assertClienteMaiorDeIdade() {
        assertThat(cliente.isMaiorIdade()).isTrue();
    }

    private LocalDateTime getData30Junho2015() {
        return LocalDateTime.of(2015, 6, 30, 0, 0);
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

    private OngoingStubbing<BigDecimal> mockValorTotalDoPedidoFaturado2Como(BigDecimal valorTotalFinal) {
        return when(pedidoFaturado2.getValorTotalFinal()).thenReturn(valorTotalFinal);
    }

    private OngoingStubbing<BigDecimal> mockValorTotalDoPedidoFaturado1Como350() {
        return when(pedidoFaturado1.getValorTotalFinal()).thenReturn(BigDecimal.valueOf(350));
    }
}