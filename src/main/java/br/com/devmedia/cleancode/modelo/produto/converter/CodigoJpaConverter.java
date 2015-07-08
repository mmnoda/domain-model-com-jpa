package br.com.devmedia.cleancode.modelo.produto.converter;

import br.com.devmedia.cleancode.modelo.produto.Codigo;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 */
@Converter(autoApply = true)
public class CodigoJpaConverter implements AttributeConverter<Codigo, String> {

    @Override
    public String convertToDatabaseColumn(Codigo codigo) {
        return codigo.toString();
    }

    @Override
    public Codigo convertToEntityAttribute(String s) {
        return Codigo.valueOf(s);
    }
}
