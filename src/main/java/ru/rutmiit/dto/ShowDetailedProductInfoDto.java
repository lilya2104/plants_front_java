package ru.rutmiit.dto;

public class ShowDetailedProductInfoDto {
    private String name;
    private Double price;
    private String description;

    private ProductPropertiesDto properties;

    private ShowArticleInfoDto articleInfoDto;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductPropertiesDto getProperties() {
        return properties;
    }

    public void setProperties(ProductPropertiesDto properties) {
        this.properties = properties;
    }

    public ShowArticleInfoDto getArticleInfoDto() {
        return articleInfoDto;
    }

    public void setArticleInfoDto(ShowArticleInfoDto articleInfoDto) {
        this.articleInfoDto = articleInfoDto;
    }
}
