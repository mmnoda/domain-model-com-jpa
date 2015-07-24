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
import br.com.devmedia.cleancode.modelo.comum.Quantidade;
import br.com.devmedia.cleancode.modelo.produto.Produto;
import com.google.common.base.MoreObjects;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class ItemPedido implements Serializable {

    private static final long serialVersionUID = -1312001855343698639L;

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    @NotNull
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    @NotNull
    private Produto produto;

    @NotNull
    private Quantidade quantidade;

    @NotNull
    private Dinheiro valorUnitario;

    @NotNull
    private Dinheiro valorTotal;

    protected ItemPedido() {
    }

    private ItemPedido(Pedido pedido, Produto produto, Quantidade quantidade) {
        this.pedido = pedido;
        this.quantidade = quantidade;
        this.produto = produto;
        this.valorUnitario = produto.getPreco();
        calcularValorTotal();
    }

    public static ItemPedido newItemPedido(Pedido pedido, Produto produto, Quantidade quantidade) {
        return new ItemPedido(pedido, produto, quantidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pedido, produto);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ItemPedido) {
            final ItemPedido other = (ItemPedido) obj;
            return Objects.equals(this.pedido, other.pedido) && Objects.equals(this.produto, other.produto);
        }
        return false;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("pedido", pedido)
                .add("produto", produto)
                .add("quantidade", quantidade)
                .add("valorUnitario", valorUnitario)
                .add("valorTotal", valorTotal)
                .toString();
    }

    private void calcularValorTotal() {
        valorTotal = valorUnitario.multiply(quantidade);
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getId() {
        return id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public Quantidade getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Quantidade quantidade) {
        this.quantidade = quantidade;
        calcularValorTotal();
    }

    public Dinheiro getValorUnitario() {
        return valorUnitario;
    }

    public Dinheiro getValorTotal() {
        return valorTotal;
    }

}
