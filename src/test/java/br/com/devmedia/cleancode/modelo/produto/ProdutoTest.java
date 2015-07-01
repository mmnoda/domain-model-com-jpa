package br.com.devmedia.cleancode.modelo.produto;

import br.com.devmedia.cleancode.modelo.Descricao;
import br.com.devmedia.cleancode.modelo.Dinheiro;
import br.com.devmedia.cleancode.modelo.Nome;
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
    public void deve_construir_com_builder() {
        Nome banana = Nome.valueOf("Banana");
        Descricao fruta = Descricao.valueOf("Fruta");
        Dinheiro preco = Dinheiro.valueOf(10);
        produto = Produto.builder().codigo(getCodigo1()).nome(banana).
                descricao(fruta).preco(preco).build();
        assertThat(produto).isNotNull();
        assertThat(produto.getNome()).isNotNull().isEqualTo(banana);
        assertThat(produto.getDescricao()).isNotNull().isEqualTo(fruta);
        assertThat(produto.getPreco()).isNotNull().isEqualTo(preco);
    }

    @Test
    public void deve_ser_igual_ao_proprio() {
        produto.setCodigo(getCodigo1());
        assertThat(produto).isEqualTo(produto);
    }

    @Test
    public void deve_implementar_equals_consistente() {
        produto.setCodigo(getCodigo1());
        assertProdutoIgual();
        assertProdutoDiferente();
    }

    private void assertProdutoDiferente() {
        Produto outroDiferente = new Produto();
        outroDiferente.setCodigo(Codigo.valueOf("2"));
        assertThat(produto).isNotEqualTo(outroDiferente);
    }

    private Codigo getCodigo1() {
        return Codigo.valueOf("1");
    }

    private void assertProdutoIgual() {
        Produto outroIgual = new Produto();
        outroIgual.setCodigo(getCodigo1());
        assertThat(produto).isEqualTo(outroIgual);
    }
}