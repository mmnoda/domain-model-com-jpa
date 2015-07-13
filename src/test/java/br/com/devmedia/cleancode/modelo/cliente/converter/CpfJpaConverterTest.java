package br.com.devmedia.cleancode.modelo.cliente.converter;

import br.com.devmedia.cleancode.modelo.cliente.Cpf;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class CpfJpaConverterTest {

    private final String CPF_ESPERADO = "97785537852";

    private CpfJpaConverter cpfJpaConverter;

    @Before
    public void setUp() {
        cpfJpaConverter = new CpfJpaConverter();
    }

    @Test
    public void deve_converter_string_para_cpf() {
        Cpf cpf = cpfJpaConverter.convertToEntityAttribute(CPF_ESPERADO);
        assertThat(cpf).isNotNull().isEqualTo(Cpf.valueOf(CPF_ESPERADO));
    }

    @Test
    public void deve_converter_cpf_para_string() {
        String cpf = cpfJpaConverter.convertToDatabaseColumn(Cpf.valueOf(CPF_ESPERADO));
        assertThat(cpf).isNotNull().isEqualTo(CPF_ESPERADO);
    }
}