package ru.rutmiit.models.entities;

import jakarta.persistence.*;
import ru.rutmiit.models.enums.*;

@Entity
@Table(name = "properties_article")
public class Properties extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CareLevel careLevel;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LightRequirement lightRequirement;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WateringFrequency wateringFrequency;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GrowthRate growthRate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SizePlant sizePlant;

    @Column(columnDefinition = "BOOL", nullable = false)
    private boolean petSafe;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_product")
    private Product product;


    public Properties() {
    }

    public CareLevel getCareLevel() {
        return careLevel;
    }

    public void setCareLevel(CareLevel careLevel) {
        this.careLevel = careLevel;
    }

    public LightRequirement getLightRequirement() {
        return lightRequirement;
    }

    public void setLightRequirement(LightRequirement lightRequirement) {
        this.lightRequirement = lightRequirement;
    }

    public WateringFrequency getWateringFrequency() {
        return wateringFrequency;
    }

    public void setWateringFrequency(WateringFrequency wateringFrequency) {
        this.wateringFrequency = wateringFrequency;
    }

    public GrowthRate getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(GrowthRate growthRate) {
        this.growthRate = growthRate;
    }

    public SizePlant getSizePlant() {
        return sizePlant;
    }

    public void setSizePlant(SizePlant sizePlant) {
        this.sizePlant = sizePlant;
    }

    public boolean isPetSafe() {
        return petSafe;
    }

    public void setPetSafe(boolean petSafe) {
        this.petSafe = petSafe;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
