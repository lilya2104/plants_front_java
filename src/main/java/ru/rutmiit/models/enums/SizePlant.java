package ru.rutmiit.models.enums;

public enum SizePlant {
    TINY(1), // для окна, очень маленький
    SMALL(2), // для окна, средний
    MEDIUM(3), // для окна, большой
    FLOOR(4); // напольный

    private final int value;

    SizePlant (int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getDisplayName() {
        switch (this) {
            case TINY: return "Очень маленький (для окна)";
            case SMALL: return "Маленький (для окна)";
            case MEDIUM: return "Средний (для окна)";
            case FLOOR: return "Напольный";
            default: return this.name();
        }
    }
}
