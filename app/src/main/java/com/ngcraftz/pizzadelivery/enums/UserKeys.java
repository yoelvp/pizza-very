package com.ngcraftz.pizzadelivery.enums;

public enum UserKeys {
    NAMES("names"),
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    EMAIL("email");

    private final String key;

    UserKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
