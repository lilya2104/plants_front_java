package ru.rutmiit.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rutmiit.dto.AddProductDto;
import ru.rutmiit.dto.ShowArticleInfoDto;
import ru.rutmiit.dto.ShowDetailedProductInfoDto;
import ru.rutmiit.dto.ShowProductInfoDto;
import ru.rutmiit.models.entities.Product;
import ru.rutmiit.models.entities.Properties;
import ru.rutmiit.models.enums.*;
import ru.rutmiit.models.exceptions.ProductNotFoundException;
import ru.rutmiit.repositories.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true) // все методы по умолчанию только читают данные
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper mapper; // ModelMapper — автоматически маппит Entity → DTO и DTO → Entity

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
        log.info("ProductServiceImpl инициализирован");
    }

    @Override
    @Transactional // если все действия метода успешны - добавляет, если даже что-то одно не выполнилось - не добавляет
    // Транзакция — это группа операций с БД, которые выполняются как единое целое.
    @CacheEvict(cacheNames = "products", allEntries = true) // очищение кэша (после успешного выполнения метода)
    public ShowProductInfoDto addProduct(AddProductDto productDto) {
        log.debug("Добавление нового растения: {}", productDto.getName());
        Product product = mapper.map(productDto, Product.class);

        Properties properties = mapper.map(productDto.getProperties(), Properties.class);
        properties.setProduct(product);
        product.setProperties(properties);

        Product addProduct = productRepository.save(product);
        log.info("Растение успешно добавлено: {}", product.getName());
        return mapper.map(addProduct, ShowProductInfoDto.class);
    }

    @Override
    @Cacheable(value = "products", key = "'all'")
    public List<ShowProductInfoDto> allProducts() {
        log.debug("Получение списка всех растений");
        List<ShowProductInfoDto> products = productRepository.findAll().stream()
                .map(product -> mapper.map(product, ShowProductInfoDto.class))
                .collect(Collectors.toList());
        log.info("Найдено растений: {}", products.size());
        return products;
    }

    @Override
    public Page<ShowProductInfoDto> allProductsPaginated(Pageable pageable) {
        log.debug("Получение растений с пагинацией: страница {}, размер {}",
                pageable.getPageNumber(), pageable.getPageSize());
        return productRepository.findAll(pageable)
                .map(product -> mapper.map(product, ShowProductInfoDto.class));
    }

    @Override
    public List<ShowProductInfoDto> searchProduct(String searchTerm) {
        log.debug("Поиск растений по запросу: {}", searchTerm);
        List<ShowProductInfoDto> results = productRepository.searchByName(searchTerm)
                .stream()
                .map(product -> mapper.map(product, ShowProductInfoDto.class))
                .collect(Collectors.toList());
        log.info("По запросу '{}' найдено растений: {}", searchTerm, results.size());
        return results;
    }

    @Override
    public List<ShowProductInfoDto> findProductsWithoutArticles() {
        log.debug("Получение растений без статьей");
        List<ShowProductInfoDto> results = productRepository.findAllWithoutArticles()
                .stream()
                .map(product -> mapper.map(product, ShowProductInfoDto.class))
                .collect(Collectors.toList());
        log.info("Найдено {} растений без статей", results.size());
        return results;
    }

    @Override
    @Cacheable(value = "products", key = "'filtered_' + #careLevel + '_' + #lightRequirement + '_' " +
            "+ #wateringFrequency + '_' + #growthRate + '_' + #petSafe")
    public List<ShowProductInfoDto> findProductsByProperties(CareLevel careLevel, LightRequirement lightRequirement,
                                                             WateringFrequency wateringFrequency,
                                                             GrowthRate growthRate,
                                                             SizePlant sizePlant,
                                                             Boolean petSafe) {
        log.debug("Поиск растений по свойствам: careLevel={}, lightRequirement={}, " +
                "wateringFrequency={}, growthRate={}, sizePlant={}, petSafe={}",
                careLevel, lightRequirement, wateringFrequency, growthRate, sizePlant, petSafe);
        List<Product> products = productRepository.findByProperties(
                careLevel, lightRequirement, wateringFrequency, growthRate, sizePlant, petSafe);
        List<ShowProductInfoDto> results = products.stream()
                .map(product -> mapper.map(product, ShowProductInfoDto.class))
                .collect(Collectors.toList());
        log.info("По фильтрам найдено растений: {}", results.size());
        return results;
    }

    @Override
    @Cacheable(value = "product", key = "#productName")
    public ShowDetailedProductInfoDto productDetails(String productName) {
        log.debug("Получение деталей растения: {}", productName);
        Product product = productRepository.findByNameWithArticle(productName)
                .orElseThrow(() -> {
                    log.warn("Растение не найдено: {}", productName);
                    return new ProductNotFoundException("Растение '" + productName + "' не найдено");
                });
        ShowDetailedProductInfoDto detailsProduct = mapper.map(product, ShowDetailedProductInfoDto.class);
        if (product.getArticle() != null) {
            ShowArticleInfoDto infoArticle = mapper.map(product.getArticle(), ShowArticleInfoDto.class);
            detailsProduct.setArticleInfoDto(infoArticle);
        }
        return detailsProduct;
    }

    @Override
    @Transactional // если все действия метода успешны - удаляет, если даже что-то одно не выполнилось - не удаляет
    // Транзакция — это группа операций с БД, которые выполняются как единое целое.
    @CacheEvict(cacheNames = {"products", "product", "articles"}, allEntries = true) // очищение кэша (после успешного выполнения метода)
    public void removeProduct(String productName) {
        log.debug("Удаление растение: {}", productName);
        if (!productRepository.existsByName(productName)) {
            log.warn("Попытка удалить несуществующее рестение: {}", productName);
            throw new ProductNotFoundException("Растение '" + productName + "' не найдено");
        }
        productRepository.deleteByName(productName);
        log.info("Растение успешно удалено: {}", productName);
    }
}
