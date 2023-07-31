package vendingmachine.app.mapper;

import org.mapstruct.Mapper;
import vendingmachine.app.dto.productCategory.ProductCategoryDto;
import vendingmachine.app.model.product.ProductCategoryEntity;

@Mapper(componentModel = "spring")
public interface ProductCategoryMapper {

    ProductCategoryDto entityToDto(ProductCategoryEntity productCategoryEntity);
}
