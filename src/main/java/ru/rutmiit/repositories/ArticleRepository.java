package ru.rutmiit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.rutmiit.models.entities.Article;
import ru.rutmiit.models.enums.*;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, String> {

    Optional<Article> findByTitle(String title);

    boolean existsByTitle(String title);

    @Query("SELECT a FROM Article a LEFT JOIN FETCH a.product WHERE a.title = :title")
    // позволяет не использовать отдельный запрос для получения связной статьи
    Optional<Article> findByTitleWithProduct(@Param("title") String title);

    @Query("SELECT a FROM Article a WHERE " +
            "LOWER(a.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(a.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
        // полнотекстовый поиск по названию или описанию
    List<Article> searchByTitleOrDescription(@Param("searchTerm") String searchTerm);

    List<Article> findByPlantFamily(PlantFamily plantFamily);

    @Modifying
    @Transactional
    void deleteByTitle(String title);
}
