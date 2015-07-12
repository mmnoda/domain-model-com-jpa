package br.com.devmedia.cleancode.spring.ambiente;

import br.com.devmedia.cleancode.modelo.pedido.NumeroPedido;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.springframework.transaction.annotation.Propagation.MANDATORY;

/**
 *
 */
@Component
@Transactional(propagation = MANDATORY)
public class AmbienteNumeroPedido {

    @PersistenceContext
    private EntityManager em;

    public NumeroPedido getNumero1000() {
        return em.find(NumeroPedido.class, 1000);
    }
}
