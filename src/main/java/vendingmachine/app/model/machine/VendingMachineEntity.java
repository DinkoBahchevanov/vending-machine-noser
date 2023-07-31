package vendingmachine.app.model.machine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vendingmachine.app.model.BaseEntity;
import vendingmachine.app.model.paymentItem.PaymentItemEntity;
import vendingmachine.app.model.product.ProductEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "vending_machine")
public class VendingMachineEntity extends BaseEntity {

    @OneToMany(cascade = CascadeType.ALL)
    private List<ProductEntity> productEntities = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<PaymentItemEntity> paymentItemEntities = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<PaymentItemEntity> insertedMoney = new ArrayList<>();

    public BigDecimal getTotalInsertedMoney() {
        if (insertedMoney != null) {
            return insertedMoney.stream()
                    .map(paymentItemEntity -> paymentItemEntity.getValue().getValue())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        return BigDecimal.ZERO;
    }
}
