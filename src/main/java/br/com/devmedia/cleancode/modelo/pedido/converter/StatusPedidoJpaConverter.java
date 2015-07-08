package br.com.devmedia.cleancode.modelo.pedido.converter;

import br.com.devmedia.cleancode.modelo.pedido.StatusPedido;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 */
@Converter(autoApply = true)
public class StatusPedidoJpaConverter implements AttributeConverter<StatusPedido, Character> {

    @Override
    public Character convertToDatabaseColumn(StatusPedido statusPedido) {
        return statusPedido.getPrefixo();
    }

    @Override
    public StatusPedido convertToEntityAttribute(Character prefixo) {
        return StatusPedido.byPrefixo(prefixo);
    }
}
