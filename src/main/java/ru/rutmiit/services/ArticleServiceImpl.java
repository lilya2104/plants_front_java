package ru.rutmiit.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rutmiit.dto.*;
import ru.rutmiit.models.entities.Article;
import ru.rutmiit.models.entities.Product;
import ru.rutmiit.models.entities.TextArticle;
import ru.rutmiit.models.enums.PlantFamily;
import ru.rutmiit.models.exceptions.ArticleNotFoundException;
import ru.rutmiit.repositories.ArticleRepository;
import ru.rutmiit.repositories.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)  // все методы по умолчанию только читают данные
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final ProductRepository productRepository;
    private final ModelMapper mapper; // ModelMapper — автоматически маппит Entity → DTO и DTO → Entity

    public ArticleServiceImpl(ArticleRepository articleRepository, ProductRepository productRepository, ModelMapper mapper) {
        this.articleRepository = articleRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
        log.info("ArticleServiceImpl инициализирован");
    }

    @Override
    @Transactional // если все действия метода успешны - добавляет, если даже что-то одно не выполнилось - не добавляет
    // Транзакция — это группа операций с БД, которые выполняются как единое целое.
    @CacheEvict(cacheNames = "articles", allEntries = true) // очищение кэша (после успешного выполнения метода)
    public ShowArticleInfoDto addArticle(AddArticleDto articleDto) {
        log.debug("Добавление новой статьи: {}", articleDto.getTitle());

        Article article = mapper.map(articleDto, Article.class);

        article.setProduct(productRepository.findByName(articleDto.getProductName()).orElseThrow());

        TextArticle textArticle = mapper.map(articleDto.getTextArticle(), TextArticle.class);
        textArticle.setArticle(article);
        article.setTextArticle(textArticle);

        Article addArticle = articleRepository.save(article);
        log.info("Статья успешно добавлена: {}", article.getTitle());
        return mapper.map(addArticle, ShowArticleInfoDto.class);
    }

    @Override
    @Cacheable(value = "articles", key = "'all'")
    public List<ShowArticleInfoDto> allArticles() {
        log.debug("Получение списка всех статей");
        List<ShowArticleInfoDto> articles = articleRepository.findAll().stream()
                .map(article -> mapper.map(article, ShowArticleInfoDto.class))
                .collect(Collectors.toList());
        log.info("Найдено статей: {}", articles.size());
        return articles;
    }

    @Override
    public Page<ShowArticleInfoDto> allArticlesPaginated(Pageable pageable) {
        log.debug("Получение растений с пагинацией: страница {}, размер {}",
                pageable.getPageNumber(), pageable.getPageSize());
        return articleRepository.findAll(pageable)
                .map(article -> mapper.map(article, ShowArticleInfoDto.class));
    }

    @Override
    public List<ShowArticleInfoDto> searchArticles(String searchTerm) {
        log.debug("Поиск статей по запросу: {}", searchTerm);
        List<ShowArticleInfoDto> results = articleRepository.searchByTitleOrDescription(searchTerm).stream()
                .map(article -> mapper.map(article, ShowArticleInfoDto.class))
                .collect(Collectors.toList());
        log.info("По запросу '{}' найдено статей: {}", searchTerm, results.size());
        return results;
    }

    @Override
    @Cacheable(value = "articles", key = "'filtered_' + #plantFamily")
    public List<ShowArticleInfoDto> findByPlantFamily(PlantFamily plantFamily) {
        log.debug("Поиск растений по семейству: {}", plantFamily);
        return articleRepository.findByPlantFamily(plantFamily).stream()
                .map(article -> mapper.map(article, ShowArticleInfoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "article", key = "#articleTitle")
    public ShowDetailedArticleInfoDto articleDetails(String articleTitle) {
        log.debug("Получение деталей статьи: {}", articleTitle);
        Article article = articleRepository.findByTitleWithProduct(articleTitle)
                .orElseThrow(() -> {
                    log.warn("Статья не найдена: {}", articleTitle);
                    return new ArticleNotFoundException("Статьи с заголовком '"
                            + articleTitle + "' не найдено");
                });
        // подробная статья
        ShowDetailedArticleInfoDto detailsArticle = mapper.map(article, ShowDetailedArticleInfoDto.class);
        // краткая информация о связанном товаре
        ShowProductInfoDto infoProduct = mapper.map(article.getProduct(), ShowProductInfoDto.class);
        detailsArticle.setProductInfoDto(infoProduct);
        return detailsArticle;
    }

    @Override
    @Transactional // если все действия метода успешны - удаляет, если даже что-то одно не выполнилось - не удаляет
    // Транзакция — это группа операций с БД, которые выполняются как единое целое.
    @CacheEvict(cacheNames = {"articles", "article", "products"}, allEntries = true) // очищение кэша (после успешного выполнения метода)
    public void removeArticle(String articleTitle) {
        log.debug("Удаление статьи: {}", articleTitle);

        if (!articleRepository.existsByTitle(articleTitle)) {
            log.warn("Попытка удалить несуществующую статью: {}", articleTitle);
            throw new ArticleNotFoundException("Статьи с заголовком '" + articleTitle + "' не найдено");
        }
        articleRepository.deleteByTitle(articleTitle);
        log.info("Статья успешно удалена: {}", articleTitle);
    }
}
