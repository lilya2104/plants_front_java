package ru.rutmiit.models.enums;

public enum PlantFamily {

    DECORATIVE_FLOWERING(1),           // Декоративно-цветущие растения
    DECORATIVE_DECIDUOUS(2),      // Декоративно-лиственные
    CACTI(3),     // Кактусы
    SUCCULENT(4),      // Cуккуленты
    PALM(5),       // Пальмы
    FERN(6),          // Папоротники
    ORCHID(7),       // Орхидеи
    BROMELIADS(8),         // Бромелиевые
    CONIFEROUS_TREES(9),        // Хвойные
    FRUITING(10),              // Плодоносящие
    BULBOUS(11);               // Луковичные


    private final int value;

    PlantFamily (int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getDisplayName() {
        switch (this) {
            case DECORATIVE_FLOWERING: return "Декоративно-цветущие";
            case DECORATIVE_DECIDUOUS: return "Декоративно-лиственные";
            case CACTI: return "Кактусы";
            case SUCCULENT: return "Суккуленты";
            case PALM: return "Пальмы";
            case FERN: return "Папоротники";
            case ORCHID: return "Орхидеи";
            case BROMELIADS: return "Бромелиевые";
            case CONIFEROUS_TREES: return "Хвойные";
            case FRUITING: return "Плодоносящие";
            case BULBOUS: return "Луковичные";
            default: return this.name();
        }
    }
}
