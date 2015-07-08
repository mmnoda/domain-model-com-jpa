package br.com.devmedia.cleancode.modelo.comum.converter;

import br.com.devmedia.cleancode.modelo.comum.Quantidade;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 */
@Converter(autoApply = true)
public class QuantidadeJpaConverter implements AttributeConverter<Quantidade, Long> {

    @Override
    public Long convertToDatabaseColumn(Quantidade quantidade) {
        return quantidade.longValue();
    }

    @Override
    public Quantidade convertToEntityAttribute(Long valor) {
        return Quantidade.valueOf(valor);
    }
}
