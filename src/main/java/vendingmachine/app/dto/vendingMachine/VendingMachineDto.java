package vendingmachine.app.dto.vendingMachine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class VendingMachineDto {

    private List<String> productIds;

    private List<BigDecimal> paymentItems;

    private List<BigDecimal> insertedMoney;
}
