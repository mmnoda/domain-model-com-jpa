package br.com.devmedia.cleancode.modelo.produto;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 */
public class Codigo implements Serializable {

    private static final long serialVersionUID = 6050486045943534588L;

    private final String valor;

    private Codigo(String valor) {
        this.valor = valor;
    }

    public static Codigo valueOf(String valor) {
        return new Codigo(valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Codigo) {
            final Codigo other = (Codigo) obj;
            return Objects.equals(this.valor, other.valor);
        }
        return false;
    }

    @Override
    public String toString() {
        return valor;
    }
}
