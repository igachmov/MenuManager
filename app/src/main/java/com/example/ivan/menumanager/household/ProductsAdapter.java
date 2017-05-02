package com.example.ivan.menumanager.household;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ivan.menumanager.R;
import com.example.ivan.menumanager.ViewPageActivity;
import com.example.ivan.menumanager.model.Category;
import com.example.ivan.menumanager.model.DBManager;
import com.example.ivan.menumanager.model.Product;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by Ivan on 4/12/2017.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.NewViewHolder> {

    private ArrayList<Product> products;
    private Activity activity;

    public ProductsAdapter(Activity activity, ArrayList<Product> products) {
        this.products = products;
        this.activity = activity;
    }

    @Override
    public ProductsAdapter.NewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(activity);
        View row = li.inflate(R.layout.products_fridge_recycler, parent, false);
        NewViewHolder vh = new NewViewHolder(row);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ProductsAdapter.NewViewHolder holder, int position) {
        //get data from product
        final Product product = products.get(position);
        final String measure = DBManager.predefinedMeasures.get(product.getMeasureID());
        Category category = DBManager.predefinedCategories.get(product.getFoodCategoryID());
        int image = category.getImage();
        int expiryTermInDays = (int) ((((product.getExpiryTermInMilli() / 1000) / 60) / 60) / 24);
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        Calendar c = new GregorianCalendar(TimeZone.getTimeZone("GMT+2"));
        c.setTimeInMillis(product.getPurchaseDateInMilli());
        c.add(Calendar.DAY_OF_MONTH, expiryTermInDays);
        String date = df.format(c.getTime());

        //render data from product
        holder.productImage.setImageResource(image);
        holder.productName.setText(product.getName());
        if (measure.equals("item") || measure.equals("pack")) {
            holder.productQuantity.setText((int) product.getQuantity() + "");
        } else {
            holder.productQuantity.setText(product.getQuantity() + "");
        }
        holder.productMeasure.setText(measure);
        holder.expiryTerm.setText(date);

        if (product.isExpired()) {
            final Animation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(700);
            anim.setStartOffset(20);
            holder.expiryTerm.setHasTransientState(true);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    holder.expiryTerm.setHasTransientState(false);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);
            holder.expiryTerm.startAnimation(anim);
            holder.expiryTerm.setText("expired");
            holder.expiryTerm.setTextColor(ContextCompat.getColor(activity, R.color.colorAppleRed));
            holder.expiryTerm.setTextSize(15.0f);
        }

        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProductFragment editProductFragment = new EditProductFragment();
                Bundle bundle = new Bundle();
                bundle.putString("callingObject", "product");
                bundle.putString("name", product.getName());
                bundle.putDouble("quantity", product.getQuantity());
                bundle.putInt("measureID", product.getMeasureID());
                bundle.putInt("categoryID", product.getFoodCategoryID());
                bundle.putInt("expiryTermID", product.getExpiryTermID());
                editProductFragment.setArguments(bundle);
                ViewPageActivity vp = (ViewPageActivity) activity;
                editProductFragment.show(vp.getSupportFragmentManager(), "editItem");
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
            this.productImage = (ImageView) row.findViewById(R.id.product_image);
            this.productName = (TextView) row.findViewById(R.id.product_name_tv);
            this.productQuantity = (TextView) row.findViewById(R.id.qunatity_tv);
            this.productMeasure = (TextView) row.findViewById(R.id.measure_tv);
            this.expiryTerm = (TextView) row.findViewById(R.id.expiry_term_tv);
        }
    }

    public void removeProduct(String name, double quantity){
        for (int i = 0; i < this.products.size(); i++) {
            if(this.products.get(i).getName().equals(name) && this.products.get(i).getQuantity() == quantity) {
                this.products.remove(this.products.get(i));
            }
        }
    }

    public void addProduct(Product product){
        boolean isNotAvailable = true;
        for(int i = 0; i < this.products.size(); i++){
            if(this.products.get(i).getName().equals(product.getName())){
                this.products.get(i).setQuantity(product.getQuantity());
                this.products.get(i).setExpiryTermID(product.getExpiryTermID());
                this.products.get(i).setPurchaseDateInMilli(product.getPurchaseDateInMilli());
                isNotAvailable = false;
            }
        }
        if(isNotAvailable){
            this.products.add(product);
            Collections.sort(this.products, new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }
    }

    public void changeProducts(ArrayList<Product> products){
        this.products = products;
    }


}
