package ru.rutmiit.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.rutmiit.dto.AddArticleDto;
import ru.rutmiit.dto.ShowArticleInfoDto;
import ru.rutmiit.dto.ShowDetailedArticleInfoDto;
import ru.rutmiit.models.enums.PlantFamily;

import java.util.List;

public interface ArticleService {

    ShowArticleInfoDto addArticle(AddArticleDto articleDto);

    List<ShowArticleInfoDto> allArticles();

    Page<ShowArticleInfoDto> allArticlesPaginated(Pageable pageable);

    List<ShowArticleInfoDto> searchArticles(String searchTerm);

    List<ShowArticleInfoDto> findByPlantFamily(PlantFamily plantFamily);

    ShowDetailedArticleInfoDto articleDetails(String articleTitle);

    void removeArticle(String articleTitle);


}
