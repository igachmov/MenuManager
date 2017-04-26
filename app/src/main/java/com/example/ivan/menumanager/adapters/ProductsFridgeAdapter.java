package com.example.ivan.menumanager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ivan.menumanager.R;
import com.example.ivan.menumanager.ViewPageActivity;
import com.example.ivan.menumanager.fragments.ChooseFragment;
import com.example.ivan.menumanager.fragments.EditProductFragment;
import com.example.ivan.menumanager.model.Category;
import com.example.ivan.menumanager.model.DBManager;
import com.example.ivan.menumanager.model.Product;

import java.util.List;

/**
 * Created by Ivan on 4/12/2017.
 */

public class ProductsFridgeAdapter extends RecyclerView.Adapter<ProductsFridgeAdapter.NewViewHolder>{

    private List<Product> products;
    private Context context;
    public static ChooseFragment chooseDialog;

    public ProductsFridgeAdapter(Context context, List<Product> products) {
        this.products = products;
        this.context = context;
    }

    @Override
    public ProductsFridgeAdapter.NewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(context);
        View row = li.inflate(R.layout.products_fridge_recycler, parent,false);
        NewViewHolder vh = new NewViewHolder(row);
        return vh;
    }

    @Override
    public void onBindViewHolder(ProductsFridgeAdapter.NewViewHolder holder, int position) {
        Product product = products.get(position);
        String measure = DBManager.predefinedMeasures.get(product.getMeasureID() - 1);
        Category category = DBManager.predefinedCategories.get(product.getFoodCategoryID() - 1);
        int image = category.getImage();
        holder.productImage.setImageResource(image);
        holder.productName.setText(product.getName());
        holder.productQuantity.setText(product.getQuantity()+"");
        holder.productMeasure.setText(measure);
        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDialog = new ChooseFragment();
                chooseDialog.show(ViewPageActivity.fm, "chooseItem");
            }
        });
    }



    @Override
    public int getItemCount() {
        return products.size();
    }


    class NewViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        View row;
        TextView productQuantity;
        TextView productMeasure;

        public NewViewHolder(View row) {
            super(row);
            productImage = (ImageView) row.findViewById(R.id.product_image);
            productName = (TextView) row.findViewById(R.id.product_name_tv);
            this.row = row;
            productQuantity = (TextView) row.findViewById(R.id.qunatity_tv);
            productMeasure = (TextView) row.findViewById(R.id.measure_tv);
        }


    }
}
