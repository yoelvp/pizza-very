package com.ngcraftz.pizzadelivery.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "pizzadelivery.db";
    private static final int DB_VERSION = 1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("SQLite", "Creating tables in database");
        String usersTable = "CREATE TABLE users(id INTEGER PRIMARY KEY AUTOINCREMENT, first_name VARCHAR(50), last_name VARCHAR(50), email VARCHAR(100), password VARCHAR(255))";
        String pizzasTable = "CREATE TABLE pizzas(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(50), price DECIMAL(6, 2), description TEXT)";
        db.execSQL(usersTable);
        db.execSQL(pizzasTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS pizzas");
        onCreate(db);
    }
}
