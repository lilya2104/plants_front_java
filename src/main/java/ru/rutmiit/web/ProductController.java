package ru.rutmiit.web;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rutmiit.dto.AddProductDto;
import ru.rutmiit.dto.ShowDetailedProductInfoDto;
import ru.rutmiit.dto.ShowProductInfoDto;
import ru.rutmiit.models.enums.*;
import ru.rutmiit.services.OrderService;
import ru.rutmiit.services.ProductService;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    private final ProductService productService;
    private final OrderService orderService;

    public ProductController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
        log.info("ProductController инициализирован");
    }

    @PostMapping("/add")
    public ResponseEntity<ShowProductInfoDto> addProduct(@Valid @RequestBody AddProductDto productDto) {
        log.info("Создание товара: {}", productDto.getName());
        ShowProductInfoDto addProduct = productService.addProduct(productDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(addProduct);
    }

    @GetMapping("")
    public ResponseEntity<List<ShowProductInfoDto>> allProducts(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) CareLevel careLevel,
            @RequestParam(required = false) LightRequirement lightRequirement,
            @RequestParam(required = false) WateringFrequency wateringFrequency,
            @RequestParam(required = false) GrowthRate growthRate,
            @RequestParam(required = false) SizePlant sizePlant,
            @RequestParam(required = false) Boolean petSafe) {

        log.info("Отображение списка растений: поиск={}", searchTerm);
        List<ShowProductInfoDto> products;

        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            products = productService.searchProduct(searchTerm);
        }
        else if (careLevel != null || lightRequirement != null || wateringFrequency != null ||
                growthRate != null || sizePlant != null || petSafe != null) {
            products = productService.findProductsByProperties(
                    careLevel, lightRequirement, wateringFrequency, growthRate, sizePlant, petSafe);
        }
        else {
            products = productService.allProducts();
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/noarticles")
    public ResponseEntity<List<ShowProductInfoDto>> getProductsWithoutArticles() {
        log.info("Получение товаров без статей");
        List<ShowProductInfoDto> products = productService.findProductsWithoutArticles();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productName}")
    public ResponseEntity<ShowDetailedProductInfoDto> getProductDetails(@PathVariable String productName) {

        log.info("Получение деталей {}", productName);
        ShowDetailedProductInfoDto product = productService.productDetails(productName);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/order/{productName}")
    public ResponseEntity<Void> buyProduct(@PathVariable String productName,
                             @RequestParam(defaultValue = "1") int count,
                             Principal principal) {
        log.info("Оформление заказа: product={}, count={}, user={}", productName, count, principal.getName());
        orderService.createOrder(principal.getName(), productName, count);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete/{productName}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productName) {
        log.info("Удаление растения: {}", productName);
        productService.removeProduct(productName);

        return ResponseEntity.noContent().build();
    }
}
