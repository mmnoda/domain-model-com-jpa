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

import br.com.devmedia.cleancode.infraestrutura.DateTimeUtils;
import br.com.devmedia.cleancode.modelo.comum.Dinheiro;
import br.com.devmedia.cleancode.modelo.comum.Nome;
import br.com.devmedia.cleancode.modelo.pedido.Pedido;
import br.com.devmedia.cleancode.modelo.pedido.StatusPedido;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static br.com.devmedia.cleancode.modelo.cliente.PedidosSet.newPedidosSet;
import static br.com.devmedia.cleancode.modelo.cliente.TipoCliente.*;
import static br.com.devmedia.cleancode.modelo.pedido.StatusPedido.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ClienteTest {

    private Cliente cliente;

    private final DateTimeUtils dateTimeUtils = DateTimeUtils.INSTANCE;

    private final Pedido pedidoFaturado1 = mock(Pedido.class);
    private final Pedido pedidoFaturado2 = mock(Pedido.class);
    private final Pedido pedidoCancelado = mock(Pedido.class);
    private final Pedido pedidoAberto = mock(Pedido.class);

    private final PedidosSet pedidos = newPedidosSet();

    private final Cpf cpfQualquer = Cpf.valueOf("16738530722");

    @Before
    public void setUp() {
        cliente = new Cliente();
        vincularPedidosAoCliente();
    }

    private void vincularPedidosAoCliente() {
        cliente.pedidos = pedidos;
        adicionarPedido(pedidoFaturado1, FATURADO);
        adicionarPedido(pedidoFaturado2, FATURADO);
        adicionarPedido(pedidoCancelado, CANCELADO);
        adicionarPedido(pedidoAberto, ABERTO);
    }

    private void adicionarPedido(Pedido pedidoMock, StatusPedido faturado) {
        when(pedidoMock.getEstado()).thenReturn(faturado);
        pedidos.add(pedidoMock);
    }

    @After
    public void tearDown() {
        pedidos.clear();
        reset(pedidoFaturado1, pedidoFaturado2, pedidoCancelado, pedidoAberto);
        dateTimeUtils.setClockPadrao();
    }

    @Test
    public void deve_ser_igual_ao_proprio() {
        cliente.setCpf(cpfQualquer);
        assertClienteIgual(cliente);
    }

    @Test
    public void deve_implementar_equals_consistente() {
        cliente.setCpf(cpfQualquer);
        Cliente outroIgual = new Cliente();
        outroIgual.setCpf(cpfQualquer);
        assertClienteIgual(outroIgual);
        assertClienteDiferente();
    }

    @Test
    public void deve_calcular_tipo_de_cliente_BRONZE_com_base_em_seus_pedidos_faturados() {
        mockValorTotalDoPedido1FaturadoComo(Dinheiro.valueOf(350));
        mockValorTotalDoPedido2FaturadoComo(Dinheiro.valueOf(150));
        assertTipoClienteIgualA(BRONZE);
    }

    @Test
    public void deve_calcular_tipo_de_cliente_PRATA_com_base_em_seus_pedidos_faturados() {
        mockValorTotalDoPedido1FaturadoComo(Dinheiro.valueOf(350));
        mockValorTotalDoPedido2FaturadoComo(Dinheiro.valueOf(2650));
        assertTipoClienteIgualA(PRATA);
    }

    @Test
    public void deve_calcular_tipo_de_cliente_OURO_com_base_em_seus_pedidos_faturados() {
        mockValorTotalDoPedido1FaturadoComo(Dinheiro.valueOf(3500));
        mockValorTotalDoPedido2FaturadoComo(Dinheiro.valueOf(7500));
        assertTipoClienteIgualA(OURO);
    }

    @Test
    public void deve_verificar_se_cliente_MAIOR_de_idade() {
        cliente.setNascimento(DataNascimento.of(1997, 6, 30));
        fixarDataCorrenteComo30Junho2015();
        assertIdadeClienteIgualA(Idade.valueOf(18));
        assertClienteMaiorDeIdade();
    }

    @Test
    public void deve_verificar_se_cliente_MENOR_de_idade() {
        cliente.setNascimento(DataNascimento.of(2000, 10, 15));
        fixarDataCorrenteComo30Junho2015();
        assertIdadeClienteIgualA(Idade.valueOf(14));
        assertClienteMenorDeIdade();
    }

    @Test
    public void deve_manter_o_nome_do_cliente_maiusculo_sem_espacos_no_inicio_ou_no_final() {
        cliente.setNome(Nome.valueOf(" João da Silva "));
        assertThat(cliente.getNome()).isNotNull().isEqualTo(Nome.valueOf("JOÃO DA SILVA"));
    }

    private void fixarDataCorrenteComo30Junho2015() {
        dateTimeUtils.fixar(getData30Junho2015());
    }

    private void assertTipoClienteIgualA(TipoCliente bronze) {
        assertThat(cliente.calcularTipoCliente()).isNotNull().isEqualTo(bronze);
    }

    private void assertClienteMenorDeIdade() {
        assertThat(cliente.isMaiorIdade()).isFalse();
    }

    private void assertIdadeClienteIgualA(Idade anos) {
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
        outroDiferente.setCpf(Cpf.valueOf("31692172751"));
        assertThat(cliente).isNotEqualTo(outroDiferente);
        assertThat(cliente.hashCode()).isNotEqualTo(outroDiferente.hashCode());
    }

    private void assertClienteIgual(Cliente outroIgual) {
        assertThat(cliente).isEqualTo(outroIgual);
        assertThat(cliente.hashCode()).isEqualTo(outroIgual.hashCode());
    }

    private void mockValorTotalDoPedido2FaturadoComo(Dinheiro valorTotalFinal) {
        when(pedidoFaturado2.getValorTotalFinal()).thenReturn(valorTotalFinal);
    }

    private void mockValorTotalDoPedido1FaturadoComo(Dinheiro valor) {
        when(pedidoFaturado1.getValorTotalFinal()).thenReturn(valor);
    }
}