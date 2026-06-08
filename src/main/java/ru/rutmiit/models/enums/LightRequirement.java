package ru.rutmiit.models.enums;

public enum LightRequirement {
    LOW(1), // тень
    MEDIUM(2), // полутень
    HIGH(3), // яркий свет
    DIRECT(4); // прямое солнце

    private final int value;

    LightRequirement(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getDisplayName() {
        switch (this) {
            case LOW: return "Тень";
            case MEDIUM: return "Полутень";
            case HIGH: return "Яркий свет";
            case DIRECT: return "Прямое солнце";
            default: return this.name();
        }
    }
}
