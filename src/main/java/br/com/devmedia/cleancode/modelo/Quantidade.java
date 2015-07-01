package br.com.devmedia.cleancode.modelo;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

/**
 *
 */
public class Quantidade implements Serializable, Comparable<Quantidade> {

    private static final long serialVersionUID = 4868785344958171893L;

    public static final Quantidade UM = valueOf(BigInteger.ONE);

    private final BigInteger valor;

    private Quantidade(BigInteger valor) {
        this.valor = valor;
    }

    public static Quantidade valueOf(BigInteger valor) {
        return new Quantidade(valor);
    }

    public static Quantidade valueOf(long valor) {
        return new Quantidade(BigInteger.valueOf(valor));
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Quantidade) {
            final Quantidade other = (Quantidade) obj;
            return Objects.equals(this.valor, other.valor);
        }
        return false;
    }

    @Override
    public String toString() {
        return valor.toString();
    }

    @Override
    public int compareTo(@NotNull Quantidade o) {
        return valor.compareTo(o.valor);
    }

    public BigDecimal toBigDecimal() {
        return new BigDecimal(valor);
    }
}
