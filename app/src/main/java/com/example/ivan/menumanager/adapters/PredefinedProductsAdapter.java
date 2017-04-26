package com.example.ivan.menumanager.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ivan.menumanager.R;
import com.example.ivan.menumanager.ViewPageActivity;
import com.example.ivan.menumanager.fragments.EditProductFragment;
import com.example.ivan.menumanager.model.DBManager;

/**
 * Created by Vanya on 25.4.2017 г..
 */

public class PredefinedProductsAdapter extends RecyclerView.Adapter<PredefinedProductsAdapter.MyViewHolder> {

    private Activity activity;
    private String[] predefinedProductsName;

    public PredefinedProductsAdapter(Activity activity){
        this.activity = activity;
    }

    @Override
    public PredefinedProductsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View row = inflater.inflate(R.layout.choose_recycler, parent, false);
        PredefinedProductsAdapter.MyViewHolder viewHolder = new PredefinedProductsAdapter.MyViewHolder(row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PredefinedProductsAdapter.MyViewHolder holder, final int position) {
        if(DBManager.predefinedProducts.size() != 0){
            predefinedProductsName = DBManager.predefinedProducts.keySet().toArray(new String[DBManager.predefinedProducts.size()]);
        }
        if(predefinedProductsName != null){
            holder.textView.setText(predefinedProductsName[position]);
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditProductFragment editDialog = new EditProductFragment();
                    editDialog.show(ViewPageActivity.fm, "chooseItem");
                }
            });
        }
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
