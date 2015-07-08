package br.com.devmedia.cleancode.modelo.comum;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class NomeTest {

    private Nome nome;

    @Test
    public void deve_ser_igual_ao_proprio() {
        nome = Nome.valueOf("João da Silva");
        assertThat(nome).isEqualTo(nome);
    }

    @Test
    public void deve_implementar_equals_consistente() {
        nome = Nome.valueOf("José");
        assertNomeIgualA(Nome.valueOf("José"));
        assertNomeDiferenteDe(Nome.valueOf("Maria"));
    }

    @Test
    public void deve_converter_para_maiusculo() {
        nome = Nome.valueOf("teste minúsculo");
        assertThat(nome.toString()).isEqualTo("TESTE MINÚSCULO");
    }

    private void assertNomeIgualA(Nome nomeIgual) {
        assertThat(nome).isEqualTo(nomeIgual);
    }

    private void assertNomeDiferenteDe(Nome nomeDiferente) {
        assertThat(nome).isNotEqualTo(nomeDiferente);
    }

}