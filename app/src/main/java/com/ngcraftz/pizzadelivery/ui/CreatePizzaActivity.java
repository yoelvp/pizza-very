package com.ngcraftz.pizzadelivery.ui;

import android.content.Intent;
import android.os.Bundle;
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
import com.ngcraftz.pizzadelivery.controllers.PizzasController;
import com.ngcraftz.pizzadelivery.models.Pizza;

public class CreatePizzaActivity extends AppCompatActivity {
    private PizzasController pizzasController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_pizza);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        pizzasController = new PizzasController(this);
        createPizza();
    }

    private void createPizza() {
        Button createPizzaButton = findViewById(R.id.createPizza);


        createPizzaButton.setOnClickListener(view -> {
            EditText pizzaName = findViewById(R.id.textNameCreate);
            EditText pizzaDescription = findViewById(R.id.textDescriptionCreate);
            EditText pizzaPrice = findViewById(R.id.textPriceCreate);

            Pizza newPizza = new Pizza(
                    pizzaName.getText().toString(),
                    pizzaDescription.getText().toString(),
                    Double.parseDouble(pizzaPrice.getText().toString())
            );

            Pizza createdPizza = pizzasController.create(newPizza);

            if (createdPizza != null) {
                Toast.makeText(this, "Se registró la pizza con éxito", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "No se a registrado la pizza :(", Toast.LENGTH_LONG).show();
            }

        });
    }
}