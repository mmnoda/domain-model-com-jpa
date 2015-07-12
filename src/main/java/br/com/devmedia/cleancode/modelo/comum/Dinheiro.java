package br.com.devmedia.cleancode.modelo.comum;

import br.com.devmedia.cleancode.infraestrutura.FormatadorSingleton;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Formattable;
import java.util.Formatter;
import java.util.Objects;

import static br.com.devmedia.cleancode.infraestrutura.ArredondamentoConstants.ARREDONDAMENTO_PADRAO;
import static br.com.devmedia.cleancode.infraestrutura.ArredondamentoConstants.CASAS_DECIMAIS;
import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.isNull;

/**
 *
 */
public class Dinheiro implements Serializable, Comparable<Dinheiro>, Formattable {

    private static final long serialVersionUID = -6881965660274224561L;

    public static final Dinheiro ZERO = valueOf(BigDecimal.ZERO);

    private final BigDecimal valor;

    private Dinheiro(BigDecimal valor) {
        checkArgument(!isNull(valor), "Valor do dinheiro nulo");
        this.valor = valor.setScale(CASAS_DECIMAIS, ARREDONDAMENTO_PADRAO);
    }

    public static Dinheiro valueOf(BigDecimal valor) {
        return new Dinheiro(valor);
    }

    public static Dinheiro valueOf(double valor) {
        return new Dinheiro(BigDecimal.valueOf(valor));
    }

    public static Dinheiro parse(String s) throws ParseException {
        return valueOf(FormatadorSingleton.INSTANCE.parse(s));
    }

    public static Dinheiro somar(Dinheiro d1, Dinheiro d2) {
        return d1.add(d2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Dinheiro) {
            final Dinheiro other = (Dinheiro) obj;
            return Objects.equals(this.valor, other.valor);
        }
        return false;
    }

    @Override
    public String toString() {
        return valor.toString();
    }

    @Override
    public int compareTo(Dinheiro o) {
        return valor.compareTo(o.valor);
    }

    public BigDecimal toBigDecimal() {
        return valor;
    }

    public Dinheiro add(Dinheiro outro) {
        return valueOf(valor.add(outro.valor));
    }

    public Dinheiro multiply(Quantidade quantidade) {
        return valueOf(valor.multiply(quantidade.toBigDecimal()));
    }

    public Dinheiro negate() {
        return valueOf(valor.negate());
    }

    @Override
    public void formatTo(Formatter formatter, int flags, int width, int precision) {
        formatter.format(FormatadorSingleton.INSTANCE.formatar(valor));
    }
}
