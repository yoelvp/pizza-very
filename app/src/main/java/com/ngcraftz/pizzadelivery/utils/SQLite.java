package com.ngcraftz.pizzadelivery.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLite extends SQLiteOpenHelper {
    private static final String DB_NAME = "pizzadelivery.db";
    private static final int DB_VERSION = 1;

    public SQLite(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE users(id INTEGER PRIMARY KEY AUTOINCREMENT, first_name VARCHAR(50), last_name VARCHAR(50), email VARCHAR(100), password VARCHAR(255))";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }
}
