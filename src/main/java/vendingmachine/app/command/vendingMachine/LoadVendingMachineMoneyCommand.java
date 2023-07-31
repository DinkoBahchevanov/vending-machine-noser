package vendingmachine.app.command.vendingMachine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoadVendingMachineMoneyCommand {

    private int tenStQuantity;

    private int twentyStQuantity;

    private int fiftyStQuantity;

    private int levQuantity;

    private int twoLevQuantity;
}
