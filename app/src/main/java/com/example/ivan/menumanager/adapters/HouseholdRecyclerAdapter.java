package com.example.ivan.menumanager.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ivan.menumanager.R;
import com.example.ivan.menumanager.ViewPageActivity;
import com.example.ivan.menumanager.model.DBManager;

import java.util.Date;


/**
 * Created by Vanya on 14.4.2017 г..
 */

public class HouseholdRecyclerAdapter extends RecyclerView.Adapter<HouseholdRecyclerAdapter.MyViewHolder> {

    private Activity activity;
    private String[] householdNames;

    public HouseholdRecyclerAdapter(Activity activity){
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View row = inflater.inflate(R.layout.layout_households, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if(DBManager.households.size() != 0){
            householdNames = DBManager.households.keySet().toArray(new String[DBManager.households.size()]);
        }
        if(householdNames != null){
            holder.textView.setText(householdNames[position]);
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, ViewPageActivity.class);
                    DBManager.currentHousehold = householdNames[position];
                    activity.startActivity(intent);
                    activity.finish();

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return DBManager.households.size();
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
