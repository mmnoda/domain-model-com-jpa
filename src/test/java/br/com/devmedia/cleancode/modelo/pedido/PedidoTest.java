package br.com.devmedia.cleancode.modelo.pedido;

import br.com.devmedia.cleancode.modelo.Dinheiro;
import br.com.devmedia.cleancode.modelo.cliente.Cliente;
import br.com.devmedia.cleancode.modelo.cliente.TipoCliente;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static br.com.devmedia.cleancode.modelo.cliente.TipoCliente.*;
import static br.com.devmedia.cleancode.modelo.pedido.Pedido.newPedido;
import static br.com.devmedia.cleancode.modelo.pedido.StatusPedido.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 *
 */
public class PedidoTest {

    private Pedido pedido;

    private Cliente cliente = mock(Cliente.class);

    private final Dinheiro valorTotalItem1 = Dinheiro.valueOf(499);
    private final Dinheiro valorTotalItem2 = Dinheiro.valueOf(2000);
    private final Dinheiro valorTotalItem3 = Dinheiro.valueOf(600);
    private final Dinheiro valorTotalItem4 = Dinheiro.valueOf(3000);
    private final Dinheiro somaItens1e2 = valorTotalItem1.add(valorTotalItem2);
    private final Dinheiro somaItens1e2e3 = valorTotalItem1.add(valorTotalItem2).add(valorTotalItem3);
    private final Dinheiro somaTodosItens = somaItens1e2e3.add(valorTotalItem4);

    private ItemPedido item1 = mock(ItemPedido.class);
    private ItemPedido item2 = mock(ItemPedido.class);
    private ItemPedido item3 = mock(ItemPedido.class);
    private ItemPedido item4 = mock(ItemPedido.class);

    private NumeroPedido numero = mock(NumeroPedido.class);

    private List<ItemPedido> itens;

    @Before
    public void setUp() {
        pedido = newPedido(cliente, numero);
        mockValoresItemPedido();
    }

    private void mockValoresItemPedido() {
        mockValoresItemPedido(item1, valorTotalItem1);
        mockValoresItemPedido(item2, valorTotalItem2);
        mockValoresItemPedido(item3, valorTotalItem3);
        mockValoresItemPedido(item4, valorTotalItem4);
    }

    private void mockValoresItemPedido(ItemPedido item, Dinheiro valorTotalItem) {
        when(item.getValorTotal()).thenReturn(valorTotalItem);
        when(item.getValorUnitario()).thenReturn(valorTotalItem);
    }

    @After
    public void tearDown() {
        reset(cliente, item1, item2, item3, item4);
    }

    @Test
    public void deve_ser_igual_ao_proprio() {
        pedido.setId(1);
        assertThat(pedido).isEqualTo(pedido);
    }

    @Test
    public void deve_implementar_equals_consistente() {
        pedido.setId(1);
        assertPedidoIgual();
        assertPedidoDiferente();
    }

    private void assertPedidoDiferente() {
        Pedido outroDiferente = new Pedido();
        outroDiferente.numero = mock(NumeroPedido.class);
        assertThat(pedido).isNotEqualTo(outroDiferente);
    }

    private void assertPedidoIgual() {
        Pedido outroIgual = new Pedido();
        outroIgual.numero = numero;
        assertThat(pedido).isEqualTo(outroIgual);
    }

    @Test
    public void deve_adicionar_item() {
        pedido.adicionar(item1).adicionar(item2).adicionar(item3);
        assertThat(pedido.getItens()).contains(item1, item2, item3);
    }

    @Test
    public void deve_remover_itens() {
        adicionarItens1e2AoPedido();
        pedido.remover(item2);
        assertThat(pedido.getItens()).hasSize(1).contains(item1).doesNotContain(item2, item3);
    }

    @Test
    public void deve_faturar_pedido_aberto() {
        setEstadoPedidoAberto();
        adicionarTodosItensAoPedido();
        mockCalcularTipoClienteComRetornoVazio();
        faturar();
        verificarExecucaoGetValorTotalDosItens1e2e3();
        assertThat(pedido.getEstado()).isEqualTo(FATURADO);
        assertValorTotalItensPedidoIgualAoValorDe(somaTodosItens);
        assertValorTotalFinalPedidoIgualAoValorDe(somaTodosItens);
    }

    private void mockCalcularTipoClienteComRetornoVazio() {
        when(cliente.calcularTipoCliente()).thenReturn(SEM_CLASSIFICACAO);
    }

    @Test(expected = IllegalStateException.class)
    public void deve_impedir_o_faturamento_de_pedido_ja_faturado() {
        setEstadoPedidoFaturado();
        faturar();
    }

    private void setEstadoPedidoFaturado() {
        pedido.estado = FATURADO;
    }

    @Test(expected = IllegalStateException.class)
    public void deve_impedir_o_faturamento_de_pedido_cancelado() {
        setEstadoPedidoCancelado();
        faturar();
    }

    private void setEstadoPedidoCancelado() {
        pedido.estado = CANCELADO;
    }

    @Test
    public void deve_cancelar_pedido_faturado() {
        setEstadoPedidoFaturado();
        pedido.cancelar();
        assertThat(pedido.getEstado()).isEqualTo(CANCELADO);
    }

    @Test(expected = IllegalStateException.class)
    public void deve_impedir_o_cancelamento_de_pedido_aberto() {
        setEstadoPedidoAberto();
        pedido.cancelar();
    }

    private void setEstadoPedidoAberto() {
        pedido.estado = ABERTO;
    }

    @Test(expected = IllegalStateException.class)
    public void deve_impedir_o_cancelamento_de_pedido_ja_cancelado() {
        setEstadoPedidoCancelado();
        pedido.cancelar();
    }

    @Test
    public void deve_aplicar_desconto_zero_para_cliente_sem_historico_de_compras() {
        setEstadoPedidoAberto();
        adicionarTodosItensAoPedido();
        mockCalcularTipoClienteComRetornoVazio();
        faturar();
        verificarExecucaoGetValorTotalDosItens1e2e3();
        assertDescontoPedidoZero();
        assertValorTotalFinalIgualASomaDeTodosOsItens();
    }

    private void faturar() {
        pedido.faturar();
    }

    @Test
    public void deve_aplicar_desconto_ZERO_para_cliente_tipo_BRONZE_com_pedido_inferior_a_500() {
        setEstadoPedidoAberto();
        adicionarSomenteItem1AoPedido();
        mockCalcularTipoClienteComo(BRONZE);
        faturar();
        verificarExecucaoCalcularTipoCliente();
        verificarExecucaoGetValorTotalSomenteDo(item1);
        assertValorTotalItensPedidoIgualAoValorItem1();
        assertDescontoPedidoZero();
        assertValorTotalFinalPedidoIgualAoValorDe(valorTotalItem1);
    }

    @Test
    public void deve_aplicar_desconto_de_3_porcento_para_cliente_tipo_BRONZE_com_pedido_acima_de_500() {
        setEstadoPedidoAberto();
        adicionarTodosItensAoPedido();
        mockCalcularTipoClienteComo(BRONZE);
        faturar();
        verificarExecucaoCalcularTipoCliente();
        verificarExecucaoGetValorTotalDosItens1e2e3();
        assertValorTotalItensPedidoIgualAoValorDe(somaTodosItens);
        assertDescontoPedidoIgualAoValorDe(Dinheiro.valueOf(182.97));
        assertValorTotalFinalPedidoIgualAoValorDe(Dinheiro.valueOf(5916.03));
    }

    @Test
    public void deve_aplicar_desconto_de_3_porcento_para_cliente_tipo_PRATA_com_pedido_inferior_a_3000() {
        setEstadoPedidoAberto();
        adicionarItens1e2AoPedido();
        mockCalcularTipoClienteComo(PRATA);
        faturar();
        verificarExecucaoCalcularTipoCliente();
        verificarExecucaoGetValorTotalDosItens1e2();
        assertValorTotalItensPedidoIgualAoValorDe(somaItens1e2);
        assertDescontoPedidoIgualAoValorDe(Dinheiro.valueOf(74.97));
        assertValorTotalFinalPedidoIgualAoValorDe(Dinheiro.valueOf(2424.03));
    }

    @Test
    public void deve_aplicar_desconto_de_5_porcento_para_cliente_tipo_PRATA_com_pedido_superior_a_3000() {
        setEstadoPedidoAberto();
        adicionarItens1e2e3AoPedido();
        mockCalcularTipoClienteComo(PRATA);
        faturar();
        verificarExecucaoCalcularTipoCliente();
        verificarExecucaoGetValorTotalDosItens1e2e3();
        assertValorTotalItensPedidoIgualAoValorDe(somaItens1e2e3);
        assertDescontoPedidoIgualAoValorDe(Dinheiro.valueOf(154.95));
        assertValorTotalFinalPedidoIgualAoValorDe(Dinheiro.valueOf(2944.05));
    }

    @Test
    public void deve_aplicar_desconto_de_8_porcento_para_cliente_tipo_PRATA_com_pedido_superior_a_5000() {
        setEstadoPedidoAberto();
        adicionarTodosItensAoPedido();
        mockCalcularTipoClienteComo(PRATA);
        faturar();
        verificarExecucaoCalcularTipoCliente();
        verificarExecucaoGetValorTotalDeTodosItens();
        assertValorTotalItensPedidoIgualAoValorDe(somaTodosItens);
        assertDescontoPedidoIgualAoValorDe(Dinheiro.valueOf(487.92));
        assertValorTotalFinalPedidoIgualAoValorDe(Dinheiro.valueOf(5611.08));
    }

    @Test
    public void deve_aplicar_desconto_de_10_porcento_para_cliente_tipo_OURO_com_pedido_inferior_a_500() {
        setEstadoPedidoAberto();
        adicionarSomenteItem1AoPedido();
        mockCalcularTipoClienteComo(OURO);
        faturar();
        verificarExecucaoCalcularTipoCliente();
        verificarExecucaoGetValorTotalSomenteDo(item1);
        assertValorTotalItensPedidoIgualAoValorItem1();
        assertDescontoPedidoIgualAoValorDe(Dinheiro.valueOf(49.90));
        assertValorTotalFinalPedidoIgualAoValorDe(Dinheiro.valueOf(449.10));
    }

    private void assertDescontoPedidoIgualAoValorDe(Dinheiro desconto) {
        assertThat(pedido.getDesconto()).isEqualTo(desconto);
    }

    private void assertValorTotalItensPedidoIgualAoValorDe(Dinheiro valorTotalItens) {
        assertThat(pedido.getValorTotalItens()).isEqualTo(valorTotalItens);
    }

    private void verificarExecucaoCalcularTipoCliente() {
        verify(cliente, only()).calcularTipoCliente();
    }

    private void verificarExecucaoGetValorTotalDeTodosItens() {
        verificarExecucaoGetValorTotalDosItens1e2e3();
        verificarExecucaoGetValorTotalSomenteDo(item4);
    }

    private void assertValorTotalFinalIgualASomaDeTodosOsItens() {
        assertThat(pedido.getValorTotalFinal()).isEqualTo(somaTodosItens);
    }

    private void assertDescontoPedidoZero() {
        assertThat(pedido.getDesconto()).isEqualTo(Dinheiro.ZERO);
    }

    private void verificarExecucaoGetValorTotalDosItens1e2() {
        verificarExecucaoGetValorTotalSomenteDo(item1);
        verificarExecucaoGetValorTotalSomenteDo(item2);
    }

    private void verificarExecucaoGetValorTotalDosItens1e2e3() {
        verificarExecucaoGetValorTotalDosItens1e2();
        verificarExecucaoGetValorTotalSomenteDo(item3);
    }

    private void adicionarSomenteItem1AoPedido() {
        itens = new ArrayList<>();
        itens.add(item1);
        pedido.itens = itens;
    }

    private void adicionarItens1e2AoPedido() {
        itens = new ArrayList<>();
        itens.add(item1);
        itens.add(item2);
        pedido.itens = itens;
    }

    private void adicionarItens1e2e3AoPedido() {
        adicionarItens1e2AoPedido();
        itens.add(item3);
    }

    private void adicionarTodosItensAoPedido() {
        adicionarItens1e2e3AoPedido();
        itens.add(item4);
    }

    private void assertValorTotalFinalPedidoIgualAoValorDe(Dinheiro valorTotalItem1) {
        assertThat(pedido.getValorTotalFinal()).isEqualTo(valorTotalItem1);
    }

    private void assertValorTotalItensPedidoIgualAoValorItem1() {
        assertValorTotalItensPedidoIgualAoValorDe(valorTotalItem1);
    }

    private void verificarExecucaoGetValorTotalSomenteDo(ItemPedido item) {
        verify(item).getValorTotal();
    }

    private void mockCalcularTipoClienteComo(TipoCliente tipo) {
        when(cliente.calcularTipoCliente()).thenReturn(tipo);
    }
}