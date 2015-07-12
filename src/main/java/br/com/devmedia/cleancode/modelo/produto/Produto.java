package br.com.devmedia.cleancode.modelo.produto;

import br.com.devmedia.cleancode.modelo.comum.Descricao;
import br.com.devmedia.cleancode.modelo.comum.Dinheiro;
import br.com.devmedia.cleancode.modelo.comum.Nome;
import com.google.common.base.MoreObjects;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 */
@Entity
public class Produto implements Serializable {

    private static final long serialVersionUID = -4715596198193856107L;

    @Id
    @GeneratedValue
    private Integer id;

    @Version
    Integer version;

    @NotNull
    @Column(unique = true, length = 30)
    private Codigo codigo;

    @NotNull
    @Column(length = 150)
    private Nome nome;

    @NotNull
    private Descricao descricao;

    @NotNull
    private Dinheiro preco;

    protected Produto() {
    }

    public Produto(Builder builder) {
        this.codigo = builder.codigo;
        this.nome = builder.nome;
        this.descricao = builder.descricao;
        this.preco = builder.preco;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Produto) {
            final Produto other = (Produto) obj;
            return Objects.equals(this.codigo, other.codigo);
        }
        return false;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("codigo", codigo)
                .add("nome", nome)
                .add("descricao", descricao)
                .add("preco", preco)
                .toString();
    }

    public Integer getId() {
        return id;
    }

    public Codigo getCodigo() {
        return codigo;
    }

    public void setCodigo(Codigo codigo) {
        this.codigo = codigo;
    }

    public Nome getNome() {
        return nome;
    }

    public void setNome(Nome nome) {
        this.nome = nome;
    }

    public Descricao getDescricao() {
        return descricao;
    }

    public void setDescricao(Descricao descricao) {
        this.descricao = descricao;
    }

    public Dinheiro getPreco() {
        return preco;
    }

    public void setPreco(Dinheiro preco) {
        this.preco = preco;
    }

    public static class Builder {

        private Codigo codigo;

        private Nome nome;

        private Descricao descricao;

        private Dinheiro preco = Dinheiro.ZERO;

        private Builder() {
        }

        public Builder codigo(Codigo codigo) {
            this.codigo = codigo;
            return this;
        }

        public Builder nome(Nome nome) {
            this.nome = nome;
            return this;
        }

        public Builder descricao(Descricao descricao) {
            this.descricao = descricao;
            return this;
        }

        public Builder preco(Dinheiro preco) {
            this.preco = preco;
            return this;
        }

        public Produto build() {
            return new Produto(this);
        }
    }
}
