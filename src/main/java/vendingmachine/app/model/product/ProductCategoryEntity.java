package vendingmachine.app.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vendingmachine.app.model.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product_category")
public class ProductCategoryEntity extends BaseEntity {

    private String type;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductEntity> productEntities = new ArrayList<>();
}
