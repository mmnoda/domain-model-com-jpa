package br.com.devmedia.cleancode.infraestrutura;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 *
 */
public class FormatadorSingletonTest {

    private final FormatadorSingleton formatadorSingleton = FormatadorSingleton.INSTANCE;

    @Test
    public void deve_formatar_big_decimal() throws Exception {
        assertThat(formatadorSingleton.formatar(BigDecimal.valueOf(1999.50))).isNotNull().isEqualTo("1.999,50");
    }

    @Test
    public void deve_formatar_big_integer() throws Exception {
        assertThat(formatadorSingleton.formatar(BigInteger.valueOf(123456))).isNotNull().isEqualTo("123.456");
    }

    @Test
    public void deve_efetuar_parse_com_sucesso() throws ParseException {
        assertThat(formatadorSingleton.parse("1.234.567,89")).isNotNull().
                isEqualTo(BigDecimal.valueOf(1234567.89));

    }

    @Test(expected = ParseException.class)
    public void deve_validar_parse_de_string_invalido() throws ParseException {
        formatadorSingleton.parse(" 1234567,89 ");
        fail("Parse incorreto");
    }
}