package com.ngcraftz.pizzadelivery.controllers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ngcraftz.pizzadelivery.enums.UsersTable;
import com.ngcraftz.pizzadelivery.models.User;
import com.ngcraftz.pizzadelivery.utils.SQLiteHelper;

public class UserController {
    private final SQLiteHelper dbHelper;
    private SQLiteDatabase db;

    public UserController(Context context) {
        this.dbHelper = new SQLiteHelper(context);
    }

    public User getById(long id) {
        User user = null;
        Cursor cursor = null;

        try {
            db = dbHelper.getReadableDatabase();
            String[] args = { String.valueOf(id) };
            cursor = db.rawQuery("SELECT * FROM users WHERE id = ?", args);

            if (cursor != null && cursor.moveToFirst()) {
                long userId = cursor.getInt(cursor.getColumnIndexOrThrow(UsersTable.ID.getKey()));
                String firstName = cursor.getString(cursor.getColumnIndexOrThrow(UsersTable.FIRST_NAME.getKey()));
                String lastName = cursor.getString(cursor.getColumnIndexOrThrow(UsersTable.LAST_NAME.getKey()));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(UsersTable.EMAIL.getKey()));
                String password = cursor.getString(cursor.getColumnIndexOrThrow(UsersTable.PASSWORD.getKey()));

                user = new User(userId, firstName, lastName, email, password);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }

            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return user;
    }
}
