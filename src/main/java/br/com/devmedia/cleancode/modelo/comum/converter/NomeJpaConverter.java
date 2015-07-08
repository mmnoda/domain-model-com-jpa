package br.com.devmedia.cleancode.modelo.comum.converter;

import br.com.devmedia.cleancode.modelo.comum.Nome;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 */
@Converter(autoApply = true)
public class NomeJpaConverter implements AttributeConverter<Nome, String> {

    @Override
    public String convertToDatabaseColumn(Nome nome) {
        return nome.toString();
    }

    @Override
    public Nome convertToEntityAttribute(String s) {
        return Nome.valueOf(s);
    }
}
