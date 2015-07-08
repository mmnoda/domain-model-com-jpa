package br.com.devmedia.cleancode.modelo.pedido;

import br.com.devmedia.cleancode.modelo.comum.Dinheiro;
import br.com.devmedia.cleancode.modelo.cliente.DescontoClienteConstants;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 */
@Embeddable
public class ItensPedidoList implements Serializable, Iterable<ItemPedido> {

    private static final long serialVersionUID = -8476382593568230652L;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens = new LinkedList<>();

    protected ItensPedidoList() {
    }

    public static ItensPedidoList newItensPedidoList() {
        return new ItensPedidoList();
    }

    @Override
    public int hashCode() {
        return Objects.hash(itens);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ItensPedidoList) {
            final ItensPedidoList other = (ItensPedidoList) obj;
            return Objects.equals(this.itens, other.itens);
        }
        return false;
    }

    @Override
    public String toString() {
        return itens.toString();
    }

    public void add(ItemPedido item) {
        itens.add(item);
    }

    @Override
    public Iterator<ItemPedido> iterator() {
        return itens.iterator();
    }

    public boolean possuiItemComValorMaiorOuIgualQue3000() {
        return itens.stream().anyMatch(item -> item.getValorUnitario().compareTo(DescontoClienteConstants.TRES_MIL) >= 0);
    }

    public Dinheiro calcularValorTotalItens() {
        return itens.stream().map(ItemPedido::getValorTotal).reduce(Dinheiro::add).orElse(Dinheiro.ZERO);
    }

    public void remove(ItemPedido item) {
        itens.remove(item);
    }
}
