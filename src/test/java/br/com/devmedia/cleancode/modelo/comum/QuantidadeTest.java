package br.com.devmedia.cleancode.modelo.comum;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class QuantidadeTest {

    private Quantidade quantidade;

    @Test
    public void deve_ser_igual_ao_proprio() {
        quantidade = Quantidade.valueOf(5);
        assertThat(quantidade).isEqualTo(quantidade);
    }

    @Test
    public void deve_implementar_equals_consistente() {
        quantidade = Quantidade.valueOf(15);
        assertQuantidadeIgualA(Quantidade.valueOf(15));
        assertQuantidadeDiferenteDe(Quantidade.UM);
    }

    private void assertQuantidadeIgualA(Quantidade quantidadeIgual) {
        assertThat(quantidade).isEqualTo(quantidadeIgual);
    }

    private void assertQuantidadeDiferenteDe(Quantidade quantidadeDiferente) {
        assertThat(quantidade).isNotEqualTo(quantidadeDiferente);
    }

}