package com.example.ivan.menumanager.household;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ivan.menumanager.R;
import com.example.ivan.menumanager.model.Category;
import com.example.ivan.menumanager.model.DBManager;
import com.example.ivan.menumanager.model.Product;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    public void onBindViewHolder(final ProductsFridgeAdapter.NewViewHolder holder, int position) {
        //get data from product
        final Product product = products.get(position);
        final String measure = DBManager.predefinedMeasures.get(product.getMeasureID());
        Category category = DBManager.predefinedCategories.get(product.getFoodCategoryID());
        int image = category.getImage();
        int expiryTermID = product.getExpiryTermID();
        long expiryTermInMilliseconds = product.getExpiryTermInMilliseconds(expiryTermID);
        long purchaseDateInMilliseconds = product.getPurchaseDateMinutes()*60*1000;
        long expiryDateInMilliseconds = purchaseDateInMilliseconds + expiryTermInMilliseconds;
        DateFormat df = new SimpleDateFormat("MM/dd/yy");
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(Long.parseLong(String.valueOf(purchaseDateInMilliseconds)));
        String date = df.format(c.getTime());


        //render data from product
        holder.productImage.setImageResource(image);
        holder.productName.setText(product.getName());
        holder.productQuantity.setText(product.getQuantity()+"");
        holder.productMeasure.setText(measure);
        holder.expiryTerm.setText(date);

        if(product.isExpired()){
            final AnimationDrawable drawable = new AnimationDrawable();
            final Handler handler = new Handler();
            drawable.addFrame(new ColorDrawable(ContextCompat.getColor(context, R.color.colorBlue_grey)), 400);
            drawable.addFrame(new ColorDrawable(Color.WHITE), 400);
            drawable.setOneShot(false);
            holder.row.setBackground(drawable);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    drawable.start();
                }
            }, 100);
        }

        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               EditProductFragment editProductFragment = new EditProductFragment();
               Bundle bundle = new Bundle();
               bundle.putString("name", product.getName());
                bundle.putDouble("quantity", product.getQuantity());
                bundle.putInt("measureID", product.getMeasureID());
                bundle.putInt("categoryID", product.getFoodCategoryID());
                bundle.putInt("expiryTermID",product.getExpiryTermID());
                editProductFragment.setArguments(bundle);
                editProductFragment.show(iCommunicator.getTheFragmentManager(), "editItem");
            }
        });
    }



    @Override
    public int getItemCount() {
        return products.size();
    }


    class NewViewHolder extends RecyclerView.ViewHolder {
        View row;
        ImageView productImage;
        TextView productName;
        TextView productQuantity;
        TextView productMeasure;
        TextView expiryTerm;

        public NewViewHolder(View row) {
            super(row);
            this.row = row;
            productImage = (ImageView) row.findViewById(R.id.product_image);
            productName = (TextView) row.findViewById(R.id.product_name_tv);
            productQuantity = (TextView) row.findViewById(R.id.qunatity_tv);
            productMeasure = (TextView) row.findViewById(R.id.measure_tv);
            expiryTerm = (TextView) row.findViewById(R.id.expiry_term_tv);
        }
    }


    public interface ICommunicator{

        public FragmentManager getTheFragmentManager();

    }

    public static String convertDate(String expiryDateInMilliseconds, String dateFormat) {
        DateFormat df = new SimpleDateFormat(dateFormat);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(Long.parseLong(expiryDateInMilliseconds));
        c.add(Calendar.HOUR_OF_DAY, 1); //Work-around to get your lost hour
        return df.format(c.getTime());
    }
}
