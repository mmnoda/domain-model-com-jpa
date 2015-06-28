package br.com.devmedia.cleancode.modelo.produto;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class ProdutoTest {

    private Produto produto;

    @Before
    public void setUp() {
        produto = new Produto();
    }

    @Test
    public void deve_ser_igual_ao_proprio() {
        produto.setId(1);
        assertThat(produto).isEqualTo(produto);
    }

    @Test
    public void deve_implementar_equals_consistente() {
        produto.setId(1);
        assertProdutoIgual();
        assertProdutoDiferente();
    }

    private void assertProdutoDiferente() {
        Produto outroDiferente = new Produto();
        outroDiferente.setId(2);
        assertThat(produto).isNotEqualTo(outroDiferente);
    }

    private void assertProdutoIgual() {
        Produto outroIgual = new Produto();
        outroIgual.setId(1);
        assertThat(produto).isEqualTo(outroIgual);
    }
}