package ru.rutmiit.models.enums;

public enum GrowthRate {
    SLOW(1), // медленный
    MODERATE(2), // средний
    FAST(3); // быстрый

    private final int value;

    GrowthRate(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getDisplayName() {
        switch (this) {
            case SLOW: return "Медленный";
            case MODERATE: return "Средний";
            case FAST: return "Быстрый";
            default: return this.name();
        }
    }
}
