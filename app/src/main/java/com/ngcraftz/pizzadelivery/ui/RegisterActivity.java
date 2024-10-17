package com.ngcraftz.pizzadelivery.ui;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ngcraftz.pizzadelivery.R;
import com.ngcraftz.pizzadelivery.utils.SQLite;

public class RegisterActivity extends AppCompatActivity {
    private SQLite sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sqLiteHelper = new SQLite(getApplicationContext());

        Button register = findViewById(R.id.register);
        register.setOnClickListener((view -> registerNewAccount()));
    }

    private void registerNewAccount() {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        EditText firstName = findViewById(R.id.firstName);
        EditText lastName = findViewById(R.id.lastName);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);

        ContentValues values = new ContentValues();
        values.put("first_name", firstName.getText().toString());
        values.put("last_name", lastName.getText().toString());
        values.put("email", email.getText().toString());
        values.put("password", password.getText().toString());
        long newUserId = db.insert("users", null, values);
        System.out.println("----------------------------------------------------");
        System.out.println(newUserId);
        System.out.println("----------------------------------------------------");
    }
}