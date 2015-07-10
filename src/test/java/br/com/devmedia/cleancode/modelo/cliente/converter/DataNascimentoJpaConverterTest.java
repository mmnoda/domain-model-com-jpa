package br.com.devmedia.cleancode.modelo.cliente.converter;

import br.com.devmedia.cleancode.modelo.cliente.DataNascimento;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class DataNascimentoJpaConverterTest {

    private DataNascimentoJpaConverter dataNascimentoJpaConverter;

    @Before
    public void setUp() {
        dataNascimentoJpaConverter = new DataNascimentoJpaConverter();
    }

    @Test
    public void deve_converter_date_para_nascimento() {
        LocalDate dataEsperada = LocalDate.of(2015, 07, 10);
        DataNascimento nascimento = dataNascimentoJpaConverter.convertToEntityAttribute(Date.valueOf(dataEsperada));
        assertThat(nascimento).isNotNull().isEqualTo(DataNascimento.valueOf(dataEsperada));
    }

}