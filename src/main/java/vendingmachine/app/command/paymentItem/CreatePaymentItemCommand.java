package vendingmachine.app.command.paymentItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreatePaymentItemCommand {

    private double value;
}
