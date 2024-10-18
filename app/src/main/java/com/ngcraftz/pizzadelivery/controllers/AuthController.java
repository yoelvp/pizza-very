package com.ngcraftz.pizzadelivery.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ngcraftz.pizzadelivery.enums.UsersTable;
import com.ngcraftz.pizzadelivery.models.User;
import com.ngcraftz.pizzadelivery.utils.SQLiteHelper;

public class AuthController {
    private final SQLiteHelper dbHelper;
    private SQLiteDatabase db;
    private final UserController userController;

    public AuthController(Context context) {
        this.dbHelper = new SQLiteHelper(context);
        this.userController = new UserController(context);
    }

    public User login(String email, String password) {
        User userLogged = null;
        Cursor cursor = null;
        String[] selectionArgs = { email, password};

        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM users WHERE email = ? AND password = ?", selectionArgs);

            if (cursor.moveToFirst()) {
                String firstName = cursor.getString(cursor.getColumnIndexOrThrow("first_name"));
                String lastName = cursor.getString(cursor.getColumnIndexOrThrow("last_name"));
                String emailValue = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String passwordValue = cursor.getString(cursor.getColumnIndexOrThrow("password"));

                userLogged = new User(firstName, lastName, emailValue, passwordValue);
            }
        } finally {
            if (cursor != null) cursor.close();
            if (db != null && db.isOpen()) db.close();
        }

        return userLogged;
    }

    public User register(User user) {
        User newUser = null;
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UsersTable.FIRST_NAME.getKey(), user.getFirstName());
        values.put(UsersTable.LAST_NAME.getKey(), user.getLastName());
        values.put(UsersTable.EMAIL.getKey(), user.getEmail());
        values.put(UsersTable.PASSWORD.getKey(), user.getPassword());

        try {
            long newUserId = db.insert("users", null, values);

            if (newUserId != -1) {
                userController.getById(newUserId);
            }

            return newUser;
        } finally {
            if (db != null && db.isOpen()) db.close();
        }

    }
}
