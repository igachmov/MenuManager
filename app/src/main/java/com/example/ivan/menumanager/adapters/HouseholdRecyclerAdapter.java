package com.example.ivan.menumanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ivan.menumanager.R;
import com.example.ivan.menumanager.ViewPageActivity;
import com.example.ivan.menumanager.fragments.ProductsFragment;
import com.example.ivan.menumanager.model.Household;

import static com.example.ivan.menumanager.MainActivity.households;

/**
 * Created by Vanya on 14.4.2017 г..
 */

public class HouseholdRecyclerAdapter extends RecyclerView.Adapter<HouseholdRecyclerAdapter.MyViewHolder> {

    Context context;
    String[] householdNames;

    public HouseholdRecyclerAdapter(Context context){
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.layout_households, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if(households.size() != 0){
            householdNames = households.keySet().toArray(new String[households.size()]);
        }
        if(householdNames != null){
            holder.textView.setText(householdNames[position]);
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ViewPageActivity.class);
                    intent.putExtra("Household name", householdNames[position]);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return households.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        View row;
        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.row = itemView;
            textView = (TextView) row.findViewById(R.id.household_name);
        }
    }
}
