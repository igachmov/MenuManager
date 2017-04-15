package com.example.ivan.menumanager.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import static com.example.ivan.menumanager.MainActivity.households;

/**
 * Created by Vanya on 13.4.2017 г..
 */

public class DBManager extends SQLiteOpenHelper{
    private static  Context context;
    private static DBManager ourInstance;
    private static final String CREATE_HOUSEHOLDS = "CREATE TABLE households(name text PRIMARY KEY NOT NULL);";

    public static DBManager getInstance(Context context) {
        if(ourInstance == null){
            ourInstance = new DBManager(context);
            DBManager.context = context;
            loadHouseholds();
        }
        return ourInstance;
    }

    private DBManager(Context context) {
        super(context, "mdb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_HOUSEHOLDS);
        Toast.makeText(context, "DBManager created", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private static void loadHouseholds(){
            Cursor cursor = ourInstance.getWritableDatabase().rawQuery("SELECT name FROM households;", null);
            while(cursor.moveToNext()) {
                String householdName = cursor.getString(cursor.getColumnIndex("name"));
                households.put(householdName, new Household(householdName));
            }
    }

    public void addHousehold(String householdName){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", householdName);
        ourInstance.getWritableDatabase().insert("households", null, contentValues);
        households.put(householdName, new Household(householdName));
    }
}
