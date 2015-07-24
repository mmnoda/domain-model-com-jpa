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

package br.com.devmedia.cleancode.modelo.produto;

import br.com.devmedia.cleancode.modelo.comum.Descricao;
import br.com.devmedia.cleancode.modelo.comum.Dinheiro;
import br.com.devmedia.cleancode.modelo.comum.Nome;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProdutoTest {

    private Nome banana;

    private Descricao fruta;

    private Dinheiro preco;

    private Produto produto;

    @Before
    public void setUp() {
        produto = new Produto();
        banana = Nome.valueOf("Banana");
        fruta = Descricao.valueOf("Fruta");
        preco = Dinheiro.valueOf(10);
    }

    @Test
    public void deve_construir_com_builder() {
        produto = Produto.builder().codigo(getCodigo1()).nome(banana).
                descricao(fruta).preco(preco).build();
        assertProdutoConstruidoComSucesso();
    }

    @Test
    public void deve_ser_igual_ao_proprio() {
        assertProdutoIgual(produto);
    }

    @Test
    public void deve_implementar_equals_consistente() {
        produto.setCodigo(getCodigo1());
        assertProdutoIgual(new Produto());
        assertProdutoDiferente();
    }

    @Test
    public void deve_alterar_preco() {
        produto.setPreco(preco);
        assertThat(produto.getPreco()).isEqualTo(preco);
    }

    @Test
    public void deve_alterar_descricao() {
        produto.setDescricao(fruta);
        assertThat(produto.getDescricao()).isEqualTo(fruta);
    }

    private void assertProdutoConstruidoComSucesso() {
        assertThat(produto).isNotNull();
        assertThat(produto.getNome()).isNotNull().isEqualTo(banana);
        assertThat(produto.getDescricao()).isNotNull().isEqualTo(fruta);
        assertThat(produto.getPreco()).isNotNull().isEqualTo(preco);
        assertThat(produto.getCodigo()).isNotNull().isEqualTo(getCodigo1());
    }

    private void assertProdutoDiferente() {
        Produto outroDiferente = new Produto();
        outroDiferente.setCodigo(Codigo.valueOf("2"));
        assertThat(produto).isNotEqualTo(outroDiferente);
        assertThat(produto.hashCode()).isNotEqualTo(outroDiferente.hashCode());
    }

    private Codigo getCodigo1() {
        return Codigo.valueOf("1");
    }

    private void assertProdutoIgual(Produto outroIgual) {
        outroIgual.setCodigo(getCodigo1());
        assertThat(produto).isEqualTo(outroIgual);
        assertThat(produto.hashCode()).isEqualTo(outroIgual.hashCode());
    }
}