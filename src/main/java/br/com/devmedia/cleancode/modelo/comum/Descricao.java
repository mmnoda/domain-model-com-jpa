package br.com.devmedia.cleancode.modelo.comum;

import java.io.Serializable;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 *
 */
public class Descricao implements Serializable, Comparable<Descricao> {

    private static final long serialVersionUID = 5026614055708173550L;

    private final String valor;

    private Descricao(String valor) {
        this.valor = valor;
    }

    public static Descricao valueOf(String valor) {
        checkArgument(!isNullOrEmpty(valor), "Descrição nulo ou vazio");
        return new Descricao(valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Descricao) {
            final Descricao other = (Descricao) obj;
            return Objects.equals(this.valor, other.valor);
        }
        return false;
    }

    @Override
    public String toString() {
        return valor;
    }

    @Override
    public int compareTo(Descricao o) {
        return valor.compareTo(o.valor);
    }
}
