package vendingmachine.app.command.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateProductCommand {

    private String name;

    private String description;

    private double price;
}
