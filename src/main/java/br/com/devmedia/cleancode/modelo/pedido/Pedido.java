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

import br.com.devmedia.cleancode.modelo.cliente.Cliente;
import br.com.devmedia.cleancode.modelo.cliente.TipoCliente;
import br.com.devmedia.cleancode.modelo.comum.Dinheiro;
import br.com.devmedia.cleancode.modelo.comum.Percentual;
import com.google.common.base.MoreObjects;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

import static br.com.devmedia.cleancode.modelo.pedido.ItensPedidoList.newItensPedidoList;
import static br.com.devmedia.cleancode.modelo.pedido.StatusPedido.ABERTO;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Objects.isNull;

@Entity
public class Pedido implements Serializable {

    private static final long serialVersionUID = -7709238927106303434L;

    @Id
    @GeneratedValue
    private Integer id;

    @Version
    Integer version;

    @OneToOne
    @JoinColumn(unique = true)
    @NotNull
    NumeroPedido numero;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    @NotNull
    private Cliente cliente;

    @NotNull
    StatusPedido estado;

    @NotNull
    private Dinheiro valorTotalItens;

    @NotNull
    private Dinheiro desconto;

    @NotNull
    private Dinheiro valorTotalFinal;

    @Embedded
    ItensPedidoList itens;

    protected Pedido() {
    }

    private Pedido(Cliente cliente, NumeroPedido numero) {
        validar(cliente, numero);
        this.cliente = cliente;
        this.numero = numero;
        inicializar();
    }

    private void validar(Cliente cliente, NumeroPedido numero) {
        checkArgument(!isNull(cliente), "Cliente nulo");
        checkNotNull(!isNull(numero), "NumeroPedido nulo");
    }

    public static Pedido newPedido(Cliente cliente, NumeroPedido numero) {
        return new Pedido(cliente, numero);
    }

    private void inicializar() {
        estado = ABERTO;
        valorTotalItens = Dinheiro.ZERO;
        desconto = Dinheiro.ZERO;
        itens = newItensPedidoList();
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pedido) {
            final Pedido other = (Pedido) obj;
            return Objects.equals(this.numero, other.numero);
        }
        return false;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("numero", numero)
                .add("cliente", cliente)
                .add("estado", estado)
                .add("valorTotalItens", valorTotalItens)
                .add("desconto", desconto)
                .add("valorTotalFinal", valorTotalFinal)
                .toString();
    }

    public Pedido adicionar(ItemPedido item) {
        if (estado.podeIncluirNovoItem()) {
            itens.add(item);
            calcularValorTotalItens();
        } else {
            lancarExcecaoItensDoPedidoNaoPodeSerAlterado();
        }
        return this;
    }

    private void lancarExcecaoItensDoPedidoNaoPodeSerAlterado() {
        throw new IllegalStateException("Os itens do pedido não pode ser alterado");
    }

    public Pedido remover(ItemPedido item) {
        if (estado.podeRemoverItem()) {
            itens.remove(item);
            calcularValorTotalItens();
        } else {
            lancarExcecaoItensDoPedidoNaoPodeSerAlterado();
        }
        return this;
    }

    void aplicarDesconto() {
        final TipoCliente tipoCliente = cliente.calcularTipoCliente();
        final Percentual percentualDesconto = tipoCliente.calcularPercentualDesconto(this);
        desconto = percentualDesconto.calcular(valorTotalItens);
    }

    public boolean possuiItemComValorMaiorOuIgualQue3000() {
        return itens.possuiItemComValorMaiorOuIgualQue3000();
    }

    void calcularValorFinal() {
        valorTotalFinal = valorTotalItens.add(desconto.negate());
    }

    void calcularValorTotalItens() {
        valorTotalItens = itens.calcularValorTotalItens();
    }

    public void faturar() {
        estado.faturar(this);
    }

    public void cancelar() {
        estado.cancelar(this);
    }

    void setEstado(StatusPedido estado) {
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Dinheiro getValorTotalItens() {
        return valorTotalItens;
    }

    public Dinheiro getDesconto() {
        return desconto;
    }

    public Dinheiro getValorTotalFinal() {
        return valorTotalFinal;
    }

    public ItensPedidoList getItens() {
        return itens;
    }

    public StatusPedido getEstado() {
        return estado;
    }

    public NumeroPedido getNumero() {
        return numero;
    }
}
