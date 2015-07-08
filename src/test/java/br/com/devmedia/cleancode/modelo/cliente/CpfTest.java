package br.com.devmedia.cleancode.modelo.cliente;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class CpfTest {

    private Cpf cpf;

    @Test
    public void deve_ser_igual_ao_proprio() {
        cpf = Cpf.valueOf("56470416470");
        assertThat(cpf).isEqualTo(cpf);
    }

    @Test
    public void deve_implementar_equals_consistente() {
        cpf = Cpf.valueOf("31628763973");
        assertCpfIgualA(Cpf.valueOf("31628763973"));
        assertCpfDiferenteDe(Cpf.valueOf("42263873090"));
    }

    private void assertCpfIgualA(Cpf cpfIgual) {
        assertThat(cpf).isEqualTo(cpfIgual);
    }

    private void assertCpfDiferenteDe(Cpf cpfDiferente) {
        assertThat(cpf).isNotEqualTo(cpfDiferente);
    }

}