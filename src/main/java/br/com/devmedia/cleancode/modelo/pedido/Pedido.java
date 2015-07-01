package br.com.devmedia.cleancode.modelo.pedido;

import br.com.devmedia.cleancode.infraestrutura.FormatadorSingleton;
import br.com.devmedia.cleancode.modelo.Dinheiro;
import br.com.devmedia.cleancode.modelo.Percentual;
import br.com.devmedia.cleancode.modelo.cliente.Cliente;
import br.com.devmedia.cleancode.modelo.cliente.DescontoCliente;
import br.com.devmedia.cleancode.modelo.cliente.TipoCliente;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static br.com.devmedia.cleancode.modelo.pedido.StatusPedido.*;

/**
 */
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

    private Dinheiro valorTotalItens;

    private Dinheiro desconto;

    private Dinheiro valorTotalFinal;

    @OneToMany(mappedBy = "pedido")
    List<ItemPedido> itens;

    protected Pedido() {
    }

    private Pedido(Cliente cliente, NumeroPedido numero) {
        this.cliente = cliente;
        this.numero = numero;
        inicializar();
    }

    public static Pedido newPedido(Cliente cliente, NumeroPedido numero) {
        return new Pedido(cliente, numero);
    }

    private void inicializar() {
        estado = ABERTO;
        valorTotalItens = Dinheiro.ZERO;
        desconto = Dinheiro.ZERO;
        itens = new LinkedList<>();
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

    public Pedido remover(ItemPedido item) {
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
            final TipoCliente tipoCliente = cliente.calcularTipoCliente();

            Percentual percentualDesconto = tipoCliente.calcularPercentualDesconto(this);
            desconto = percentualDesconto.calcular(valorTotalItens);
        }
    }

     public boolean possuiItemComValorMaiorOuIgualQue3000() {
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
        valorTotalItens = Dinheiro.ZERO;
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
        return FormatadorSingleton.INSTANCE.formatar(valorTotalItens.toBigDecimal());
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

    public void setValorTotalItens(Dinheiro valorTotalItens) {
        this.valorTotalItens = valorTotalItens;
    }

    public Dinheiro getDesconto() {
        return desconto;
    }

    public void setDesconto(Dinheiro desconto) {
        this.desconto = desconto;
    }

    public Dinheiro getValorTotalFinal() {
        return valorTotalFinal;
    }

    public void setValorTotalFinal(Dinheiro valorTotalFinal) {
        this.valorTotalFinal = valorTotalFinal;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public StatusPedido getEstado() {
        return estado;
    }

    public void setEstado(StatusPedido estado) {
        this.estado = estado;
    }

    public NumeroPedido getNumero() {
        return numero;
    }

}
