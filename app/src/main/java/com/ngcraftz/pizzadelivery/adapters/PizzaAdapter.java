package com.ngcraftz.pizzadelivery.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ngcraftz.pizzadelivery.R;
import com.ngcraftz.pizzadelivery.models.Pizza;

import java.util.List;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder> {
    private final List<Pizza> pizzaList;

    public PizzaAdapter(List<Pizza> pizzas) {
        this.pizzaList = pizzas;
    }

    static class PizzaViewHolder extends RecyclerView.ViewHolder {
        TextView pizzaName;
        TextView pizzaDescription;
        TextView pizzaPrice;

        public PizzaViewHolder(@NonNull View itemView) {
            super(itemView);
            pizzaName = itemView.findViewById(R.id.pizzaName);
            pizzaDescription = itemView.findViewById(R.id.pizzaDescription);
            pizzaPrice = itemView.findViewById(R.id.pizzaPrice);
        }
    }

    @NonNull
    @Override
    public PizzaAdapter.PizzaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pizza, parent, false);

        return new PizzaViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull PizzaAdapter.PizzaViewHolder holder, int position) {
        Pizza pizza = pizzaList.get(position);
        holder.pizzaName.setText(pizza.getName());
        holder.pizzaDescription.setText(pizza.getDescription());
        holder.pizzaPrice.setText(String.format("S/. %.2f", pizza.getPrice()));
    }

    @Override
    public int getItemCount() {
        return pizzaList.size();
    }
}
