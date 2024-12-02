package com.e_commerce.epic_loot.enumEntity;

public enum RamMemory {
    RAM_4("4"),
    RAM_8("8"),
    RAM_16("16"),
    RAM_32("32"),
    RAM_64("64");

    private final String value;

    // Конструктор
    RamMemory(String value) {
        this.value = value;
    }

    // Метод для получения значения
    public String getValue() {
        return value;
    }

    // Метод для поиска по значению
    public static RamMemory fromValue(String value) {
        for (RamMemory ram : RamMemory.values()) {
            if (ram.value.equals(value)) {
                return ram;
            }
        }
        throw new IllegalArgumentException("Unknown RAM value: " + value);
    }
}
