package ru.rutmiit.repositories;

import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.rutmiit.models.entities.Article;
import ru.rutmiit.models.entities.Product;
import ru.rutmiit.models.enums.*;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    Optional<Product> findByName(String name);

    boolean existsByName(String name);

    @Query("SELECT p FROM Product p WHERE p.article IS NULL")
    // для отображения при создании новой статьи только тех растений, которые еще не имеют статьи
    List<Product> findAllWithoutArticles();

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.article WHERE p.name = :name")
    // позволяет не использовать отдельный запрос к бд при обращении к getArticle()
    Optional<Product> findByNameWithArticle(@Param("name") String name);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    // для полнотекстового поиска
    List<Product> searchByName(@Param("searchTerm") String searchTerm);

    @Query("SELECT p FROM Product p JOIN FETCH p.properties prop " +
            "WHERE (:careLevel IS NULL OR prop.careLevel = :careLevel) AND " +
            "(:lightRequirement IS NULL OR prop.lightRequirement = :lightRequirement) AND " +
            "(:wateringFrequency IS NULL OR prop.wateringFrequency = :wateringFrequency) AND " +
            "(:growthRate IS NULL OR prop.growthRate = :growthRate) AND " +
            "(:sizePlant IS NULL OR prop.sizePlant = :sizePlant) AND " +
            "(:petSafe IS NULL OR prop.petSafe = :petSafe)")
    // поиск для фильтрации по характеристикам
    List<Product> findByProperties(
            @Param("careLevel") CareLevel careLevel,
            @Param("lightRequirement") LightRequirement lightRequirement,
            @Param("wateringFrequency") WateringFrequency wateringFrequency,
            @Param("growthRate") GrowthRate growthRate,
            @Param("sizePlant") SizePlant sizePlant,
            @Param("petSafe") Boolean petSafe);

    @Modifying
    @Transactional
    void deleteByName(String name);
}
