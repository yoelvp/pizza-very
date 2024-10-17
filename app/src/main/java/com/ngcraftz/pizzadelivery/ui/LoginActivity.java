package com.ngcraftz.pizzadelivery.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ngcraftz.pizzadelivery.R;
import com.ngcraftz.pizzadelivery.utils.SQLite;

public class LoginActivity extends AppCompatActivity {
    private SQLite sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sqLiteHelper = new SQLite(getApplicationContext());

        Button loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(view -> login());

        registerNewAccount();
    }

    private void login() {
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
        EditText email = findViewById(R.id.emailText);
        EditText password = findViewById(R.id.passwordText);

        String[] selectionArgs = { email.getText().toString(), password.getText().toString()};
        Cursor user = db.rawQuery("SELECT * FROM users WHERE email = ? AND password = ?", selectionArgs);
        System.out.println("----------------------------------------------------");
        System.out.println(user.getColumnName(0));
        System.out.println(user.getColumnName(1));
        System.out.println(user.getColumnName(2));
        System.out.println(user.getColumnName(3));
        System.out.println(user.getColumnName(4));
        System.out.println("----------------------------------------------------");

        editor.putString("email", "email");
        editor.putBoolean("isLogged", true);
        editor.apply();
        user.close();
    }

    private void registerNewAccount() {
        TextView registerAccount = findViewById(R.id.registerAccount);
        registerAccount.setOnClickListener((view -> {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        }));
    }
}