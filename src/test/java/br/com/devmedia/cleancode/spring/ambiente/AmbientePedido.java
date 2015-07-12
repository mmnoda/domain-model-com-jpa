package br.com.devmedia.cleancode.spring.ambiente;

import br.com.devmedia.cleancode.modelo.pedido.Pedido;
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
public class AmbientePedido {

    @PersistenceContext
    private EntityManager em;

    public Pedido getPedidoFaturado() {
        return em.find(Pedido.class, 0);
    }
}
