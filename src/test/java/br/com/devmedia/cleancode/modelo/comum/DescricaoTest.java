package br.com.devmedia.cleancode.modelo.comum;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class DescricaoTest {

    private Descricao descricao;

    @Test
    public void deve_ser_igual_ao_proprio() {
        descricao = Descricao.valueOf("teste");
        assertDescricaoIgualA(descricao);
    }

    @Test
    public void deve_implementar_equals_consistente() {
        descricao = Descricao.valueOf("xyz");
        assertDescricaoIgualA(Descricao.valueOf("xyz"));
        assertDescricaoDiferenteDe(Descricao.valueOf("diferente"));
    }

    private void assertDescricaoIgualA(Descricao descricaoIgual) {
        assertThat(descricao).isEqualTo(descricaoIgual);
        assertThat(descricao.hashCode()).isEqualTo(descricaoIgual.hashCode());
    }

    private void assertDescricaoDiferenteDe(Descricao descricaoDiferente) {
        assertThat(descricao).isNotEqualTo(descricaoDiferente);
        assertThat(descricao.hashCode()).isNotEqualTo(descricaoDiferente.hashCode());
    }

}