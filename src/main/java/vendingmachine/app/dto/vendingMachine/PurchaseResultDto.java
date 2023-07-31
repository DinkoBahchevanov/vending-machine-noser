package vendingmachine.app.dto.vendingMachine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vendingmachine.app.dto.product.ProductDto;
import vendingmachine.app.model.paymentItem.PaymentItemEntity;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PurchaseResultDto {

    private ProductDto productDto;

    private List<PaymentItemEntity> change;
}
