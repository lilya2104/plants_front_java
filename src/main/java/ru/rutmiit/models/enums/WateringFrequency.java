package ru.rutmiit.models.enums;

public enum WateringFrequency {
    RARE(1), // редкий
    MODERATE(2), // умеренный
    FREQUENT(3); // обильный

    private final int value;

    WateringFrequency(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getDisplayName() {
        switch (this) {
            case RARE: return "Редкий";
            case MODERATE: return "Умеренный";
            case FREQUENT: return "Обильный";
            default: return this.name();
        }
    }
}
