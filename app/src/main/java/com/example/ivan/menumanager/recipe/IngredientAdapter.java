package com.example.ivan.menumanager.recipe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ivan.menumanager.R;
import com.example.ivan.menumanager.model.Product;

import java.util.List;

/**
 * Created by Ivan on 4/22/2017.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.NewViewHolder>{

    private List<Product> products;
    private Context context;

    public IngredientAdapter(Context context,  List<Product> products) {
        this.products = products;
        this.context = context;
    }

    @Override
    public IngredientAdapter.NewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(context);
        View row = li.inflate(R.layout.ingredients_recycler, parent,false);
        IngredientAdapter.NewViewHolder vh = new IngredientAdapter.NewViewHolder(row);
        return vh;
    }

    @Override
    public void onBindViewHolder(IngredientAdapter.NewViewHolder holder, int position) {
        Product product = products.get(position);
        holder.ingredientName.setText(product.getName());
        holder.ingredientAmount.setText(product.getQuantity()+"");
        holder.ingredientUnit.setText(product.getUnit());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class NewViewHolder extends RecyclerView.ViewHolder  {
        TextView ingredientName;
        TextView ingredientAmount;
        TextView ingredientUnit;

        public NewViewHolder(View row) {
            super(row);
            ingredientName = (TextView) row.findViewById(R.id.ingredient_name_tv);
            ingredientAmount = (TextView) row.findViewById(R.id.ingredint_amount_tv);
            ingredientUnit = (TextView) row.findViewById(R.id.ingredint_unit_tv);
        }
    }

}
