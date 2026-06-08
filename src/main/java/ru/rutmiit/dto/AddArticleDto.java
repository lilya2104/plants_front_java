package ru.rutmiit.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import ru.rutmiit.models.enums.PlantFamily;

public class AddArticleDto {
    private String title;
    private Integer readTime;
    private String description;

    private PlantFamily plantFamily;

    private TextArticleDto textArticle;

    private String productName;


    @NotEmpty(message = "Заголовок статьи не должен быть пустым!")
    @Size(min = 5, message = "Заголовок статьи должен быть не менее 5 символов!")
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @NotNull(message = "Время чтения не должно быть пустым!")
    @Min(value = 1, message = "Время чтения должно быть не менее 1 минуты!")
    @Max(value = 120, message = "Время чтения должно быть не более 120 минут!")
    public Integer getReadTime() {
        return readTime;
    }
    public void setReadTime(Integer readTime) {
        this.readTime = readTime;
    }

    @NotEmpty(message = "Описание не должно быть пустым!")
    @Size(min = 10, message = "Описание должно содержать минимум 10 символов!")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = "Выберите семейство растения!")
    public PlantFamily getPlantFamily() {
        return plantFamily;
    }
    public void setPlantFamily(PlantFamily plantFamily) {
        this.plantFamily = plantFamily;
    }

    @NotNull(message = "Заполните текст статьи!")
    @Valid
    public TextArticleDto getTextArticle() { return textArticle; }
    public void setTextArticle(TextArticleDto textArticle) { this.textArticle = textArticle; }

    @NotNull(message = "Выберите продукт для привязки!")
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
}
