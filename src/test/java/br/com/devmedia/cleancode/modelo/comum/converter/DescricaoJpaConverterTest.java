package br.com.devmedia.cleancode.modelo.comum.converter;

import br.com.devmedia.cleancode.modelo.comum.Descricao;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class DescricaoJpaConverterTest {

    private DescricaoJpaConverter descricaoJpaConverter;
    private final String VALOR_ESPERADO = "Teste descrição";

    @Before
    public void setUp() {
        descricaoJpaConverter = new DescricaoJpaConverter();
    }

    @Test
    public void deve_converter_descricao_para_string() {
        String s = descricaoJpaConverter.convertToDatabaseColumn(Descricao.valueOf(VALOR_ESPERADO));
        assertThat(s).isNotNull().isEqualTo(VALOR_ESPERADO);
    }

    @Test
    public void deve_converter_string_para_descricao() {
        Descricao descricao = descricaoJpaConverter.convertToEntityAttribute(VALOR_ESPERADO);
        assertThat(descricao).isNotNull().isEqualTo(Descricao.valueOf(VALOR_ESPERADO));
    }
}