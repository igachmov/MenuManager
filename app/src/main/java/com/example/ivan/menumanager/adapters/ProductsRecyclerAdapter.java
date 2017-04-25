package com.example.ivan.menumanager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ivan.menumanager.R;
import com.example.ivan.menumanager.model.Category;
import com.example.ivan.menumanager.model.DBManager;
import com.example.ivan.menumanager.model.Product;

import java.util.List;

/**
 * Created by Ivan on 4/12/2017.
 */

public class ProductsRecyclerAdapter extends RecyclerView.Adapter<ProductsRecyclerAdapter.NewViewHolder>{

    private List<Product> products;
    private Context context;

    public ProductsRecyclerAdapter(Context context, List<Product> products) {
        this.products = products;
        this.context = context;
    }

    @Override
    public ProductsRecyclerAdapter.NewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(context);
        View row = li.inflate(R.layout.layout_products, parent,false);
        NewViewHolder vh = new NewViewHolder(row);
        return vh;
    }

    @Override
    public void onBindViewHolder(ProductsRecyclerAdapter.NewViewHolder holder, int position) {
        Product product = products.get(position);
        String measure = DBManager.predefinedMeasures.get(product.getMeasureID() - 1);
        Category category = DBManager.predefinedCategories.get(product.getFoodCategoryID() - 1);
        int image = category.getImage();
        holder.productImage.setImageResource(image);
        holder.productName.setText(product.getName());
       // no need to display this -   holder.productExpiryTerm.setText(product.getExpiryTerm());
        holder.productQuantity.setText(product.getQuantity()+"");
        holder.productMeasure.setText(measure);

    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    class NewViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productExpiryTerm;
        TextView productQuantity;
        TextView productMeasure;
        Button removeButton;

        public NewViewHolder(View row) {
            super(row);
            productImage = (ImageView) row.findViewById(R.id.product_image);
            productName = (TextView) row.findViewById(R.id.product_name_tv);
           // productExpiryTerm = (TextView) row.findViewById(R.id.date_tv);
            productQuantity = (TextView) row.findViewById(R.id.qunatity_tv);
            productMeasure = (TextView) row.findViewById(R.id.measure_tv);
           // removeButton = (Button) row.findViewById(R.id.remove_button);
        }


    }
}
