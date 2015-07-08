package br.com.devmedia.cleancode.modelo.comum.converter;

import br.com.devmedia.cleancode.modelo.comum.Descricao;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 */
@Converter(autoApply = true)
public class DescricaoJpaConverter implements AttributeConverter<Descricao, String> {

    @Override
    public String convertToDatabaseColumn(Descricao descricao) {
        return descricao.toString();
    }

    @Override
    public Descricao convertToEntityAttribute(String s) {
        return Descricao.valueOf(s);
    }
}
