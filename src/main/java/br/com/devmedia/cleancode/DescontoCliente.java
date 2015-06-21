package br.com.devmedia.cleancode;

import java.math.BigDecimal;

/**
 *
 */
public interface DescontoCliente {

    int CLIENTE_BRONZE = 0;

    int CLIENTE_PRATA = 1;

    int CLIENTE_OURO = 2;

    BigDecimal _3_PORCENTO = BigDecimal.valueOf(3);
    BigDecimal _5_PORCENTO = BigDecimal.valueOf(5);
    BigDecimal _7_PORCENTO = BigDecimal.valueOf(7);

    void aplicarDesconto(final int tipoCliente);
}
