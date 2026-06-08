package ru.rutmiit.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.rutmiit.dto.AddProductDto;
import ru.rutmiit.dto.ShowDetailedProductInfoDto;
import ru.rutmiit.dto.ShowProductInfoDto;
import ru.rutmiit.models.enums.*;

import java.util.List;

public interface ProductService {
    ShowProductInfoDto addProduct(AddProductDto productDto);

    List<ShowProductInfoDto> allProducts();

    Page<ShowProductInfoDto> allProductsPaginated(Pageable pageable);

    List<ShowProductInfoDto> searchProduct(String searchTerm);

    List<ShowProductInfoDto> findProductsWithoutArticles();

    List<ShowProductInfoDto> findProductsByProperties(CareLevel careLevel,
                                                      LightRequirement lightRequirement,
                                                      WateringFrequency wateringFrequency,
                                                      GrowthRate growthRate,
                                                      SizePlant sizePlant,
                                                      Boolean petSafe);

    ShowDetailedProductInfoDto productDetails(String productName);

    void removeProduct(String productName);
}
