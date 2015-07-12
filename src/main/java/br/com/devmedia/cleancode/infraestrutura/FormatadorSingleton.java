package br.com.devmedia.cleancode.infraestrutura;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;

/**
 *
 */
public enum FormatadorSingleton {
    INSTANCE;

    public final String FORMATO_DUAS_CASAS_DECIMAIS = "###,###,##0.00";

    public final String FORMATO_INTEIRO = "###,###,##0";

    public String formatar(BigDecimal valor) {
        DecimalFormat numberFormat = new DecimalFormat(FORMATO_DUAS_CASAS_DECIMAIS);
        return numberFormat.format(valor.doubleValue());
    }

    public BigDecimal parse(String s) throws ParseException {
        DecimalFormat numberFormat = new DecimalFormat(FORMATO_DUAS_CASAS_DECIMAIS);
        return BigDecimal.valueOf(numberFormat.parse(s).doubleValue());
    }

    public String formatar(BigInteger valor) {
        DecimalFormat numberFormat = new DecimalFormat(FORMATO_INTEIRO);
        return numberFormat.format(valor.doubleValue());
    }
}
