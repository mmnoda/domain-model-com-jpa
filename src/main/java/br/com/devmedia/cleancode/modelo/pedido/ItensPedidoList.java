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

package br.com.devmedia.cleancode.modelo.pedido;

import br.com.devmedia.cleancode.modelo.comum.Dinheiro;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static br.com.devmedia.cleancode.modelo.cliente.DescontoClienteConstants.TRES_MIL;

@Embeddable
public class ItensPedidoList implements Serializable, Iterable<ItemPedido> {

    private static final long serialVersionUID = -8476382593568230652L;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    List<ItemPedido> itens;

    protected ItensPedidoList() {
    }

    public static ItensPedidoList newItensPedidoList() {
        return new ItensPedidoList().inicializar();
    }

    private ItensPedidoList inicializar() {
        itens = new LinkedList<>();
        return this;
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

    public void remove(ItemPedido item) {
        itens.remove(item);
    }

    public void clear() {
        itens.clear();
    }

    @Override
    public Iterator<ItemPedido> iterator() {
        return itens.iterator();
    }

    public boolean possuiItemComValorMaiorOuIgualQue3000() {
        return itens.stream().anyMatch(item -> item.getValorUnitario().compareTo(TRES_MIL) >= 0);
    }

    public Dinheiro calcularValorTotalItens() {
        return itens.stream().map(ItemPedido::getValorTotal).reduce(Dinheiro::somar).orElse(Dinheiro.ZERO);
    }
}
