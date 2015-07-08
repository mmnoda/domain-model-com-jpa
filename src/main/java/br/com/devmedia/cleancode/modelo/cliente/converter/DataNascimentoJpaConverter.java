package br.com.devmedia.cleancode.modelo.cliente.converter;

import br.com.devmedia.cleancode.modelo.cliente.DataNascimento;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Date;

/**
 *
 */
@Converter(autoApply = true)
public class DataNascimentoJpaConverter implements AttributeConverter<DataNascimento, Date> {

    @Override
    public Date convertToDatabaseColumn(DataNascimento dataNascimento) {
        return Date.from(dataNascimento.toInstant());
    }

    @Override
    public DataNascimento convertToEntityAttribute(Date data) {
        return DataNascimento.valueOf(data.toInstant());
    }
}
