package com.example.ivan.menumanager.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Maria on 13.4.2017 г..
 */

class DBManager extends SQLiteOpenHelper{
    private static  Context context;
    private static final DBManager ourInstance = new DBManager(context);

    static DBManager getInstance() {
        return ourInstance;
    }

    private DBManager(Context context) {
        super(context, "mdb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
