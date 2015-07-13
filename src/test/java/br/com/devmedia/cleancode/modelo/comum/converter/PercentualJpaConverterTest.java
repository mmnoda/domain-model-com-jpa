package br.com.devmedia.cleancode.modelo.comum.converter;

import br.com.devmedia.cleancode.modelo.comum.Percentual;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static br.com.devmedia.cleancode.infraestrutura.ArredondamentoConstants.ARREDONDAMENTO_PADRAO;
import static br.com.devmedia.cleancode.infraestrutura.ArredondamentoConstants.CASAS_DECIMAIS;
import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class PercentualJpaConverterTest {

    public final double VALOR_ESPERADO = 93.75;

    public final BigDecimal BIG_DECIMAL = BigDecimal.valueOf(VALOR_ESPERADO).
            setScale(CASAS_DECIMAIS, ARREDONDAMENTO_PADRAO);

    private PercentualJpaConverter percentualJpaConverter;

    @Before
    public void setUp() {
        percentualJpaConverter = new PercentualJpaConverter();
    }

    @Test
    public void deve_converter_percentual_para_big_decimal() {
        BigDecimal bigDecimal = percentualJpaConverter.convertToDatabaseColumn(Percentual.valueOf(VALOR_ESPERADO));
        assertThat(bigDecimal).isNotNull().isEqualTo(BIG_DECIMAL);
    }

    @Test
    public void deve_converter_big_decimal_para_percentual() {
        Percentual percentual = percentualJpaConverter.convertToEntityAttribute(BIG_DECIMAL);
        assertThat(percentual).isNotNull().isEqualTo(Percentual.valueOf(VALOR_ESPERADO));
    }
}