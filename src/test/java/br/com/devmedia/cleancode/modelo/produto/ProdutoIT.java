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

/**
 *
 */
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
