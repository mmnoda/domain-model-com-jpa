package br.com.devmedia.cleancode.modelo;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import static br.com.devmedia.cleancode.infraestrutura.ArredondamentoConstants.ARREDONDAMENTO_PADRAO;
import static br.com.devmedia.cleancode.infraestrutura.ArredondamentoConstants.CASAS_DECIMAIS;

/**
 *
 */
public class Percentual implements Serializable, Comparable<Percentual> {

    private static final long serialVersionUID = 1394408518476318914L;

    public static final Percentual ZERO = valueOf(BigDecimal.ZERO);

    private static final BigDecimal _100 = new BigDecimal(100).setScale(CASAS_DECIMAIS, ARREDONDAMENTO_PADRAO);

    private final BigDecimal valor;

    private Percentual(BigDecimal valor) {
        this.valor = valor.setScale(CASAS_DECIMAIS, ARREDONDAMENTO_PADRAO);
    }

    public static Percentual valueOf(BigDecimal valor) {
        return new Percentual(valor);
    }

    public static Percentual valueOf(double valor) {
        return new Percentual(BigDecimal.valueOf(valor));
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Percentual) {
            final Percentual other = (Percentual) obj;
            return Objects.equals(this.valor, other.valor);
        }
        return false;
    }

    @Override
    public String toString() {
        return valor.toString();
    }

    public Percentual add(Percentual outro) {
        return valueOf(valor.add(outro.valor));
    }

    public Dinheiro calcular(Dinheiro dinheiro) {
        return Dinheiro.valueOf(dinheiro.toBigDecimal().multiply(valor).
                divide(_100, ARREDONDAMENTO_PADRAO));
    }

    @Override
    public int compareTo(@NotNull Percentual o) {
        return valor.compareTo(o.valor);
    }
}
