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

package br.com.devmedia.cleancode.modelo.cliente;

import br.com.devmedia.cleancode.modelo.comum.Dinheiro;
import br.com.devmedia.cleancode.modelo.comum.Nome;
import com.google.common.base.MoreObjects;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

import static br.com.devmedia.cleancode.modelo.cliente.PedidosSet.newPedidosSet;
import static br.com.devmedia.cleancode.modelo.cliente.TipoCliente.BRONZE;
import static br.com.devmedia.cleancode.modelo.cliente.TipoCliente.SEM_CLASSIFICACAO;
import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.isNull;

@Entity
public class Cliente implements Serializable {

    private static final long serialVersionUID = 85250768609444L;

    @Id
    @GeneratedValue
    private Integer id;

    @Version
    Integer version;

    @NotNull
    @Column(unique = true, length = 11)
    private Cpf cpf;

    @NotNull
    @Column(length = 150)
    private Nome nome;

    @NotNull
    private DataNascimento nascimento;

    @Embedded
    PedidosSet pedidos = newPedidosSet();

    @Transient
    private transient Dinheiro total;

    protected Cliente() {
    }

    private Cliente(Cpf cpf, Nome nome, DataNascimento nascimento) {
        validar(cpf, nome, nascimento);
        this.cpf = cpf;
        this.nome = nome;
        this.nascimento = nascimento;
    }

    private void validar(Cpf cpf, Nome nome, DataNascimento nascimento) {
        checkArgument(!isNull(cpf), "CPF nulo");
        checkArgument(!isNull(nome), "Nome nulo");
        checkArgument(!isNull(nascimento), "DataNascimento nulo");
    }

    public static Cliente newCliente(Cpf cpf, Nome nome, DataNascimento nascimento) {
        return new Cliente(cpf, nome, nascimento);
    }

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
