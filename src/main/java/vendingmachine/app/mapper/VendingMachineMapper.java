package vendingmachine.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vendingmachine.app.dto.vendingMachine.VendingMachineDto;
import vendingmachine.app.model.machine.VendingMachineEntity;
import vendingmachine.app.model.paymentItem.PaymentItemEntity;
import vendingmachine.app.model.paymentItem.PaymentItemValue;
import vendingmachine.app.model.product.ProductEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface VendingMachineMapper {

    @Mapping(target = "productIds", expression = "java(mapProductEntities(entity.getProductEntities()))")
    @Mapping(target = "paymentItems", expression = "java(mapPaymentItemEntities(entity.getPaymentItemEntities()))")
    @Mapping(target = "insertedMoney", expression = "java(mapPaymentItemEntities(entity.getInsertedMoney()))")
    VendingMachineDto entityToDto(VendingMachineEntity entity);

    default List<String> mapProductEntities(List<ProductEntity> productEntities) {
        return productEntities.stream()
                .map(ProductEntity::getId)
                .collect(Collectors.toList());
    }

    default List<BigDecimal> mapPaymentItemEntities(List<PaymentItemEntity> paymentItemEntities) {
        return paymentItemEntities.stream()
                .map(PaymentItemEntity::getValue)
                .map(PaymentItemValue::getValue)
                .collect(Collectors.toList());
    }
}
