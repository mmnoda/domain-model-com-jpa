package br.com.devmedia.cleancode.infraestrutura;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class FormatadorSingletonTest {

    private FormatadorSingleton formatadorSingleton = FormatadorSingleton.getInstance();

    @Test
    public void deve_formatar_big_decimal() throws Exception {
        assertThat(formatadorSingleton.formatar(BigDecimal.valueOf(1999.50))).isNotNull().isEqualTo("1.999,50");
    }

    @Test
    public void deve_formatar_big_integer() throws Exception {
        assertThat(formatadorSingleton.formatar(BigInteger.valueOf(123456))).isNotNull().isEqualTo("123.456");
    }
}