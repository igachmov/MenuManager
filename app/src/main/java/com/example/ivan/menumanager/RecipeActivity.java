package com.example.ivan.menumanager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.design.internal.NavigationMenuItemView;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ivan.menumanager.fragments.ChooseHouseholdFragment;
import com.example.ivan.menumanager.fragments.RecipesFragment;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrListener;
import com.r0adkll.slidr.model.SlidrPosition;


public class RecipeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView toolbarTitle;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        SlidrConfig config = new SlidrConfig.Builder()
                .primaryColor(Color.blue(R.color.colorBone))
                        .secondaryColor(Color.blue(R.color.colorWhite))
                                .position(SlidrPosition.LEFT)
                                .sensitivity(1f)
                                .scrimColor(Color.BLACK)
                                .scrimStartAlpha(0.8f)
                                .scrimEndAlpha(0f)
                                .velocityThreshold(2400)
                                .distanceThreshold(0.25f)
                                .edge(false)
                                .edgeSize(0.18f) // The % of the screen that counts as the edge, default 18%
                                .listener(new SlidrListener(){
                                    @Override
                                    public void onSlideStateChanged(int state) {

                                    }

                                    @Override
                                    public void onSlideChange(float percent) {

                                    }

                                    @Override
                                    public void onSlideOpened() {

                                    }

                                    @Override
                                    public void onSlideClosed() {

                                    }
                                })
                                .build();

        Slidr.attach(this, config);

//        image = (ImageView) findViewById(R.id.recipe_image2);
//        byte[] byteArray = getIntent().getByteArrayExtra("imageBitmap");
//        Bitmap imageBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//        image.setImageBitmap(imageBitmap);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String householdName = getIntent().getStringExtra("Household name");
        if(householdName != null && !householdName.isEmpty()){
            toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
            toolbarTitle.setText(householdName);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                Log.e("Vanya", "vliza v case");
                NavUtils.navigateUpFromSameTask(this);
                Log.e("Vanya", "izliza ot case");
                return true;
            case R.id.menu_main_course:
                //TODO
                return true;
            case R.id.menu_households:
                ChooseHouseholdFragment chooseHousehold = new ChooseHouseholdFragment();
                chooseHousehold.show(getSupportFragmentManager(), "chooseHousehold");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}

