package br.com.devmedia.cleancode.modelo.produto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class CodigoTest {

    private Codigo codigo;

    @Test
    public void deve_ser_igual_ao_proprio() {
        codigo = Codigo.valueOf("123");
        assertThat(codigo).isEqualTo(codigo);
    }

    @Test
    public void deve_implementar_equals_consistente() {
        codigo = Codigo.valueOf("001");
        assertCodigoIgualA(Codigo.valueOf("001"));
        assertCodigoDiferenteDe(Codigo.valueOf("777"));
    }

    private void assertCodigoIgualA(Codigo codigoIgual) {
        assertThat(codigo).isEqualTo(codigoIgual);
    }

    private void assertCodigoDiferenteDe(Codigo codigoDiferente) {
        assertThat(codigo).isNotEqualTo(codigoDiferente);
    }

}