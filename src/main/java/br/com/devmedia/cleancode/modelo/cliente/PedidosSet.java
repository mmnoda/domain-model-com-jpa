package br.com.devmedia.cleancode.modelo.cliente;

import br.com.devmedia.cleancode.modelo.comum.Dinheiro;
import br.com.devmedia.cleancode.modelo.pedido.Pedido;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import static br.com.devmedia.cleancode.modelo.pedido.StatusPedido.FATURADO;

/**
 *
 */
@Embeddable
public class PedidosSet implements Serializable, Iterable<Pedido> {

    private static final long serialVersionUID = -7854382052392174092L;

    @OneToMany(mappedBy = "cliente")
    Set<Pedido> pedidos;

    protected PedidosSet() {
    }

    protected static PedidosSet newPedidosSet() {
        return new PedidosSet().inicializar();
    }

    private PedidosSet inicializar() {
        pedidos = new LinkedHashSet<>();
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pedidos);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PedidosSet) {
            final PedidosSet other = (PedidosSet) obj;
            return Objects.equals(this.pedidos, other.pedidos);
        }
        return false;
    }

    @Override
    public String toString() {
        return pedidos.toString();
    }

    public Dinheiro calcularTotal() {
        return pedidos.stream().filter(pedido -> pedido.getEstado() == FATURADO).
                map(Pedido::getValorTotalFinal).
                reduce(Dinheiro::somar).orElse(Dinheiro.ZERO);
    }

    @Override
    public Iterator<Pedido> iterator() {
        return pedidos.iterator();
    }

    public boolean isEmpty() {
        return pedidos.isEmpty();
    }

    public boolean add(Pedido pedido) {
        return pedidos.add(pedido);
    }

    public void clear() {
        pedidos.clear();
    }
}
