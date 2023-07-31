package vendingmachine.app.mapper;

import org.mapstruct.Mapper;
import vendingmachine.app.dto.product.ProductDto;
import vendingmachine.app.model.product.ProductEntity;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto entityToDto(ProductEntity productEntity);
}
