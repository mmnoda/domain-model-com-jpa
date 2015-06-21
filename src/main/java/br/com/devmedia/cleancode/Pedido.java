package br.com.devmedia.cleancode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 */
@Entity
public class Pedido implements Serializable, DescontoCliente {

    private static final long serialVersionUID = -7709238927106303434L;

    @Id
    @GeneratedValue
    private Integer id;

    @Version
    private Integer version;

    @NotNull
    private String numero;

    @NotNull
    private String cliente;

    private BigDecimal valorTotalItens;

    private BigDecimal desconto = BigDecimal.ZERO;

    private BigDecimal valorTotalFinal;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens;

    public Pedido adicionarItem(ItemPedido item) {
        itens.add(item);
        calcularValorTotalItens();
        return this;
    }

    public Pedido removerItem(ItemPedido item) {
        itens.remove(item);
        calcularValorTotalItens();
        return this;
    }

    public void aplicarDesconto(final int tipoCliente) {
        final BigDecimal _100 = new BigDecimal(100);
        switch (tipoCliente) {
            case DescontoCliente.CLIENTE_BRONZE:
                desconto = valorTotalItens.multiply(DescontoCliente._3_PORCENTO).divide(_100, RoundingMode.HALF_EVEN);
                break;
            case DescontoCliente.CLIENTE_PRATA:
                desconto = valorTotalItens.multiply(DescontoCliente._5_PORCENTO).divide(_100, RoundingMode.HALF_EVEN);
                break;
            case DescontoCliente.CLIENTE_OURO:
                desconto = valorTotalItens.multiply(DescontoCliente._7_PORCENTO).divide(_100, RoundingMode.HALF_EVEN);
                break;
            default:
                throw new IllegalArgumentException("Tipo Cliente Inválido");
        }

    }

    public void calcularValorFinal() {
        valorTotalFinal = valorTotalItens.add(desconto.negate());
    }

    private void calcularValorTotalItens() {
        valorTotalItens = BigDecimal.ZERO;
        for (ItemPedido item : itens) {
            valorTotalItens = valorTotalItens.add(item.getValorTotal());
        }
    }

    public String getValorTotalItensAsString() {
        return FormatadorSingleton.getInstance().formatar(valorTotalItens);
    }
}
