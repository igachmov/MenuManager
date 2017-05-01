package com.example.ivan.menumanager.household;

import android.app.Activity;
import android.content.Intent;
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
 * Created by Vanya on 14.4.2017 г..
 */

public class ChooseItemAdapter extends RecyclerView.Adapter<ChooseItemAdapter.MyViewHolder> {

    private Activity activity;
    private ArrayList<String> items;
    String itemType;

    public ChooseItemAdapter(Activity activity, ArrayList<String> items, String itemType) {
        this.activity = activity;
        this.items = items;
        this.itemType = itemType;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View row = inflater.inflate(R.layout.choose_recycler, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        switch (itemType) {
            case "household":
                if (this.items != null) {
                    holder.textView.setText(items.get(position));
                    holder.textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(activity, ViewPageActivity.class);
                            DBManager.currentHousehold = items.get(position);
                            activity.startActivity(intent);
                            activity.finish();
                        }
                    });
                }
                break;
            case "product":
                final String productToRender = this.items.get(position);
                holder.textView.setText(productToRender);
                holder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Product product = DBManager.predefinedProducts.get(productToRender);
                        EditProductFragment editDialog = new EditProductFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("callingObject", "predefined");
                        bundle.putString("name", product.getName());
                        bundle.putInt("measureID", product.getMeasureID());
                        bundle.putInt("categoryID", product.getFoodCategoryID());
                        editDialog.setArguments(bundle);
                        ViewPageActivity myActivity = (ViewPageActivity) activity;
                        editDialog.show(myActivity.getSupportFragmentManager(), "editItem");
                        ChooseItemFragment ch = (ChooseItemFragment) myActivity.getSupportFragmentManager().findFragmentByTag("chooseItem");
                        ch.dismiss();
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        View row;
        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.row = itemView;
            textView = (TextView) row.findViewById(R.id.choose_item);
        }
    }

}
