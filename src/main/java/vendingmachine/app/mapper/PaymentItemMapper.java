package vendingmachine.app.mapper;

import org.mapstruct.Mapper;
import vendingmachine.app.dto.paymentItem.PaymentItemDto;
import vendingmachine.app.model.paymentItem.PaymentItemEntity;

@Mapper(componentModel = "spring")
public interface PaymentItemMapper {

    PaymentItemDto entityToDto(PaymentItemEntity paymentItemEntity);
}
