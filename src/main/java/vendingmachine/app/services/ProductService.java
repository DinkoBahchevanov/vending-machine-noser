package vendingmachine.app.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vendingmachine.app.command.product.CreateProductCommand;
import vendingmachine.app.command.product.UpdateProductCommand;
import vendingmachine.app.dto.product.ProductDto;
import vendingmachine.app.mapper.ProductMapper;
import vendingmachine.app.model.product.ProductCategoryEntity;
import vendingmachine.app.model.product.ProductEntity;
import vendingmachine.app.repository.ProductCategoryRepository;
import vendingmachine.app.repository.ProductRepository;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final ProductCategoryRepository productCategoryRepository;

    public ProductDto createProduct(CreateProductCommand command) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(command.getName());
        productEntity.setDescription(command.getDescription());
        productEntity.setPrice(BigDecimal.valueOf(command.getPrice()));
        ProductCategoryEntity productCategoryEntity = productCategoryRepository.findById(command.getCategoryId())
                .orElseThrow(() -> new RuntimeException("No such category"));
        productEntity.setCategory(productCategoryEntity);

        productCategoryEntity.getProductEntities().add(productRepository.save(productEntity));
        productCategoryRepository.save(productCategoryEntity);

        return productMapper.entityToDto(productEntity);
    }

    public void deleteProduct(String productId) {
        productRepository.deleteById(productId);
    }

    public ProductDto updateProduct(String productId, UpdateProductCommand command) {
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("No such category"));

        if (command.getName() != null) {
            productEntity.setName(command.getName());
        }

        if (command.getDescription() != null) {
            productEntity.setDescription(command.getDescription());
        }

        if (command.getName() != null) {
            productEntity.setPrice(BigDecimal.valueOf(command.getPrice()));
        }

        return productMapper.entityToDto(productRepository.save(productEntity));
    }
}
