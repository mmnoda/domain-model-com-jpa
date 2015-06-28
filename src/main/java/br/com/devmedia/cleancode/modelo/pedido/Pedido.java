package br.com.devmedia.cleancode.modelo.pedido;

import br.com.devmedia.cleancode.modelo.cliente.DescontoCliente;
import br.com.devmedia.cleancode.infraestrutura.FormatadorSingleton;
import br.com.devmedia.cleancode.modelo.cliente.Cliente;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static br.com.devmedia.cleancode.infraestrutura.ArredondamentoConstants.ARREDONDAMENTO_PADRAO;
import static br.com.devmedia.cleancode.infraestrutura.ArredondamentoConstants.CASAS_DECIMAIS;

/**
 */
@Entity
public class Pedido implements Serializable {

    private static final long serialVersionUID = -7709238927106303434L;

    public static final int ABERTO = 0;
    public static final int CANCELADO = 1;
    public static final int FATURADO = 2;

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @NotNull
    Integer estado;

    private BigDecimal valorTotalItens;

    private BigDecimal desconto;

    private BigDecimal valorTotalFinal;

    @OneToMany(mappedBy = "pedido")
    List<ItemPedido> itens;

    protected Pedido() {
    }

    public Pedido(Cliente cliente) {
        this.cliente = cliente;
        inicializar();
    }

    private void inicializar() {
        estado = ABERTO;
        valorTotalItens = BigDecimal.ZERO;
        desconto = BigDecimal.ZERO.setScale(CASAS_DECIMAIS, ARREDONDAMENTO_PADRAO);
        itens = new LinkedList<>();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pedido) {
            final Pedido other = (Pedido) obj;
            return Objects.equals(this.id, other.id);
        }
        return false;
    }

    public Pedido adicionar(ItemPedido item) {
        if (estado == ABERTO) {
            itens.add(item);
            calcularValorTotalItens();
        } else {
            return lancarExcecaoItensDoPedidoNaoPodeSerAlterado();
        }
        return this;
    }

    private Pedido lancarExcecaoItensDoPedidoNaoPodeSerAlterado() {
        throw new IllegalStateException("Os itens do pedido não pode ser alterado");
    }

    public Pedido removerItem(ItemPedido item) {
        if (estado == ABERTO) {
            itens.remove(item);
            calcularValorTotalItens();
        } else {
            lancarExcecaoItensDoPedidoNaoPodeSerAlterado();
        }
        return this;
    }

    private void aplicarDesconto() {
        if (estado == ABERTO) {

            final Integer tipoCliente = cliente.calcularTipoCliente();

            if (tipoCliente == null) {
                return;
            }

            final BigDecimal _100 = new BigDecimal(100);
            BigDecimal percentualDesconto;
            switch (tipoCliente) {
                case DescontoCliente.CLIENTE_BRONZE:
                    percentualDesconto = valorTotalItens.compareTo(DescontoCliente.QUINHENTOS) >= 0 ?
                            DescontoCliente._3_PORCENTO : BigDecimal.ZERO;
                    break;
                case DescontoCliente.CLIENTE_PRATA:
                    percentualDesconto = valorTotalItens.compareTo(DescontoCliente.TRES_MIL) >= 0 ?
                            DescontoCliente._5_PORCENTO : DescontoCliente._3_PORCENTO;

                    if (possuiItemComValorMaiorOuIgualQue3000()) {
                        percentualDesconto = percentualDesconto.add(DescontoCliente._3_PORCENTO);
                    }

                    break;
                case DescontoCliente.CLIENTE_OURO:
                    percentualDesconto = DescontoCliente._10_PORCENTO;
                    break;
                default:
                    throw new IllegalArgumentException("Tipo Cliente Inválido");
            }

            desconto = valorTotalItens.multiply(percentualDesconto).divide(_100, RoundingMode.HALF_EVEN).
                    setScale(CASAS_DECIMAIS, ARREDONDAMENTO_PADRAO);
        }
    }

    private boolean possuiItemComValorMaiorOuIgualQue3000() {
        for (ItemPedido item : itens) {
            if (item.getValorUnitario().compareTo(DescontoCliente.TRES_MIL) >= 0) {
                return true;
            }
        }
        return false;
    }

    private void calcularValorFinal() {
        valorTotalFinal = valorTotalItens.add(desconto.negate());
    }

    private void calcularValorTotalItens() {
        valorTotalItens = BigDecimal.ZERO;
        for (ItemPedido item : itens) {
            valorTotalItens = valorTotalItens.add(item.getValorTotal());
        }
    }

    public void faturar() {
        if (estado == ABERTO) {
            calcularValorTotalItens();
            aplicarDesconto();
            calcularValorFinal();
            estado = FATURADO;
        } else {
            throw new IllegalStateException("Pedido não está aberto");
        }

    }

    public void cancelar() {
        if (estado == FATURADO) {
            estado = CANCELADO;
        } else {
            throw new IllegalStateException("Pedido não está faturado");
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValorTotalItensAsString() {
        return FormatadorSingleton.getInstance().formatar(valorTotalItens);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getValorTotalItens() {
        return valorTotalItens;
    }

    public void setValorTotalItens(BigDecimal valorTotalItens) {
        this.valorTotalItens = valorTotalItens;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public BigDecimal getValorTotalFinal() {
        return valorTotalFinal;
    }

    public void setValorTotalFinal(BigDecimal valorTotalFinal) {
        this.valorTotalFinal = valorTotalFinal;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }
}
