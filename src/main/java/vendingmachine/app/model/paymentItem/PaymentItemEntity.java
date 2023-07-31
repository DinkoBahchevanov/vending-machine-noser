package vendingmachine.app.model.paymentItem;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vendingmachine.app.model.BaseEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "payment_items")
public class PaymentItemEntity extends BaseEntity {

    @Enumerated
    private PaymentItemValue value;
}
