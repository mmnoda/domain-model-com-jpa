package br.com.devmedia.cleancode;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 */
public class ItemPedido implements Serializable {

    private static final long serialVersionUID = -1312001855343698639L;

    @Id
    @GeneratedValue
    private Integer id;

    @Version
    private Integer version;

    private Pedido pedido;

    private Produto produto;

    private BigInteger quantidade;

    private BigDecimal valorUnitario;

    private BigDecimal valorTotal;

    ItemPedido() {
    }

    public ItemPedido(Pedido pedido, Produto produto, BigInteger quantidade) {
        this.pedido = pedido;
        this.produto = produto;
        this.quantidade = quantidade;
        valorUnitario = produto.getPreco();
        valorTotal = valorUnitario.multiply(new BigDecimal(quantidade));
    }

    public void calcularValorTotal() {
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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
