package br.com.devmedia.cleancode.modelo.pedido;

import br.com.devmedia.cleancode.spring.TesteDeIntegracao;
import br.com.devmedia.cleancode.spring.ambiente.AmbienteNumeroPedido;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private AmbienteNumeroPedido ambienteNumeroPedido;

    private NumeroPedido numeroPedido;

    @Test
    @Transactional
    public void deve_salvar_numero_pedido() {
        numeroPedido = newNumeroPedido();
        em.persist(numeroPedido);
        assertThat(numeroPedido.getId()).isNotNull();
    }

    @Test
    @Transactional
    public void deve_excluir_numero_pedido() {
        numeroPedido = ambienteNumeroPedido.getNumero1000();
        em.remove(numeroPedido);
        assertThat(ambienteNumeroPedido.getNumero1000()).isNull();
    }
}
