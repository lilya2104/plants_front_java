package ru.rutmiit.dto;
import jakarta.validation.constraints.*;
public class TextArticleDto {
    private String care;
    private String replication;
    private String illness;
    private String pests;

    @NotEmpty(message = "Текст об уходе не должен быть пустым!")
    @Size(min = 200, message = "Текст об уходе должен содержать минимум 200 символов!")
    public String getCare() {
        return care;
    }
    public void setCare(String care) {
        this.care = care;
    }

    @NotEmpty(message = "Текст о размножении не должен быть пустым!")
    @Size(min = 200, message = "Текст о размножении должен содержать минимум 200 символов!")
    public String getReplication() {
        return replication;
    }
    public void setReplication(String replication) {
        this.replication = replication;
    }

    @NotEmpty(message = "Текст о болезнях не должен быть пустым!")
    @Size(min = 200, message = "Текст о болезнях должен содержать минимум 200 символов!")
    public String getIllness() {
        return illness;
    }
    public void setIllness(String illness) {
        this.illness = illness;
    }

    @NotEmpty(message = "Текст о вредителях не должен быть пустым!")
    @Size(min = 200, message = "Текст о вредителях должен содержать минимум 200 символов!")
    public String getPests() {
        return pests;
    }
    public void setPests(String pests) {
        this.pests = pests;
    }
}
