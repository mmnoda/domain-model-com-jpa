package br.com.devmedia.cleancode.modelo.comum.converter;

import br.com.devmedia.cleancode.modelo.comum.Percentual;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.math.BigDecimal;

/**
 *
 */
@Converter(autoApply = true)
public class PercentualJpaConverter implements AttributeConverter<Percentual, BigDecimal> {

    @Override
    public BigDecimal convertToDatabaseColumn(Percentual percentual) {
        return percentual.toBigDecimal();
    }

    @Override
    public Percentual convertToEntityAttribute(BigDecimal valor) {
        return Percentual.valueOf(valor);
    }
}
