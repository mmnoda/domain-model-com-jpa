package br.com.devmedia.cleancode.modelo.comum;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class DinheiroTest {

    private Dinheiro dinheiro;

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

    private void assertDinheiroIgualA(Dinheiro dinheiroIgual) {
        assertThat(dinheiro).isEqualTo(dinheiroIgual);
    }

    private void assertDinheiroDiferenteDe(Dinheiro dinheiroDiferente) {
        assertThat(dinheiro).isNotEqualTo(dinheiroDiferente);
    }

}