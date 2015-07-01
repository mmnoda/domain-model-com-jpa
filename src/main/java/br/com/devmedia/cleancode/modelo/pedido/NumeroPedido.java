package br.com.devmedia.cleancode.modelo.pedido;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 */
@Entity
public class NumeroPedido implements Serializable
{
    private static final long serialVersionUID = -1668268098929534320L;

    @Id
    @GeneratedValue
    private Integer id;

    protected NumeroPedido() {
    }

    private NumeroPedido(Integer id) {
        this.id = id;
    }

    public static NumeroPedido valueOf(Integer id) {
        return new NumeroPedido(id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NumeroPedido) {
            final NumeroPedido other = (NumeroPedido) obj;
            return Objects.equals(this.id, other.id);
        }
        return false;
    }

    @Override
    public String toString() {
        return id.toString();
    }


}
