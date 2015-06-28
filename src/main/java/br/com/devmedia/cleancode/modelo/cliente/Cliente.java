package br.com.devmedia.cleancode.modelo.cliente;

import br.com.devmedia.cleancode.modelo.pedido.Pedido;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

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
    private Integer version;

    @NotNull
    private String cpf;

    @NotNull
    private String nome;

    @OneToMany(mappedBy = "cliente")
    Set<Pedido> pedidos = new LinkedHashSet<>();

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Cliente) {
            final Cliente other = (Cliente) obj;
            return Objects.equals(this.id, other.id);
        }
        return false;
    }

    @Override
    public Integer calcularTipoCliente() {
        if (pedidos.isEmpty()) {
            return null;
        }

        BigDecimal total = BigDecimal.ZERO;

        for (Pedido pedido : pedidos) {
            if (pedido.getEstado() == Pedido.FATURADO) {
                total = total.add(pedido.getValorTotalFinal());
            }
        }

        if (total.compareTo(DescontoCliente.QUINHENTOS) >= 0 && total.compareTo(DescontoCliente.TRES_MIL) == -1) {
            return DescontoCliente.CLIENTE_BRONZE;
        } else if (total.compareTo(DescontoCliente.TRES_MIL) >= 0 && total.compareTo(DescontoCliente.DEZ_MIL) == -1) {
            return DescontoCliente.CLIENTE_PRATA;
        } else if (total.compareTo(DescontoCliente.DEZ_MIL) >= 0) {
            return DescontoCliente.CLIENTE_OURO;
        }

        return -1;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}
