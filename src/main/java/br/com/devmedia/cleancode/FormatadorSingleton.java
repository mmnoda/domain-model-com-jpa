package br.com.devmedia.cleancode;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;

/**
 *
 */
public class FormatadorSingleton {

    public final String FORMATO_DUAS_CASAS_DECIMAIS = "###,###,##0.00";

    public final String FORMATO_INTEIRO = "###,###,##0";

    private static FormatadorSingleton instance;

    private FormatadorSingleton() {
    }

    public static FormatadorSingleton getInstance() {
        if (instance == null) {
            synchronized (FormatadorSingleton.class) {
                if (instance == null) {
                    instance = new FormatadorSingleton();
                }
            }
        }
        return instance;
    }

    public String formatar(BigDecimal valor) {
        DecimalFormat numberFormat = new DecimalFormat(FORMATO_DUAS_CASAS_DECIMAIS);
        return numberFormat.format(valor.doubleValue());
    }

    public String formatar(BigInteger valor) {
        DecimalFormat numberFormat = new DecimalFormat(FORMATO_INTEIRO);
        return numberFormat.format(valor.doubleValue());
    }

}
