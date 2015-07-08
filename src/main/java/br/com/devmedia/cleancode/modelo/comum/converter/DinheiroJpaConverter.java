package br.com.devmedia.cleancode.modelo.comum.converter;

import br.com.devmedia.cleancode.modelo.comum.Dinheiro;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.math.BigDecimal;

/**
 *
 */
@Converter(autoApply = true)
public class DinheiroJpaConverter implements AttributeConverter<Dinheiro, BigDecimal> {

    @Override
    public BigDecimal convertToDatabaseColumn(Dinheiro dinheiro) {
        return dinheiro.toBigDecimal();
    }

    @Override
    public Dinheiro convertToEntityAttribute(BigDecimal valor) {
        return Dinheiro.valueOf(valor);
    }
}
