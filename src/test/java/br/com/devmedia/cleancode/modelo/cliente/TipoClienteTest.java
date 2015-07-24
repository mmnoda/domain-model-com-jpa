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

import br.com.devmedia.cleancode.modelo.comum.Dinheiro;
import br.com.devmedia.cleancode.modelo.comum.Percentual;
import br.com.devmedia.cleancode.modelo.pedido.Pedido;
import org.junit.After;
import org.junit.Test;

import static br.com.devmedia.cleancode.modelo.cliente.DescontoClienteConstants.*;
import static br.com.devmedia.cleancode.modelo.cliente.TipoCliente.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TipoClienteTest {

    private TipoCliente tipoCliente;

    private final Pedido pedido = mock(Pedido.class);

    private final Cliente cliente = mock(Cliente.class);

    private Percentual percentual;

    private TipoCliente proximo;

    @After
    public void tearDown() {
        reset(pedido, cliente);
    }

    @Test
    public void deve_calcular_percentual_de_desconto_com_pedido_possuindo_valor_total_de_itens_MAIOR_de_500_quando_tipo_for_BRONZE() {
        setTipoBronze();
        mockValorTotalItensDoPedidoIgualA(Dinheiro.valueOf(501));
        calcularPercentualDesconto();
        verifyValorTotalItens();
        assertPercentualDescontoIgualA(_3_PORCENTO);
    }

    @Test
    public void deve_calcular_percentual_de_desconto_com_pedido_possuindo_valor_total_de_itens_MENOR_de_500_quando_tipo_for_BRONZE() {
        setTipoBronze();
        mockValorTotalItensDoPedidoIgualA(Dinheiro.valueOf(499));
        calcularPercentualDesconto();
        verifyValorTotalItens();
        assertPercentualDescontoIgualA(Percentual.ZERO);
    }

    @Test
    public void deve_calcular_percentual_de_desconto_com_pedido_possuindo_valor_total_de_itens_MENOR_de_3000_quando_tipo_for_PRATA() {
        setTipoPrata();
        mockValorTotalItensDoPedidoIgualA(Dinheiro.valueOf(2999));
        mockPossuiItemComValorMaiorOuIgualQue3000Retorna(false);
        calcularPercentualDesconto();
        verifyValorTotalItens();
        verifyPossuiItemComValorMaiorOuIgualQue3000();
        assertPercentualDescontoIgualA(_3_PORCENTO);
    }

    @Test
    public void deve_calcular_percentual_de_desconto_com_pedido_possuindo_valor_total_de_itens_MAIOR_de_3000_quando_tipo_for_PRATA() {
        setTipoPrata();
        mockValorTotalItensDoPedidoIgualA(Dinheiro.valueOf(3000.01));
        mockPossuiItemComValorMaiorOuIgualQue3000Retorna(false);
        calcularPercentualDesconto();
        verifyValorTotalItens();
        verifyPossuiItemComValorMaiorOuIgualQue3000();
        assertPercentualDescontoIgualA(_5_PORCENTO);
    }

    @Test
    public void deve_calcular_percentual_de_desconto_adicionando_3_porcento_caso_pedido_possuir_item_com_valor_acima_ou_igual_a_3000_quando_tipo_for_PRATA() {
        setTipoPrata();
        mockValorTotalItensDoPedidoIgualA(Dinheiro.valueOf(3000.01));
        mockPossuiItemComValorMaiorOuIgualQue3000Retorna(true);
        calcularPercentualDesconto();
        verifyValorTotalItens();
        verifyPossuiItemComValorMaiorOuIgualQue3000();
        assertPercentualDescontoIgualA(Percentual.valueOf(8));
    }

    @Test
    public void deve_calcular_percentual_de_desconto_quando_tipo_for_OURO() {
        setTipoOuro();
        mockValorTotalItensDoPedidoIgualA(Dinheiro.valueOf(12000));
        calcularPercentualDesconto();
        assertPercentualDescontoIgualA(_10_PORCENTO);
    }

    @Test
    public void deve_calcular_percentual_de_desconto_igual_a_zero_quando_tipo_for_SEM_CLASSIFICACAO() {
        setSemClassificacao();
        calcularPercentualDesconto();
        assertPercentualDescontoIgualA(Percentual.ZERO);
    }

    @Test
    public void deve_identificar_tipo_como_o_proprio_quando_tipo_for_BRONZE() {
        setTipoBronze();
        mockPossuiTotalPedidosEntreQuinhentosETresMilIgualA(true);
        identificar();
        verifyPossuiTotalPedidosEntre(QUINHENTOS, TRES_MIL);
        assertTipoClienteIdentificadoComo(BRONZE);
    }

    @Test
    public void deve_identificar_tipo_como_prata_quando_tipo_for_BRONZE() {
        setTipoBronze();
        mockPossuiTotalPedidosEntreQuinhentosETresMilIgualA(false);
        mockPossuiTotalPedidosEntreTresEDezMilIgualA(true);
        identificar();
        verifyPossuiTotalPedidosEntre(QUINHENTOS, TRES_MIL);
        verifyPossuiTotalPedidosEntre(TRES_MIL, DEZ_MIL);
        assertTipoClienteIdentificadoComo(PRATA);
    }

    @Test
    public void deve_identificar_tipo_como_ouro_quando_tipo_for_BRONZE() {
        setTipoBronze();
        mockPossuiTotalPedidosEntreQuinhentosETresMilIgualA(false);
        mockPossuiTotalPedidosEntreTresEDezMilIgualA(false);
        mockClientePossuiTotalPedidosMaiorQueRetorna(true);
        identificar();
        verifyPossuiTotalPedidosEntre(QUINHENTOS, TRES_MIL);
        verifyPossuiTotalPedidosEntre(TRES_MIL, DEZ_MIL);
        verifyPossuiTotalPedidosMaiorQue();
        assertTipoClienteIdentificadoComo(OURO);
    }

    @Test
    public void deve_identificar_tipo_como_SEM_CLASSIFICACAO_quando_tipo_for_BRONZE() {
        setTipoBronze();
        mockPossuiTotalPedidosEntreQuinhentosETresMilIgualA(false);
        mockPossuiTotalPedidosEntreTresEDezMilIgualA(false);
        mockClientePossuiTotalPedidosMaiorQueRetorna(false);
        identificar();
        verifyPossuiTotalPedidosEntre(QUINHENTOS, TRES_MIL);
        verifyPossuiTotalPedidosEntre(TRES_MIL, DEZ_MIL);
        verifyPossuiTotalPedidosMaiorQue();
        assertTipoClienteIdentificadoComo(SEM_CLASSIFICACAO);
    }

    @Test
    public void deve_sempre_identificar_o_tipo_de_cliente_como_o_proprio_quando_tipo_for_SEM_CLASSIFICACAO() {
        setSemClassificacao();
        identificar();
        assertTipoClienteIdentificadoComo(SEM_CLASSIFICACAO);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void deve_bloquear_identificacao_do_proximo_tipo_quando_for_SEM_CLASSIFICACAO() {
        setSemClassificacao();
        tipoCliente.proximo();
    }

    private void mockClientePossuiTotalPedidosMaiorQueRetorna(boolean t) {
        when(cliente.possuiTotalPedidosMaiorQue(DEZ_MIL)).thenReturn(t);
    }

    private void verifyPossuiTotalPedidosMaiorQue() {
        verify(cliente).possuiTotalPedidosMaiorQue(DEZ_MIL);
    }

    private void verifyPossuiTotalPedidosEntre(Dinheiro valorInicial, Dinheiro valorFinal) {
        verify(cliente).possuiTotalPedidosEntre(valorInicial, valorFinal);
    }

    private void mockPossuiTotalPedidosEntreTresEDezMilIgualA(boolean resultado) {
        when(cliente.possuiTotalPedidosEntre(TRES_MIL, DEZ_MIL)).thenReturn(resultado);
    }

    private void mockPossuiTotalPedidosEntreQuinhentosETresMilIgualA(boolean resultado) {
        when(cliente.possuiTotalPedidosEntre(QUINHENTOS, TRES_MIL)).thenReturn(resultado);
    }

    private void verifyPossuiItemComValorMaiorOuIgualQue3000() {
        verify(pedido).possuiItemComValorMaiorOuIgualQue3000();
    }

    private void identificar() {
        proximo = tipoCliente.identificar(cliente);
    }

    private void mockPossuiItemComValorMaiorOuIgualQue3000Retorna(boolean resultado) {
        when(pedido.possuiItemComValorMaiorOuIgualQue3000()).thenReturn(resultado);
    }

    private void mockValorTotalItensDoPedidoIgualA(Dinheiro valorEsperado) {
        when(pedido.getValorTotalItens()).thenReturn(valorEsperado);
    }

    private void assertTipoClienteIdentificadoComo(TipoCliente classificacaoEsperada) {
        assertThat(proximo).isNotNull().isEqualTo(classificacaoEsperada);
    }

    private void calcularPercentualDesconto() {
        percentual = tipoCliente.calcularPercentualDesconto(pedido);
    }

    private void assertPercentualDescontoIgualA(Percentual percentualEsperado) {
        assertThat(percentual).isNotNull().isEqualTo(percentualEsperado);
    }

    private void verifyValorTotalItens() {
        verify(pedido).getValorTotalItens();
    }

    private void setTipoBronze() {
        tipoCliente = BRONZE;
    }

    private void setTipoPrata() {
        tipoCliente = PRATA;
    }

    private void setTipoOuro() {
        tipoCliente = OURO;
    }

    private void setSemClassificacao() {
        tipoCliente = SEM_CLASSIFICACAO;
    }
}