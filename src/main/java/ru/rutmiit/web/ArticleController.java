package ru.rutmiit.web;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rutmiit.dto.AddArticleDto;
import ru.rutmiit.dto.ShowArticleInfoDto;
import ru.rutmiit.dto.ShowDetailedArticleInfoDto;
import ru.rutmiit.models.enums.PlantFamily;
import ru.rutmiit.services.ArticleService;
import ru.rutmiit.services.ProductService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/articles")
@CrossOrigin(origins = "http://localhost:5173")
public class ArticleController {
    private final ArticleService articleService;
    private final ProductService productService;

    public ArticleController(ArticleService articleService, ProductService productService) {
        this.articleService = articleService;
        this.productService = productService;
        log.info("ArticleController инициализирован");
    }

    @PostMapping("/add")
    public ResponseEntity<ShowArticleInfoDto> addArticle(@Valid @RequestBody AddArticleDto articleDto) {
        log.info("Обработка добавления статьи: {}", articleDto.getTitle());
        ShowArticleInfoDto addArticle = articleService.addArticle(articleDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(addArticle);
    }

    @GetMapping("")
    public ResponseEntity<List<ShowArticleInfoDto>> showAllArticles(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) PlantFamily plantFamily) {
        log.info("Отображение списка статей: поиск={}, фильтр={}", searchTerm, plantFamily);
        List<ShowArticleInfoDto> articles;

        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            articles = articleService.searchArticles(searchTerm);
        }
        else if (plantFamily != null) {
            articles = articleService.findByPlantFamily(plantFamily);
        }
        else {
            articles = articleService.allArticles();
        }
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/{articleTitle}")
    public ResponseEntity<ShowDetailedArticleInfoDto> showArticleDetails(@PathVariable String articleTitle) {
        log.info("Отображение полной статьи: {}", articleTitle);
        ShowDetailedArticleInfoDto article = articleService.articleDetails(articleTitle);

        return ResponseEntity.ok(article);
    }

    @DeleteMapping("/delete/{articleTitle}")
    public ResponseEntity<Void> deleteArticle(@PathVariable String articleTitle) {
        log.debug("Удаление статьи через контроллер: {}", articleTitle);
        articleService.removeArticle(articleTitle);

        return ResponseEntity.noContent().build();
    }
}
