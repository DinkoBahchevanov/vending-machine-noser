package vendingmachine.app.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vendingmachine.app.command.product.CreateProductCommand;
import vendingmachine.app.command.product.UpdateProductCommand;
import vendingmachine.app.dto.product.ProductDto;
import vendingmachine.app.services.ProductService;

@RestController
@RequestMapping(path = "/api/product/")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductDto createProduct(@RequestBody CreateProductCommand command) {
        return this.productService.createProduct(command);
    }

    @DeleteMapping("{productId}")
    public void deleteProduct(@PathVariable String productId) {
        this.productService.deleteProduct(productId);
    }

    @PutMapping("{productId}")
    public ProductDto updateProduct(@PathVariable String productId, @RequestBody UpdateProductCommand command) {
        return productService.updateProduct(productId, command);
    }
}
