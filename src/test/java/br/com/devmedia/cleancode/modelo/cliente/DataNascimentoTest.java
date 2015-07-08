package br.com.devmedia.cleancode.modelo.cliente;

import br.com.devmedia.cleancode.infraestrutura.DateTimeUtils;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class DataNascimentoTest {

    private DataNascimento nascimento;

    private final DateTimeUtils dateTimeUtils = DateTimeUtils.INSTANCE;

    @Test
    public void deve_ser_igual_ao_proprio() {
        nascimento = newDataNascimento10Dezembro1995();
        assertThat(nascimento).isEqualTo(nascimento);
    }

    @Test
    public void deve_implementar_equals_consistente() {
        nascimento = newDataNascimento25Junho1980();
        assertNascimentoIgualA(newDataNascimento25Junho1980());
        assertNascimentoDiferenteDe(DataNascimento.of(2000, 1, 1));
    }

    @Test
    public void deve_calcular_idade() {
        dateTimeUtils.fixar(getData30Junho2015());
        nascimento = newDataNascimento10Dezembro1995();
        assertIdadeCalculadaIgualA(Idade.valueOf(19));

        nascimento = newDataNascimento25Junho1980();
        assertIdadeCalculadaIgualA(Idade.valueOf(35));
    }

    private DataNascimento newDataNascimento10Dezembro1995() {
        return DataNascimento.of(1995, 12, 10);
    }

    private DataNascimento newDataNascimento25Junho1980() {
        return DataNascimento.of(1980, 6, 25);
    }

    private void assertIdadeCalculadaIgualA(Idade idadeEsperada) {
        assertThat(nascimento.getIdade()).isNotNull().isEqualTo(idadeEsperada);
    }

    private void assertNascimentoDiferenteDe(DataNascimento outroDiferente) {
        assertThat(nascimento).isNotEqualTo(outroDiferente);
    }

    private void assertNascimentoIgualA(DataNascimento outroIgual) {
        assertThat(nascimento).isEqualTo(outroIgual);
    }

    private LocalDateTime getData30Junho2015() {
        return LocalDateTime.of(2015, 6, 30, 0, 0);
    }
}