package br.com.devmedia.cleancode.infraestrutura;

import java.math.RoundingMode;

/**
 *
 */
public final class ArredondamentoConstants {

    public static final int CASAS_DECIMAIS = 2;
    public static final RoundingMode ARREDONDAMENTO_PADRAO = RoundingMode.HALF_EVEN;

    private ArredondamentoConstants() {
    }
}
