package vendingmachine.app.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vendingmachine.app.command.productCategory.CreateProductCategoryCommand;
import vendingmachine.app.dto.productCategory.ProductCategoryDto;
import vendingmachine.app.services.ProductCategoryService;

@RestController
@RequestMapping(path = "/api/product-category/")
@AllArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @PostMapping
    public ProductCategoryDto createProductCategory(@RequestBody CreateProductCategoryCommand command) {
        return this.productCategoryService.createProductCategory(command);
    }

    @GetMapping("{categoryId}")
    public ProductCategoryDto getProductCategory(@PathVariable String categoryId) {
        return this.productCategoryService.getProductCategory(categoryId);
    }

    @DeleteMapping("{categoryId}")
    public void deleteProductCategory(@PathVariable String categoryId) {
        this.productCategoryService.deleteProductCategory(categoryId);
    }
}
