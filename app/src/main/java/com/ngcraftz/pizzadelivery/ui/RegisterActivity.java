package com.ngcraftz.pizzadelivery.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
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

public class RegisterActivity extends AppCompatActivity {
    private AuthController authController;

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

        authController = new AuthController(getApplicationContext());

        Button register = findViewById(R.id.register);
        register.setOnClickListener((view -> registerNewAccount()));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void registerNewAccount() {
        EditText firstNameEditable = findViewById(R.id.firstName);
        EditText lastNameEditable = findViewById(R.id.lastName);
        EditText emailEditable = findViewById(R.id.email);
        EditText passwordEditable = findViewById(R.id.password);

        String firstName = firstNameEditable.getText().toString();
        String lastName = lastNameEditable.getText().toString();
        String email = emailEditable.getText().toString();
        String password = passwordEditable.getText().toString();

        User userLogged = authController.register(new User(firstName, lastName, email, password));

        if (userLogged != null) {
            SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLogged", true);
            editor.putString(UserKeys.FIRST_NAME.getKey(), userLogged.getFirstName());
            editor.putString(UserKeys.LAST_NAME.getKey(), userLogged.getLastName());
            editor.putString(UserKeys.EMAIL.getKey(), userLogged.getEmail());
            editor.apply();

            Toast.makeText(this, "Se registró exitosamente", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "El usuario no se pudo registrar, inténtelo de nuevo", Toast.LENGTH_LONG).show();
        }
    }
}