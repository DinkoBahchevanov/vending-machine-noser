package vendingmachine.app.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDto {

    private String id;

    private String name;

    private String description;

    private double price;

    private String categoryId;
}
