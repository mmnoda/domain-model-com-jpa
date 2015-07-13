package br.com.devmedia.cleancode.modelo.cliente;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class IdadeTest {

    private Idade idade;

    @Test
    public void deve_ser_igual_ao_proprio() {
        idade = Idade.valueOf(10);
        assertIdadeIgualA(idade);
    }

    @Test
    public void deve_implementar_equals_consistente() {
        idade = Idade.valueOf(21);
        assertIdadeIgualA(Idade.valueOf(21));
        assertIdadeDiferenteDe(Idade.valueOf(55));
    }

    @Test
    public void deve_verificar_se_MENOR_de_idade() {
        idade = Idade.valueOf(17);
        assertThat(idade.isMaiorIdade()).isFalse();
    }

    @Test
    public void deve_verificar_se_MAIOR_de_idade() {
        idade = Idade.valueOf(18);
        assertThat(idade.isMaiorIdade()).isTrue();
    }

    @Test
    public void deve_imprimir_to_string_corretamente() {
        idade = Idade.valueOf(84);
        assertThat(idade.toString()).isNotNull().isEqualTo("84 ano(s)") ;

    }

    private void assertIdadeIgualA(Idade idadeIgual) {
        assertThat(idade).isEqualTo(idadeIgual);
        assertThat(idade.hashCode()).isEqualTo(idadeIgual.hashCode());
    }

    private void assertIdadeDiferenteDe(Idade idadeDiferente) {
        assertThat(idade).isNotEqualTo(idadeDiferente);
        assertThat(idade.hashCode()).isNotEqualTo(idadeDiferente.hashCode());
    }
}