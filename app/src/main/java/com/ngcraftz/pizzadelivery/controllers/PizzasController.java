package com.ngcraftz.pizzadelivery.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.ngcraftz.pizzadelivery.enums.PizzasTable;
import com.ngcraftz.pizzadelivery.models.Pizza;
import com.ngcraftz.pizzadelivery.utils.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class PizzasController {
    private final SQLiteHelper dbHelper;
    private SQLiteDatabase db;

    public PizzasController(Context context) {
        this.dbHelper = new SQLiteHelper(context);
    }

    public Pizza getById(long id) {
        Pizza pizza = null;
        Cursor cursor = null;

        try {
            db = dbHelper.getReadableDatabase();
            String[] args = { String.valueOf(id) };
            cursor = db.rawQuery("SELECT * FROM pizzas WHERE id = ?", args);

            if (cursor != null && cursor.moveToFirst()) {
                int pizzaId = cursor.getInt(cursor.getColumnIndexOrThrow(PizzasTable.ID.getKey()));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(PizzasTable.NAME.getKey()));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(PizzasTable.DESCRIPTION.getKey()));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(PizzasTable.PRICE.getKey()));

                pizza = new Pizza(pizzaId, name, description, price);
            }
        } catch (SQLException exception) {
            System.out.println(exception);
        } finally {
            if (cursor != null) {
                cursor.close();
            }

            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return pizza;
    }

    public Pizza create(Pizza pizza) {
        db = dbHelper.getWritableDatabase();
        System.out.println("----------------- Controller name " + pizza.getName());

        ContentValues values = new ContentValues();
        values.put(PizzasTable.NAME.getKey(), pizza.getName());
        values.put(PizzasTable.DESCRIPTION.getKey(), pizza.getDescription());
        values.put(PizzasTable.PRICE.getKey(), pizza.getPrice());

        long pizzaId = db.insert("pizzas", null, values);
        db.close();

        return this.getById(pizzaId);
    }

    public List<Pizza> getAll() {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM pizzas", null);
        List<Pizza> pizzas = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));

                pizzas.add(new Pizza(id, name, description, price));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return pizzas;
    }
}
