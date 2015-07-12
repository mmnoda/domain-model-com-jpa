package br.com.devmedia.cleancode.modelo.comum.converter;

import br.com.devmedia.cleancode.modelo.comum.Nome;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class NomeJpaConverterTest {

    public final String VALOR_ESPERADO = "JOÃO DE BARRO";

    private NomeJpaConverter nomeJpaConverter;

    @Before
    public void setUp() {
        nomeJpaConverter = new NomeJpaConverter();
    }

    @Test
    public void deve_converter_nome_para_string() {
        String s = nomeJpaConverter.convertToDatabaseColumn(Nome.valueOf(VALOR_ESPERADO));
        assertThat(s).isNotNull().isEqualTo(VALOR_ESPERADO);
    }

    @Test
    public void deve_converter_string_para_nome() {
        Nome nome = nomeJpaConverter.convertToEntityAttribute(VALOR_ESPERADO);
        assertThat(nome).isNotNull().isEqualTo(Nome.valueOf(VALOR_ESPERADO));
    }
}