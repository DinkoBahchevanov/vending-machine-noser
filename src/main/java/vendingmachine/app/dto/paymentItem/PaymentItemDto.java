package vendingmachine.app.dto.paymentItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vendingmachine.app.model.paymentItem.PaymentItemValue;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentItemDto {

    private String id;

    private PaymentItemValue paymentItemValue;
}
