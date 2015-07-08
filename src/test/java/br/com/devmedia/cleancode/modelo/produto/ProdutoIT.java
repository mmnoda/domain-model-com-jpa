package br.com.devmedia.cleancode.modelo.produto;

import br.com.devmedia.cleancode.modelo.comum.Descricao;
import br.com.devmedia.cleancode.modelo.comum.Dinheiro;
import br.com.devmedia.cleancode.modelo.comum.Nome;
import br.com.devmedia.cleancode.spring.TesteDeIntegracao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 */
@TesteDeIntegracao
@RunWith(SpringJUnit4ClassRunner.class)
public class ProdutoIT {

    @PersistenceContext
    private EntityManager em;

    private Produto produto;

    @Test
    @Transactional
    public void deve_salvar_produto() {
        produto = Produto.builder().codigo(Codigo.valueOf("123")).descricao(Descricao.valueOf("Teste")).
                nome(Nome.valueOf("Produto A")).preco(Dinheiro.valueOf(10)).build();

        em.persist(produto);
    }
}
