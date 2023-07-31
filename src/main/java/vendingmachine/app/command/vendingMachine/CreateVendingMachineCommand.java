package vendingmachine.app.command.vendingMachine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateVendingMachineCommand {

    private List<String> productIds;

    private List<Double> paymentItems;
}
