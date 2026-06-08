package ru.rutmiit.models.enums;

public enum UserRoles {
    USER(1), MODERATOR(2), ADMIN(3);

    private int value;

    UserRoles(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
