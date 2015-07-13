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
        assertPercentualIgualA(percentual);
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

    @Test
    public void deve_somar_com_outro_percentual() {
        percentual = Percentual.valueOf(10);
        Percentual resultado = percentual.add(Percentual.valueOf(30));
        assertThat(resultado).isNotNull().isEqualTo(Percentual.valueOf(40));
    }

    private void assertPercentualIgualA(Percentual percentualIgual) {
        assertThat(percentual).isEqualTo(percentualIgual);
        assertThat(percentual.hashCode()).isEqualTo(percentualIgual.hashCode());
    }

    private void assertPercentualDiferenteDe(Percentual percentualDiferente) {
        assertThat(percentual).isNotEqualTo(percentualDiferente);
        assertThat(percentual.hashCode()).isNotEqualTo(percentualDiferente.hashCode());
    }

}