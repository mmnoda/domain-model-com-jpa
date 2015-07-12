package br.com.devmedia.cleancode.modelo.comum;

import org.junit.Test;

import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class DinheiroTest {

    private Dinheiro dinheiro;

    private String formatado;

    @Test
    public void deve_ser_igual_ao_proprio() {
        dinheiro = Dinheiro.valueOf(50);
        assertThat(dinheiro).isEqualTo(dinheiro);
    }

    @Test
    public void deve_implementar_equals_consistente() {
        dinheiro = Dinheiro.valueOf(200);
        assertDinheiroIgualA(Dinheiro.valueOf(200));
        assertDinheiroDiferenteDe(Dinheiro.valueOf(100));
    }

    @Test
    public void deve_formatar() {
        dinheiro = Dinheiro.valueOf(123456.89);
        formatado = String.format("%s", dinheiro);
        assertThat(formatado).isNotNull().isEqualTo("123.456,89");
    }

    @Test
    public void deve_efetuar_parse() throws ParseException {
        dinheiro = Dinheiro.parse("987.654,32");
        assertThat(dinheiro).isEqualTo(Dinheiro.valueOf(987654.32));
    }

    private void assertDinheiroIgualA(Dinheiro dinheiroIgual) {
        assertThat(dinheiro).isEqualTo(dinheiroIgual);
    }

    private void assertDinheiroDiferenteDe(Dinheiro dinheiroDiferente) {
        assertThat(dinheiro).isNotEqualTo(dinheiroDiferente);
    }

}