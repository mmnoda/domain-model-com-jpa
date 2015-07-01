package br.com.devmedia.cleancode.modelo;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 */
public class Nome implements Serializable, Comparable<Nome> {

    private static final long serialVersionUID = -6630993258824215226L;

    private final String valor;

    private Nome(String valor) {
        this.valor = valor.trim().toUpperCase();
    }

    public static Nome valueOf(String valor) {
        return new Nome(valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Nome) {
            final Nome other = (Nome) obj;
            return Objects.equals(this.valor, other.valor);
        }
        return false;
    }

    @Override
    public String toString() {
        return valor;
    }

    @Override
    public int compareTo(@NotNull Nome o) {
        return valor.compareTo(o.valor);
    }
}