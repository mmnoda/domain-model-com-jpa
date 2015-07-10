package br.com.devmedia.cleancode.modelo.pedido;

import br.com.devmedia.cleancode.spring.TesteDeIntegracao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static br.com.devmedia.cleancode.modelo.pedido.NumeroPedido.newNumeroPedido;
import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
@TesteDeIntegracao
@RunWith(SpringJUnit4ClassRunner.class)
public class NumeroPedidoIT {

    @PersistenceContext
    private EntityManager em;

    private NumeroPedido numeroPedido;

    @Test
    @Transactional
    public void deve_salvar_numero_pedido() {
        numeroPedido = newNumeroPedido();
        em.persist(numeroPedido);
        assertThat(numeroPedido.getId()).isNotNull();
    }
}
