package com.example.ivan.menumanager.household;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ivan.menumanager.R;
import com.example.ivan.menumanager.ViewPageActivity;
import com.example.ivan.menumanager.model.DBManager;
import com.example.ivan.menumanager.model.Product;

import java.util.ArrayList;

/**
 * Created by Vanya on 25.4.2017 г..
 */

public class PredefinedProductsAdapter extends RecyclerView.Adapter<PredefinedProductsAdapter.MyViewHolder> {

    private Context context;
    private ProductsFridgeAdapter.ICommunicator iCommunicator;
    private ArrayList<Product> predefinedProducts;

    public PredefinedProductsAdapter(Context context, ProductsFridgeAdapter.ICommunicator iCommunicator, ArrayList<Product> predefinedProducts){
        this.context = context;
        this.iCommunicator = iCommunicator;
        this.predefinedProducts = predefinedProducts;
    }

    @Override
    public PredefinedProductsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.choose_recycler, parent, false);
        PredefinedProductsAdapter.MyViewHolder viewHolder = new PredefinedProductsAdapter.MyViewHolder(row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PredefinedProductsAdapter.MyViewHolder holder, int position) {
        final Product productToRender = predefinedProducts.get(position);
        holder.textView.setText(productToRender.getName());
        holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditProductFragment editDialog = new EditProductFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("name", productToRender.getName());
                    bundle.putInt("measureID", productToRender.getMeasureID() );
                    bundle.putInt("categoryID", productToRender.getFoodCategoryID());
                    editDialog.setArguments(bundle);
                    editDialog.show(iCommunicator.getTheFragmentManager(), "editItem");

                    ChooseFragment ch = (ChooseFragment)iCommunicator.getTheFragmentManager().findFragmentByTag("chooseItem");
                    ch.dismiss();
                   //test ViewPageActivity vg = (ViewPageActivity) context;


                }
            });
    }

    @Override
    public int getItemCount() {

        return DBManager.predefinedProducts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        View row;
        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.row = itemView;
            textView = (TextView) row.findViewById(R.id.choose_item);
        }
    }

}
