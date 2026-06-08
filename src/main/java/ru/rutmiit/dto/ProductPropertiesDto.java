package ru.rutmiit.dto;

import ru.rutmiit.models.enums.*;

public class ProductPropertiesDto {
    private CareLevel careLevel;
    private LightRequirement lightRequirement;
    private WateringFrequency wateringFrequency;
    private GrowthRate growthRate;
    private SizePlant sizePlant;
    private Boolean petSafe;

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

    public Boolean getPetSafe() {
        return petSafe;
    }

    public void setPetSafe(Boolean petSafe) {
        this.petSafe = petSafe;
    }
}
