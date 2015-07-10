package br.com.devmedia.cleancode.modelo.cliente;

import br.com.devmedia.cleancode.modelo.comum.Nome;
import br.com.devmedia.cleancode.spring.TesteDeIntegracao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static br.com.devmedia.cleancode.modelo.cliente.Cliente.newCliente;
import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
@TesteDeIntegracao
@RunWith(SpringJUnit4ClassRunner.class)
public class ClienteIT {

    @PersistenceContext
    private EntityManager em;

    private Cliente cliente;

    @Test
    @Transactional
    public void deve_salvar_cliente() {
        cliente = newCliente(Cpf.valueOf("883.370.725-39"), Nome.valueOf("Cliente Teste"),
                DataNascimento.of(199, 10, 12));
        em.persist(cliente);
        assertThat(cliente.getId()).isNotNull();
    }
}
