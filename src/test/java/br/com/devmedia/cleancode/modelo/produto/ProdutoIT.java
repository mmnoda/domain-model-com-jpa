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
import br.com.devmedia.cleancode.spring.TesteDeIntegracao;
import br.com.devmedia.cleancode.spring.ambiente.AmbienteProduto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@TesteDeIntegracao
@RunWith(SpringJUnit4ClassRunner.class)
public class ProdutoIT {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private AmbienteProduto ambienteProduto;

    private Produto produto;

    @Test
    @Transactional
    public void deve_salvar_produto() {
        produto = Produto.builder().codigo(Codigo.valueOf("A1A2A3")).descricao(Descricao.valueOf("Teste")).
                nome(Nome.valueOf("Produto A")).preco(Dinheiro.valueOf(10)).build();
        em.persist(produto);
        assertThat(produto.getId()).isNotNull();
        assertThat(produto.version).isNotNull().isEqualTo(0);
    }

    @Test
    @Transactional
    public void deve_excluir_produto() {
        produto = ambienteProduto.getArroz();
        assertThat(produto).isNotNull();
        em.remove(produto);
        assertThat(ambienteProduto.getArroz()).isNull();
    }
}
