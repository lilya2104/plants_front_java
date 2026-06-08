package ru.rutmiit.models.enums;

public enum CareLevel {
    SIMPLE(1), // простой
    MEDIUM(2), // средний
    COMPLEX(3); // сложный

    private final int value;

    CareLevel (int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getDisplayName() {
        switch (this) {
            case SIMPLE: return "Простой";
            case MEDIUM: return "Средний";
            case COMPLEX: return "Сложный";
            default: return this.name();
        }
    }
}


