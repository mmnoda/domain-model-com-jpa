package br.com.devmedia.cleancode.modelo.comum;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class PercentualTest {

    private Percentual percentual;

    @Test
    public void deve_ser_igual_ao_proprio() {
        percentual = Percentual.valueOf(100);
        assertThat(percentual).isEqualTo(percentual);
    }

    @Test
    public void deve_implementar_equals_consistente() {
        percentual = Percentual.valueOf(75);
        assertPercentualIgualA(Percentual.valueOf(75));
        assertPercentualDiferenteDe(Percentual.valueOf(63));
    }

    @Test
    public void deve_calcular_percentual() {
        percentual = Percentual.valueOf(50);
        assertThat(percentual.calcular(Dinheiro.valueOf(1000))).isNotNull().
                isEqualTo(Dinheiro.valueOf(500));
    }

    private void assertPercentualIgualA(Percentual percentualIgual) {
        assertThat(percentual).isEqualTo(percentualIgual);
    }

    private void assertPercentualDiferenteDe(Percentual percentualDiferente) {
        assertThat(percentual).isNotEqualTo(percentualDiferente);
    }

}