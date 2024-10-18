package com.ngcraftz.pizzadelivery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ngcraftz.pizzadelivery.adapters.PizzaAdapter;
import com.ngcraftz.pizzadelivery.controllers.PizzasController;
import com.ngcraftz.pizzadelivery.enums.UserKeys;
import com.ngcraftz.pizzadelivery.ui.CreatePizzaActivity;
import com.ngcraftz.pizzadelivery.ui.LoginActivity;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private PizzasController pizzasController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        pizzasController = new PizzasController(getApplicationContext());

        verifyUserAuthenticated();
        loadUserData();
        loadPizzas();
        manageUi();
    }

    private void loadUserData() {
        TextView userNamesTextView = findViewById(R.id.userName);
        TextView userEmailTextView = findViewById(R.id.userEmail);
        TextView userPlaceholderTextView = findViewById(R.id.userPlaceholder);
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        String firstName = sharedPreferences.getString(UserKeys.FIRST_NAME.getKey(), "Username");
        String lastName = sharedPreferences.getString(UserKeys.LAST_NAME.getKey(), "Username");
        String email = sharedPreferences.getString(UserKeys.EMAIL.getKey(), "example@mail.com");
        userNamesTextView.setText(String.format("%s %s", firstName, lastName));
        userEmailTextView.setText(email);
        userPlaceholderTextView.setText(String.format("%s%s", firstName.charAt(0), lastName.charAt(0)));
    }

    private void loadPizzas() {
        RecyclerView recyclerView = findViewById(R.id.pizzasRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PizzaAdapter pizzaAdapter = new PizzaAdapter(pizzasController.getAll());
        recyclerView.setAdapter(pizzaAdapter);
    }

    private void verifyUserAuthenticated() {
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        boolean isLogged = sharedPreferences.getBoolean("isLogged", false);
        if (!isLogged) startActivity(new Intent(this, LoginActivity.class));
    }

    private void manageUi() {
        Button logout = findViewById(R.id.logout);
        Button createPizzaButton = findViewById(R.id.createPizza);

        logout.setOnClickListener(view -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLogged", false);
            editor.remove(UserKeys.FIRST_NAME.getKey());
            editor.remove(UserKeys.LAST_NAME.getKey());
            editor.remove(UserKeys.EMAIL.getKey());
            editor.apply();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        createPizzaButton.setOnClickListener(view -> {
            startActivity(new Intent(this, CreatePizzaActivity.class));
            finish();
        });
    }
}