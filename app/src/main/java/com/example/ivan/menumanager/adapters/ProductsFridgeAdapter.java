package com.example.ivan.menumanager.adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ivan.menumanager.R;
import com.example.ivan.menumanager.fragments.ChooseFragment;
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
    ICommunicator iCommunicator;

    public ProductsFridgeAdapter(Context context, ICommunicator iCommunicator, List<Product> products) {
        this.products = products;
        this.context = context;
        this.iCommunicator = iCommunicator;
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
        String measure = DBManager.predefinedMeasures.get(product.getMeasureID());
        Category category = DBManager.predefinedCategories.get(product.getFoodCategoryID());
        int image = category.getImage();
        holder.productImage.setImageResource(image);
        holder.productName.setText(product.getName());
        holder.productQuantity.setText(product.getQuantity()+"");
        holder.productMeasure.setText(measure);
        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseFragment chooseDialog = new ChooseFragment();
                chooseDialog.show(iCommunicator.getTheFragmentManager(), "chooseItem");
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


    public interface ICommunicator{

        public FragmentManager getTheFragmentManager();
    }
}
