package br.com.devmedia.cleancode.modelo.cliente;

import br.com.devmedia.cleancode.infraestrutura.DateTimeUtils;
import br.com.devmedia.cleancode.modelo.Dinheiro;
import br.com.devmedia.cleancode.modelo.Nome;
import br.com.devmedia.cleancode.modelo.pedido.Pedido;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import static br.com.devmedia.cleancode.modelo.cliente.TipoCliente.BRONZE;
import static br.com.devmedia.cleancode.modelo.cliente.TipoCliente.SEM_CLASSIFICACAO;
import static br.com.devmedia.cleancode.modelo.pedido.StatusPedido.FATURADO;
import static javax.persistence.TemporalType.DATE;

/**
 *
 */
@Entity
public class Cliente implements Serializable, DescontoCliente {

    private static final long serialVersionUID = 85250768609444L;

    @Id
    @GeneratedValue
    private Integer id;

    @Version
    Integer version;

    @NotNull
    private Cpf cpf;

    @NotNull
    private Nome nome;

    @NotNull
    @Temporal(DATE)
    private LocalDate nascimento;

    @OneToMany(mappedBy = "cliente")
    Set<Pedido> pedidos = new LinkedHashSet<>();

    @Transient
    transient Dinheiro total;

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Cliente) {
            final Cliente other = (Cliente) obj;
            return Objects.equals(this.cpf, other.cpf);
        }
        return false;
    }

    @Override
    public TipoCliente calcularTipoCliente() {
        if (pedidos.isEmpty()) {
            return SEM_CLASSIFICACAO;
        }

        total = Dinheiro.ZERO;

        for (Pedido pedido : pedidos) {
            if (pedido.getEstado() == FATURADO) {
                total = total.add(pedido.getValorTotalFinal());
            }
        }
        return BRONZE.identificar(this);
    }

    boolean possuiTotalPedidosMaiorQue(Dinheiro valor) {
        return total.compareTo(valor) >= 0;
    }

    boolean possuiTotalPedidosEntre(Dinheiro valorInicial, Dinheiro valorFinal) {
        return possuiTotalPedidosMaiorQue(valorInicial) && total.compareTo(valorFinal) < 0;
    }

    public void setNome(Nome nome) {
        this.nome = nome;
    }

    public boolean isMaiorIdade() {
        return getIdade() >= 18;
    }

    public int getIdade() {
        Period periodo = Period.between(nascimento, DateTimeUtils.INSTANCE.newLocalDate());
        return periodo.getYears();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cpf getCpf() {
        return cpf;
    }

    public void setCpf(Cpf cpf) {
        this.cpf = cpf;
    }

    public Nome getNome() {
        return nome;
    }

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }
}
