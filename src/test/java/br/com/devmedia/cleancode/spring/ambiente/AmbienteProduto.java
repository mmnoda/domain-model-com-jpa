package br.com.devmedia.cleancode.spring.ambiente;

import br.com.devmedia.cleancode.modelo.produto.Produto;
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
public class AmbienteProduto {

    @PersistenceContext
    private EntityManager em;

    public Produto getArroz() {
        return em.find(Produto.class, 0);
    }

    public Produto getAbc() {
        return em.find(Produto.class, -3);
    }

    public Produto getTv() {
        return em.find(Produto.class, -4);
    }
}
