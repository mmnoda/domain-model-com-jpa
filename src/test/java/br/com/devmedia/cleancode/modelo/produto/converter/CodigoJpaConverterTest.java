package br.com.devmedia.cleancode.modelo.produto.converter;

import br.com.devmedia.cleancode.modelo.produto.Codigo;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class CodigoJpaConverterTest {

    public final String VALOR_ESPERADO = "654321";

    private CodigoJpaConverter codigoJpaConverter;

    @Before
    public void setUp() {
        codigoJpaConverter = new CodigoJpaConverter();
    }

    @Test
    public void deve_converter_codigo_para_string() {
        String s = codigoJpaConverter.convertToDatabaseColumn(Codigo.valueOf(VALOR_ESPERADO));
        assertThat(s).isNotNull().isEqualTo(VALOR_ESPERADO);
    }

    @Test
    public void deve_converter_string_para_codigo() {
        Codigo codigo = codigoJpaConverter.convertToEntityAttribute(VALOR_ESPERADO);
        assertThat(codigo).isNotNull().isEqualTo(Codigo.valueOf(VALOR_ESPERADO));
    }
}