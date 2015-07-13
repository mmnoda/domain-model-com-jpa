package br.com.devmedia.cleancode.modelo.cliente;

import br.com.devmedia.cleancode.modelo.comum.Nome;
import br.com.devmedia.cleancode.spring.TesteDeIntegracao;
import br.com.devmedia.cleancode.spring.ambiente.AmbienteCliente;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static br.com.devmedia.cleancode.modelo.cliente.Cliente.newCliente;
import static br.com.devmedia.cleancode.modelo.cliente.TipoCliente.BRONZE;
import static br.com.devmedia.cleancode.modelo.cliente.TipoCliente.OURO;
import static br.com.devmedia.cleancode.modelo.cliente.TipoCliente.PRATA;
import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
@TesteDeIntegracao
@RunWith(SpringJUnit4ClassRunner.class)
public class ClienteIT {

    private final Cpf cpf = Cpf.valueOf("883.370.725-39");
    private final Nome nome = Nome.valueOf("Cliente Teste");
    private final DataNascimento nascimento = DataNascimento.of(199, 10, 12);

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private AmbienteCliente ambienteCliente;

    private Cliente cliente;
    private TipoCliente tipoCliente;

    @Test
    @Transactional
    public void deve_salvar_novo_cliente() {
        buildCliente();
        assertClienteConstruidoComSucesso();
        salvar();
        assertClienteSalvoComSucesso();
    }

    @Test
    @Transactional
    public void deve_excluir_cliente() {
        cliente = ambienteCliente.getJoseDosSantos();
        assertThat(cliente).isNotNull();
        em.remove(cliente);
        assertThat(ambienteCliente.getJoseDosSantos()).isNull();
    }

    @Test
    @Transactional
    public void deve_identificar_cliente_como_tipo_BRONZE() {
        cliente = ambienteCliente.getClienteBronze();
        calcularTipoCliente();
        assertTipoClienteIgualA(BRONZE);
    }

    @Test
    @Transactional
    public void deve_identificar_cliente_como_tipo_PRATA() {
        cliente = ambienteCliente.getClientePrata();
        calcularTipoCliente();
        assertTipoClienteIgualA(PRATA);
    }

    @Test
    @Transactional
    public void deve_identificar_cliente_como_tipo_OURO() {
        cliente = ambienteCliente.getClienteOuro();
        calcularTipoCliente();
        assertTipoClienteIgualA(OURO);
    }

    private void assertTipoClienteIgualA(TipoCliente ouro) {
        assertThat(tipoCliente).isNotNull().isEqualTo(ouro);
    }

    private void calcularTipoCliente() {
        tipoCliente = cliente.calcularTipoCliente();
    }

    private void assertClienteConstruidoComSucesso() {
        assertThat(cliente.getCpf()).isEqualTo(cpf);
        assertThat(cliente.getNome()).isEqualTo(nome);
        assertThat(cliente.getNascimento()).isEqualTo(nascimento);
    }

    private void buildCliente() {
        cliente = newCliente(cpf, nome, nascimento);
    }

    private void assertClienteSalvoComSucesso() {
        assertThat(cliente.getId()).isNotNull().isGreaterThan(0);
        assertThat(cliente.version).isNotNull().isEqualTo(0);
        assertClienteConstruidoComSucesso();
    }

    private void salvar() {
        em.persist(cliente);
    }

}
