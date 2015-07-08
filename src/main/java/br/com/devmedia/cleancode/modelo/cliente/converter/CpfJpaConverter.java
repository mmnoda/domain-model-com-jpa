package br.com.devmedia.cleancode.modelo.cliente.converter;

import br.com.devmedia.cleancode.modelo.cliente.Cpf;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 */
@Converter(autoApply = true)
public class CpfJpaConverter implements AttributeConverter<Cpf, String> {

    @Override
    public String convertToDatabaseColumn(Cpf cpf) {
        return cpf.toString();
    }

    @Override
    public Cpf convertToEntityAttribute(String s) {
        return Cpf.valueOf(s);
    }
}
