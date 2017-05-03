package com.example.ivan.menumanager.shopping_list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ivan.menumanager.R;
import com.example.ivan.menumanager.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 5/2/2017.
 */

public class ShowShoppinglistAdapter extends RecyclerView.Adapter<ShowShoppinglistAdapter.NewViewHolder>{


    private List<Product> products;
    private Context context;
    private Product product;
    public ShowShoppinglistAdapter(Context context, List<Product> products) {
        this.products = products;
        this.context = context;
    }
    @Override
    public ShowShoppinglistAdapter.NewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(context);
        View row = li.inflate(R.layout.show_shoppinglist_fragment_recycler, parent,false);
        ShowShoppinglistAdapter.NewViewHolder vh = new ShowShoppinglistAdapter.NewViewHolder(row);
        return vh;
    }

    @Override
    public void onBindViewHolder(ShowShoppinglistAdapter.NewViewHolder holder, int position) {
        product = products.get(position);
        holder.productName.setText(product.getName());
        holder.productAmount.setText(product.getQuantity()+"");
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class NewViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView productAmount;

        public NewViewHolder(View row) {
            super(row);
            productName = (TextView) row.findViewById(R.id.ingredient_shoppinglist_name_tv);
            productAmount = (TextView) row.findViewById(R.id.ingredint_shoppinglist_amount_tv);

        }


    }

}
