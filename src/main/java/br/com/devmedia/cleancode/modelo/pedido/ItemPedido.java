package br.com.devmedia.cleancode.modelo.pedido;

import br.com.devmedia.cleancode.modelo.produto.Produto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

/**
 *
 */
@Entity
public class ItemPedido implements Serializable {

    private static final long serialVersionUID = -1312001855343698639L;

    @Id
    @GeneratedValue
    private Integer id;

    @Version
    private Integer version;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    @NotNull
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    @NotNull
    private Produto produto;

    @NotNull
    private BigInteger quantidade;

    @NotNull
    private BigDecimal valorUnitario;

    @NotNull
    private BigDecimal valorTotal;

    ItemPedido() {
    }

    public ItemPedido(Pedido pedido, Produto produto, BigInteger quantidade) {
        this.pedido = pedido;
        this.quantidade = quantidade;
        this.produto = produto;
        this.valorUnitario = produto.getPreco();
        calcularValorTotal();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ItemPedido) {
            final ItemPedido other = (ItemPedido) obj;
            return Objects.equals(this.id, other.id);
        }
        return false;
    }

    private void calcularValorTotal() {
        valorTotal = valorUnitario.multiply(new BigDecimal(quantidade));
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public BigInteger getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigInteger quantidade) {
        this.quantidade = quantidade;
        calcularValorTotal();
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

}
