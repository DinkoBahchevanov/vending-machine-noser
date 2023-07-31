package vendingmachine.app.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vendingmachine.app.command.productCategory.CreateProductCategoryCommand;
import vendingmachine.app.dto.productCategory.ProductCategoryDto;
import vendingmachine.app.mapper.ProductCategoryMapper;
import vendingmachine.app.model.product.ProductCategoryEntity;
import vendingmachine.app.repository.ProductCategoryRepository;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class ProductCategoryService {

    private final ProductCategoryMapper productCategoryMapper;

    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryDto createProductCategory(CreateProductCategoryCommand command) {
        ProductCategoryEntity productCategoryEntity = new ProductCategoryEntity(command.getType(), new ArrayList<>());

        return productCategoryMapper.entityToDto(productCategoryRepository.save(productCategoryEntity));
    }

    public ProductCategoryDto getProductCategory(String categoryId) {
        ProductCategoryEntity productCategoryEntity = productCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("No such category!"));

        return productCategoryMapper.entityToDto(productCategoryEntity);
    }

    public void deleteProductCategory(String categoryId) {
        productCategoryRepository.deleteById(categoryId);
    }
}
