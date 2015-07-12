package br.com.devmedia.cleancode.modelo.comum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.isNull;

/**
 *
 */
public class Quantidade implements Serializable, Comparable<Quantidade> {

    private static final long serialVersionUID = 4868785344958171893L;

    public static final Quantidade UM = valueOf(BigInteger.ONE);

    private final BigInteger valor;

    private Quantidade(BigInteger valor) {
        checkArgument(!isNull(valor), "Valor da quantidade nulo");
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
    public int compareTo(Quantidade o) {
        return valor.compareTo(o.valor);
    }

    public long longValue() {
        return valor.longValue();
    }

    public BigDecimal toBigDecimal() {
        return new BigDecimal(valor);
    }
}
