package ru.rutmiit.dto;

import ru.rutmiit.models.enums.PlantFamily;

public class ShowDetailedArticleInfoDto {
    private String title;
    private Integer readTime;
    private String description;

    private PlantFamily plantFamily;

    private TextArticleDto textArticle;

    private ShowProductInfoDto productInfoDto;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getReadTime() {
        return readTime;
    }

    public void setReadTime(Integer readTime) {
        this.readTime = readTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PlantFamily getPlantFamily() {
        return plantFamily;
    }

    public void setPlantFamily(PlantFamily plantFamily) {
        this.plantFamily = plantFamily;
    }

    public TextArticleDto getTextArticle() {
        return textArticle;
    }

    public void setTextArticle(TextArticleDto textArticle) {
        this.textArticle = textArticle;
    }

    public ShowProductInfoDto getProductInfoDto() {
        return productInfoDto;
    }

    public void setProductInfoDto(ShowProductInfoDto productInfoDto) {
        this.productInfoDto = productInfoDto;
    }
}
