/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Márcio M. Noda
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package br.com.devmedia.cleancode.modelo.cliente;

import br.com.devmedia.cleancode.modelo.comum.Dinheiro;
import br.com.devmedia.cleancode.modelo.pedido.Pedido;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import static br.com.devmedia.cleancode.modelo.pedido.StatusPedido.FATURADO;
import static com.google.common.collect.Sets.newLinkedHashSet;

@Embeddable
public class PedidosSet implements Serializable, Iterable<Pedido> {

    private static final long serialVersionUID = -7854382052392174092L;

    @OneToMany(mappedBy = "cliente")
    Set<Pedido> pedidos;

    protected PedidosSet() {
    }

    private PedidosSet(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    protected static PedidosSet newPedidosSet() {
        return new PedidosSet().inicializar();
    }

    public static PedidosSet newPedidosSet(Set<Pedido> pedidos) {
        return new PedidosSet(pedidos);
    }

    private PedidosSet inicializar() {
        pedidos = newLinkedHashSet();
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

    public PedidosSet copia() {
        return newPedidosSet(newLinkedHashSet(pedidos));
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
