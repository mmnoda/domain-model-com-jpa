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
        assertQuantidadeIgualA(quantidade);
    }

    @Test
    public void deve_implementar_equals_consistente() {
        quantidade = Quantidade.valueOf(15);
        assertQuantidadeIgualA(Quantidade.valueOf(15));
        assertQuantidadeDiferenteDe(Quantidade.UM);
    }

    @Test
    public void deve_formatar_corretamente() {
        quantidade = Quantidade.valueOf(123456789);
        String valorFormatado = String.format("%s", quantidade);
        assertThat(valorFormatado).isNotNull().isEqualTo("123.456.789");
    }

    private void assertQuantidadeIgualA(Quantidade quantidadeIgual) {
        assertThat(quantidade).isEqualTo(quantidadeIgual);
        assertThat(quantidade.hashCode()).isEqualTo(quantidadeIgual.hashCode());
    }

    private void assertQuantidadeDiferenteDe(Quantidade quantidadeDiferente) {
        assertThat(quantidade).isNotEqualTo(quantidadeDiferente);
        assertThat(quantidade.hashCode()).isNotEqualTo(quantidadeDiferente.hashCode());
    }

}