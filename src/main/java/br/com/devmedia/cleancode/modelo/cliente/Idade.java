package br.com.devmedia.cleancode.modelo.cliente;

import java.io.Serializable;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.isNull;

/**
 *
 */
public class Idade implements Serializable, Comparable<Idade> {

    private static final long serialVersionUID = -2187798284806226001L;

    private final int valor;

    private Idade(int valor) {
        checkArgument(!isNull(valor), "Idade nulo");
        this.valor = valor;
    }

    public static Idade valueOf(int valor) {
        return new Idade(valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Idade) {
            final Idade other = (Idade) obj;
            return Objects.equals(this.valor, other.valor);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.valueOf(valor);
    }

    public boolean isMaiorIdade() {
        return valor >= 18;
    }

    @Override
    public int compareTo(Idade o) {
        return Integer.compare(valor, o.valor);
    }
}
