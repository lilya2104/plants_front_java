package ru.rutmiit.models.entities;

import jakarta.persistence.*;
import ru.rutmiit.models.enums.PlantFamily;

@Entity
@Table(name = "articles")
public class Article extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "INT", nullable = false)
    private int readTime;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PlantFamily plantFamily;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_product")
    private Product product;

    @OneToOne(mappedBy = "article", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private TextArticle textArticle;

    public Article() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReadTime() {
        return readTime;
    }

    public void setReadTime(int readTime) {
        this.readTime = readTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public TextArticle getTextArticle() {
        return textArticle;
    }

    public void setTextArticle(TextArticle textArticle) {
        this.textArticle = textArticle;
    }

    public PlantFamily getPlantFamily() {
        return plantFamily;
    }

    public void setPlantFamily(PlantFamily plantFamily) {
        this.plantFamily = plantFamily;
    }
}
