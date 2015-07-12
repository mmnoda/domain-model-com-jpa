package br.com.devmedia.cleancode.modelo.comum.converter;

import br.com.devmedia.cleancode.modelo.comum.Quantidade;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class QuantidadeJpaConverterTest {

    private QuantidadeJpaConverter quantidadeJpaConverter;

    @Before
    public void setUp() {
        quantidadeJpaConverter = new QuantidadeJpaConverter();
    }

    @Test
    public void deve_converter_quantidade_para_long() {
        Long valor = quantidadeJpaConverter.convertToDatabaseColumn(Quantidade.valueOf(50));
        assertThat(valor).isNotNull().isEqualTo(50);
    }

    @Test
    public void deve_converter_long_para_quantidade() {
        Quantidade quantidade = quantidadeJpaConverter.convertToEntityAttribute(330L);
        assertThat(quantidade).isNotNull().isEqualTo(Quantidade.valueOf(330));
    }
}