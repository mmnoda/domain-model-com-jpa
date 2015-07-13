package br.com.devmedia.cleancode.spring.ambiente;

import br.com.devmedia.cleancode.modelo.cliente.Cliente;
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
public class AmbienteCliente {

    @PersistenceContext
    private EntityManager em;

    public Cliente getJoseDosSantos() {
        return em.find(Cliente.class, 0);
    }

    public Cliente getClienteBronze() {
        return em.find(Cliente.class, -2);
    }

    public Cliente getClientePrata() {
        return em.find(Cliente.class, -3);
    }
    public Cliente getClienteOuro() {
        return em.find(Cliente.class, -4);
    }
}
