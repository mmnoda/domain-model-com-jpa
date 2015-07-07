package br.com.devmedia.cleancode.modelo.cliente;

import br.com.devmedia.cleancode.modelo.Dinheiro;
import br.com.devmedia.cleancode.modelo.Nome;
import com.google.common.base.MoreObjects;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

import static br.com.devmedia.cleancode.modelo.cliente.PedidosSet.newPedidosSet;
import static br.com.devmedia.cleancode.modelo.cliente.TipoCliente.BRONZE;
import static br.com.devmedia.cleancode.modelo.cliente.TipoCliente.SEM_CLASSIFICACAO;
import static javax.persistence.TemporalType.DATE;

/**
 *
 */
@Entity
public class Cliente implements Serializable {

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
    private DataNascimento nascimento;

    @Embedded
    PedidosSet pedidos = newPedidosSet();

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
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("cpf", cpf)
                .add("nome", nome)
                .add("nascimento", nascimento)
                .add("pedidos", pedidos)
                .add("total", total)
                .toString();
    }

    public TipoCliente calcularTipoCliente() {
        if (pedidos.isEmpty()) {
            return SEM_CLASSIFICACAO;
        }
        total = pedidos.calcularTotal();
        return BRONZE.identificar(this);
    }

    boolean possuiTotalPedidosMaiorQue(Dinheiro valor) {
        return total.compareTo(valor) >= 0;
    }

    boolean possuiTotalPedidosEntre(Dinheiro valorInicial, Dinheiro valorFinal) {
        return possuiTotalPedidosMaiorQue(valorInicial) && total.compareTo(valorFinal) < 0;
    }

    public boolean isMaiorIdade() {
        return getIdade().isMaiorIdade();
    }

    public void setNome(Nome nome) {
        this.nome = nome;
    }

    public Idade getIdade() {
        return nascimento.getIdade();
    }

    public Integer getId() {
        return id;
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

    public PedidosSet getPedidos() {
        return pedidos;
    }

    public DataNascimento getNascimento() {
        return nascimento;
    }

    public void setNascimento(DataNascimento nascimento) {
        this.nascimento = nascimento;
    }
}
