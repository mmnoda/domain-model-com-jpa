package br.com.devmedia.cleancode.modelo.cliente;

import java.io.Serializable;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 *
 */
public class Cpf implements Serializable, Comparable<Cpf> {

    private static final long serialVersionUID = -614188801416251816L;

    private final String valor;

    private Cpf(String valor) {
        this.valor = valor;
    }

    public static Cpf valueOf(String valor) {
        checkArgument(!isNullOrEmpty(valor), "CPF nulo ou vazio");
        return new Cpf(valor);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(valor);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Cpf) {
            final Cpf other = (Cpf) obj;
            return Objects.equals(this.valor, other.valor);
        }
        return false;
    }

    @Override
    public String toString() {
        return valor;
    }

    @Override
    public int compareTo(Cpf o) {
        return valor.compareTo(o.valor);
    }

}
