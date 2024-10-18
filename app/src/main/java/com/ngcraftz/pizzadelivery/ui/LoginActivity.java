package com.ngcraftz.pizzadelivery.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ngcraftz.pizzadelivery.MainActivity;
import com.ngcraftz.pizzadelivery.R;
import com.ngcraftz.pizzadelivery.controllers.AuthController;
import com.ngcraftz.pizzadelivery.enums.UserKeys;
import com.ngcraftz.pizzadelivery.models.User;
import com.ngcraftz.pizzadelivery.utils.SQLiteHelper;

public class LoginActivity extends AppCompatActivity {
    private AuthController authController;

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

        authController = new AuthController(getApplicationContext());

        Button loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(view -> login());

        registerNewAccount();
    }

    private void login() {
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        EditText email = findViewById(R.id.emailText);
        EditText password = findViewById(R.id.passwordText);

        User user = authController.login(email.getText().toString(), password.getText().toString());

        if (user != null) {
            editor.putBoolean("isLogged", true);
            editor.putString(UserKeys.FIRST_NAME.getKey(), user.getFirstName());
            editor.putString(UserKeys.LAST_NAME.getKey(), user.getLastName());
            editor.putString(UserKeys.EMAIL.getKey(), user.getEmail());
            editor.apply();

            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "No se pudo iniciar sesión, intentelo de nuevo", Toast.LENGTH_LONG).show();
        }
    }

    private void registerNewAccount() {
        TextView registerAccount = findViewById(R.id.registerAccount);
        registerAccount.setOnClickListener((view -> {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        }));
    }
}