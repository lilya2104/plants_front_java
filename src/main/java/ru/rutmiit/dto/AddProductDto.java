package ru.rutmiit.dto;

import jakarta.validation.constraints.*;
import ru.rutmiit.utils.validation.UniqueProductName;

public class AddProductDto {
    @UniqueProductName
    private String name;
    private Double price;
    private String description;
    private ProductPropertiesDto properties;

    @NotEmpty(message = "Название не должно быть пустым!")
    @Size(min = 3, message = "Название должно содержать не менее 3 символов!")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "Цена не должна быть пустой!")
    @DecimalMin(value = "1.00", message = "Цена может быть меньше нуля!")
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    @NotEmpty(message = "Описание не должно быть пустым!")
    @Size(min = 20, message = "Описание должно содержать минимум 20 символов!")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = "Заполните характеристики растения!")
    public ProductPropertiesDto getProperties() { return properties; }
    public void setProperties(ProductPropertiesDto properties) { this.properties = properties; }
}
