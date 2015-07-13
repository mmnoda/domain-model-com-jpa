package br.com.devmedia.cleancode.modelo.cliente;

import java.io.Serializable;
import java.util.Formattable;
import java.util.Formatter;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 *
 */
public class Cpf implements Serializable, Comparable<Cpf>, Formattable {

    private static final long serialVersionUID = -614188801416251816L;

    private static final Pattern PATTERN = Pattern.compile("(\\d{3})(\\d{3})(\\d{3})(\\d{2})");

    private static final String FORMATO_CPF = "$1.$2.$3-$4";

    private final String valor;

    private Cpf(String valor) {
        checkArgument(valor.matches("\\d{11}"), "CPF inválido");
        this.valor = valor;
    }

    public static Cpf valueOf(String valor) {
        checkArgument(!isNullOrEmpty(valor), "CPF nulo ou vazio");
        return new Cpf(valor.replaceAll("[\\D]+", ""));
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

    @Override
    public void formatTo(Formatter formatter, int flags, int width, int precision) {
        Matcher matcher = PATTERN.matcher(valor);
        if (matcher.matches()) {
            formatter.format(matcher.replaceAll(FORMATO_CPF));
        }
    }
}
