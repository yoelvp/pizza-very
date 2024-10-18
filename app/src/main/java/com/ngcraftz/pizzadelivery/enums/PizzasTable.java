package com.ngcraftz.pizzadelivery.enums;

public enum PizzasTable {
    _TABLE_NAME("pizzas"),
    ID("id"),
    NAME("name"),
    PRICE("price"),
    DESCRIPTION("description");

    private final String key;

    PizzasTable(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
