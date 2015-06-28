package br.com.devmedia.cleancode.modelo.cliente;

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

    BigDecimal _10_PORCENTO = BigDecimal.valueOf(10);

    BigDecimal QUINHENTOS = new BigDecimal(500);
    BigDecimal TRES_MIL = new BigDecimal(3000);
    BigDecimal DEZ_MIL = new BigDecimal(10000);

    Integer calcularTipoCliente();
}
