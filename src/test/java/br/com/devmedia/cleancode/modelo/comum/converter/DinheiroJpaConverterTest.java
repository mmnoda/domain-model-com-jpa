package br.com.devmedia.cleancode.modelo.comum.converter;

import br.com.devmedia.cleancode.modelo.comum.Dinheiro;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static br.com.devmedia.cleancode.infraestrutura.ArredondamentoConstants.ARREDONDAMENTO_PADRAO;
import static br.com.devmedia.cleancode.infraestrutura.ArredondamentoConstants.CASAS_DECIMAIS;
import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class DinheiroJpaConverterTest {

    public final double VALOR_ESPERADO = 2500.50;

    public final BigDecimal BIG_DECIMAL = BigDecimal.valueOf(VALOR_ESPERADO).
            setScale(CASAS_DECIMAIS, ARREDONDAMENTO_PADRAO);

    private DinheiroJpaConverter dinheiroJpaConverter;

    @Before
    public void setUp() {
        dinheiroJpaConverter = new DinheiroJpaConverter();
    }

    @Test
    public void deve_converter_dinheiro_para_big_decimal() {
        BigDecimal bigDecimal = dinheiroJpaConverter.convertToDatabaseColumn(Dinheiro.valueOf(VALOR_ESPERADO));
        assertThat(bigDecimal).isNotNull().isEqualTo(BIG_DECIMAL);
    }

    @Test
    public void deve_converter_big_decimal_para_dinheiro() {
        Dinheiro dinheiro = dinheiroJpaConverter.convertToEntityAttribute(BIG_DECIMAL);
        assertThat(dinheiro).isNotNull().isEqualTo(Dinheiro.valueOf(VALOR_ESPERADO));
    }
}