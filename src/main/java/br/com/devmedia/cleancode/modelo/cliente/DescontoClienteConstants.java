package br.com.devmedia.cleancode.modelo.cliente;

import br.com.devmedia.cleancode.modelo.Dinheiro;
import br.com.devmedia.cleancode.modelo.Percentual;

/**
 *
 */
public final class DescontoClienteConstants {

    public final static Percentual _3_PORCENTO = Percentual.valueOf(3);
    public final static Percentual _5_PORCENTO = Percentual.valueOf(5);
    public final static Percentual _10_PORCENTO = Percentual.valueOf(10);

    public final static Dinheiro QUINHENTOS = Dinheiro.valueOf(500);
    public final static Dinheiro TRES_MIL = Dinheiro.valueOf(3000);
    public final static Dinheiro DEZ_MIL = Dinheiro.valueOf(10000);

    private DescontoClienteConstants() {
    }
}
