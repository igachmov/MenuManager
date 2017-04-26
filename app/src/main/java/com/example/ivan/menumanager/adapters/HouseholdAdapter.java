package com.example.ivan.menumanager.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ivan.menumanager.R;
import com.example.ivan.menumanager.ViewPageActivity;
import com.example.ivan.menumanager.model.DBManager;


/**
 * Created by Vanya on 14.4.2017 г..
 */

public class HouseholdAdapter extends RecyclerView.Adapter<HouseholdAdapter.MyViewHolder> {

    private Activity activity;
    private String[] householdNames;

    public HouseholdAdapter(Activity activity){
        this.activity = activity;
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
            textView = (TextView) row.findViewById(R.id.choose_item);
        }
    }
}
