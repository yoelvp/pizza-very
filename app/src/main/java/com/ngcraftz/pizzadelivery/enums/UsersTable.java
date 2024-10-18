package com.ngcraftz.pizzadelivery.enums;

public enum UsersTable {
    _TABLE_NAME("users"),
    ID("id"),
    FIRST_NAME("first_name"),
    LAST_NAME("last_name"),
    EMAIL("email"),
    PASSWORD("password");

    private final String key;

    UsersTable(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
