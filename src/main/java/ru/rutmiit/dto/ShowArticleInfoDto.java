package ru.rutmiit.dto;

import ru.rutmiit.models.enums.PlantFamily;
import java.io.Serializable;

public class ShowArticleInfoDto implements Serializable {
    private String title;
    private Integer readTime;
    private String description;
    private PlantFamily plantFamily;

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
}
