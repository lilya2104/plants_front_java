package ru.rutmiit.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "text_article")
public class TextArticle extends BaseEntity {

    @Column(columnDefinition = "TEXT", nullable = false)
    private String care;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String replication;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String illness;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String pests;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_article")
    private Article article;


    public TextArticle() {
    }

    public String getCare() {
        return care;
    }

    public void setCare(String care) {
        this.care = care;
    }

    public String getReplication() {
        return replication;
    }

    public void setReplication(String replication) {
        this.replication = replication;
    }

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    public String getPests() {
        return pests;
    }

    public void setPests(String pests) {
        this.pests = pests;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
