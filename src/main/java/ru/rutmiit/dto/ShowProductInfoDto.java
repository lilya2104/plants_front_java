package ru.rutmiit.dto;

import java.io.Serializable;

public class ShowProductInfoDto implements Serializable {
    private String id;
    private String name;
    private Double price;
    private String description;
    private ProductPropertiesDto properties;
    private ShowArticleInfoDto article;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public ShowArticleInfoDto getArticle() {
        return article;
    }

    public void setArticle(ShowArticleInfoDto article) {
        this.article = article;
    }
}
