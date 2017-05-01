package com.example.ivan.menumanager.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.ivan.menumanager.R;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by Vanya on 13.4.2017 г..
 */

public class DBManager extends SQLiteOpenHelper {
    private static Context context;
    private static DBManager ourInstance;
    public static TreeMap<String, Household> households = new TreeMap<>();
    public static TreeMap<String, Product> predefinedProducts = new TreeMap<>();
    public static ArrayList<Category> predefinedCategories = new ArrayList<>();
    public static ArrayList<String> predefinedMeasures = new ArrayList<>();
    public static ArrayList<String> predefinedExpiryTerms = new ArrayList<>();
    public static String currentHousehold;

    private static final String DB_NAME = "mdb";
    private static final int DB_VERSION = 10;

    //tables
    private static final String HOUSEHOLD = "Household";
    private static final String SHOPPING_LIST = "Shopping_list";
    private static final String CATEGORY = "Category";
    private static final String MEASURE = "Measure";
    private static final String EXPIRY_TERM = "ExpiryTerm";
    private static final String PRODUCT = "Product";
    private static final String RECIPE = "Recipe";
    private static final String SHOPPING_LIST_PRODUCT = "ShoppingList_Product";
    private static final String RECIPE_PRODUCT = "Recipe_Product";
    private static final String HOUSEHOLD_PRODUCT = "Household_Product";
    private static final String HOUSEHOLD_RECIPE = "Household_Recipe";
    private static final String HOUSEHOLD_SHOPPING_LIST = "Household_ShoppingList";

    //columns
    private static final String ID = "_id";
    private static final String NAME = "Name";
    private static final String IMAGE = "Image";
    private static final String QUANTITY = "Quantity";
    private static final String DESCRIPTION = "Description";
    private static final String EXPIRY_TERM_ID = "Expiry_term_id";
    private static final String PURCHASE_DATE = "Purchase_date";
    private static final String MEASURE_ID = "Measure_id";
    private static final String CATEGORY_ID = "Category_id";
    private static final String HOUSEHOLD_ID = "Household_Id";
    private static final String SHOPPING_LIST_ID = "Shopping_list_Id";
    private static final String PRODUCT_ID = "Product_Id";
    private static final String RECIPE_ID = "Recipe_Id";
    private static final String RECIPE_API_STRING = "Recipe_Id";

    //create tables
    private static final String CREATE_HOUSEHOLD = "CREATE TABLE " + HOUSEHOLD + "(" + ID + " INTEGER PRIMARY KEY," + NAME + " TEXT);";
    private static final String CREATE_SHOPPING_LIST = "CREATE TABLE " + SHOPPING_LIST + "(" + ID + " INTEGER PRIMARY KEY," + NAME + " TEXT);";
    private static final String CREATE_MEASURE = "CREATE TABLE " + MEASURE + "(" + ID + " INTEGER PRIMARY KEY," + NAME + " TEXT);";
    private static final String CREATE_EXPIRY_TERM = "CREATE TABLE " + EXPIRY_TERM + "(" + ID + " INTEGER PRIMARY KEY," + NAME + " TEXT);";
    private static final String CREATE_CATEGORY = "CREATE TABLE " + CATEGORY + "(" + ID + " INTEGER PRIMARY KEY," + NAME + " TEXT, " + IMAGE + " INTEGER);";
    private static final String CREATE_PRODUCT = "CREATE TABLE " + PRODUCT + "(" + ID + " INTEGER PRIMARY KEY," + NAME + " TEXT, " + MEASURE_ID + " INTEGER, " + CATEGORY_ID + " INTEGER);";
    private static final String CREATE_RECIPE = "CREATE TABLE " + RECIPE + "(" + ID + " INTEGER PRIMARY KEY," + RECIPE_API_STRING + " INTEGER, " + NAME + " INTEGER, " + IMAGE + " TEXT, " + DESCRIPTION + " TEXT);";
    private static final String CREATE_SHOPPING_LIST_PRODUCT = "CREATE TABLE " + SHOPPING_LIST_PRODUCT + "(" + SHOPPING_LIST_ID + " INTEGER, " + PRODUCT_ID + " INTEGER, " + QUANTITY + " REAL)";
    private static final String CREATE_RECIPE_PRODUCT = "CREATE TABLE " + RECIPE_PRODUCT + "(" + RECIPE_ID + " INTEGER, " + PRODUCT_ID + " INTEGER, " + QUANTITY + " REAL);";
    private static final String CREATE_HOUSEHOLD_PRODUCT = "CREATE TABLE " + HOUSEHOLD_PRODUCT + "(" + HOUSEHOLD_ID + " INTEGER, " + PRODUCT_ID + " INTEGER, " + QUANTITY + " REAL, " + PURCHASE_DATE + " INTEGER, " + EXPIRY_TERM_ID + " INTEGER);";
    private static final String CREATE_HOUSEHOLD_RECIPE = "CREATE TABLE " + HOUSEHOLD_RECIPE + "(" + HOUSEHOLD_ID + " INTEGER, " + RECIPE_ID + " INTEGER);";
    private static final String CREATE_HOUSEHOLD_SHOPPING_LIST = "CREATE TABLE " + HOUSEHOLD_SHOPPING_LIST + "(" + HOUSEHOLD_ID + " INTEGER, " + SHOPPING_LIST_ID + " INTEGER);";


    public static DBManager getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new DBManager(context);
            DBManager.context = context;
            loadInfo();
        }
        return ourInstance;
    }

    private DBManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_HOUSEHOLD);
        db.execSQL(CREATE_SHOPPING_LIST);
        db.execSQL(CREATE_MEASURE);
        db.execSQL(CREATE_EXPIRY_TERM);
        db.execSQL(CREATE_CATEGORY);
        db.execSQL(CREATE_PRODUCT);
        db.execSQL(CREATE_RECIPE);
        db.execSQL(CREATE_SHOPPING_LIST_PRODUCT);
        db.execSQL(CREATE_RECIPE_PRODUCT);
        db.execSQL(CREATE_HOUSEHOLD_PRODUCT);
        db.execSQL(CREATE_HOUSEHOLD_RECIPE);
        db.execSQL(CREATE_HOUSEHOLD_SHOPPING_LIST);

        insertPredefinedValues(db);
        insertPredefinedProducts(db);
        Toast.makeText(context, "ON CREATE WAS CALLED", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + HOUSEHOLD);
        db.execSQL("DROP TABLE IF EXISTS " + SHOPPING_LIST);
        db.execSQL("DROP TABLE IF EXISTS " + MEASURE);
        db.execSQL("DROP TABLE IF EXISTS " + EXPIRY_TERM);
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + RECIPE);
        db.execSQL("DROP TABLE IF EXISTS " + SHOPPING_LIST_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + RECIPE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + HOUSEHOLD_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + HOUSEHOLD_RECIPE);
        db.execSQL("DROP TABLE IF EXISTS " + HOUSEHOLD_SHOPPING_LIST);
        Toast.makeText(context, "ON UPGRADE WAS CALLED", Toast.LENGTH_SHORT).show();
        onCreate(db);

    }


    //adding only in household table because we still have no recipes and other stuff
    public void addHousehold(String householdName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, householdName);
        ourInstance.getWritableDatabase().insert(HOUSEHOLD, null, contentValues);
        households.put(householdName, new Household(householdName));
    }

    //TODO
    public void deleteHousehold() {

    }

    //TODO
    public void addProduct(Product product, double quantity, int expiryTermID, long
            currentTimeInMilli) {
        ContentValues contentValues = new ContentValues();
        //insert in predefined products
        if (!predefinedProducts.containsKey(product.getName())) {
            contentValues.put(NAME, product.getName());
            contentValues.put(MEASURE_ID, product.getMeasureID());
            contentValues.put(CATEGORY_ID, product.getFoodCategoryID());
            ourInstance.getWritableDatabase().insert(PRODUCT, null, contentValues);
            predefinedProducts.put(product.getName(), product);
            contentValues.clear();
        }
        int productID = 0;
        int householdID = 0;
        if (!households.get(currentHousehold).getProducts().containsKey(product.getName())) {
            //insert in household_product
            Cursor cursor = ourInstance.getWritableDatabase().rawQuery("SELECT " + ID + " FROM " + PRODUCT + " WHERE " + NAME + "=?", new String[]{product.getName()});
            while (cursor.moveToNext()) {
                productID = cursor.getInt(cursor.getColumnIndex(ID));
                cursor = ourInstance.getWritableDatabase().rawQuery("SELECT " + ID + " FROM " + HOUSEHOLD + " WHERE " + NAME + "=?", new String[]{currentHousehold});
                while (cursor.moveToNext()) {
                    householdID = cursor.getInt(cursor.getColumnIndex(ID));

                    contentValues.put(HOUSEHOLD_ID, householdID);
                    contentValues.put(PRODUCT_ID, productID);
                    contentValues.put(QUANTITY, quantity);
                    contentValues.put(PURCHASE_DATE, currentTimeInMilli);
                    contentValues.put(EXPIRY_TERM_ID, expiryTermID);
                    ourInstance.getWritableDatabase().insert(HOUSEHOLD_PRODUCT, null, contentValues);
                    contentValues.clear();
                }
            }
        } else {
            //update in household_product
            contentValues.put(HOUSEHOLD_ID, householdID);
            contentValues.put(PRODUCT_ID, productID);
            contentValues.put(QUANTITY, quantity);
            contentValues.put(PURCHASE_DATE, currentTimeInMilli);
            contentValues.put(EXPIRY_TERM_ID, expiryTermID);
            ourInstance.getWritableDatabase().update(HOUSEHOLD_PRODUCT, contentValues,
                    HOUSEHOLD_ID + " =? AND " + PRODUCT_ID + "=?", new String[]{String.valueOf(householdID), String.valueOf(productID)});
            contentValues.clear();
            Toast.makeText(context, product.getName() + " updated successfully", Toast.LENGTH_SHORT).show();
        }
        //gets overriden if existing
        Product productToAdd = product;
        product.setPurchaseDateInMilli(currentTimeInMilli);
        product.setQuantity(quantity);
        product.setExpiryTermID(expiryTermID);
        households.get(currentHousehold).addProduct(product);
    }


    //TODO
    public void removeProduct(String productName) {
        int productID = 0;
        int householdID = 0;
        Cursor cursor = ourInstance.getWritableDatabase().rawQuery("SELECT " + ID + " FROM " + PRODUCT + " WHERE " + NAME + "=?", new String[]{productName});
        while (cursor.moveToNext()) {
            productID = cursor.getInt(cursor.getColumnIndex(ID));
        }
        cursor = ourInstance.getWritableDatabase().rawQuery("SELECT " + ID + " FROM " + HOUSEHOLD + " WHERE " + NAME + "=?", new String[]{currentHousehold});
        while (cursor.moveToNext()) {
            householdID = cursor.getInt(cursor.getColumnIndex(ID));
        }
        ourInstance.getWritableDatabase().delete(HOUSEHOLD_PRODUCT,
                HOUSEHOLD_ID + " =? AND " + PRODUCT_ID + "=?", new String[]{String.valueOf(householdID), String.valueOf(productID)});
        if (households.get(currentHousehold).removeProduct(productName)) {
            Toast.makeText(context, productName + " removed successfully", Toast.LENGTH_SHORT).show();
        }

    }


    //TODO
    public void updateProduct() {

    }

    //TODO
    public void addRecipe() {
        //TODO insert recipe table recipe
        //insert recipe id and household id in common table

    }

    //TODO
    public void deleteRecipe() {

    }

    //TODO
    public void addShoppingList() {

    }

    //TODO
    public void deleteShoppingList() {

    }


    private static void loadInfo() {
        //get household id , name
        Cursor cursorHh = ourInstance.getWritableDatabase().rawQuery("SELECT " + ID + "," + NAME + " FROM " + HOUSEHOLD, null);
        while (cursorHh.moveToNext()) {
            int idHh = cursorHh.getInt(cursorHh.getColumnIndex(ID));
            String nameHh = cursorHh.getString(cursorHh.getColumnIndex(NAME));
            households.put(nameHh, new Household(nameHh));
            households.get(nameHh).setId(idHh);

            //get shoppingList id
            Cursor cursorHh_ShL = ourInstance.getWritableDatabase().rawQuery("SELECT " + SHOPPING_LIST_ID + " FROM " + HOUSEHOLD_SHOPPING_LIST + " WHERE " + HOUSEHOLD_ID + " = ?", new String[]{String.valueOf(idHh)});
            while (cursorHh_ShL.moveToNext()) {
                int idShL = cursorHh.getInt(cursorHh_ShL.getColumnIndex(SHOPPING_LIST_ID));

                //get shoppingList name
                Cursor cursorShL = ourInstance.getWritableDatabase().rawQuery("SELECT " + NAME + " FROM " + SHOPPING_LIST + " WHERE " + ID + " = ?", new String[]{String.valueOf(idShL)});
                ShoppingList shoppingList = null;
                while (cursorHh_ShL.moveToNext()) {
                    String nameShL = cursorShL.getString(cursorHh_ShL.getColumnIndex(NAME));
                    shoppingList = new ShoppingList(nameShL, new ArrayList());
                    shoppingList.setId(idShL);
                }

                //get shopping list's product id, quantity
                Cursor cursorShL_Pr = ourInstance.getWritableDatabase().rawQuery("SELECT " + PRODUCT_ID + "," + QUANTITY + " FROM " + SHOPPING_LIST_PRODUCT + " WHERE " + SHOPPING_LIST_ID + " = ?", new String[]{String.valueOf(idShL)});
                while (cursorShL_Pr.moveToNext()) {
                    int idPr = cursorShL_Pr.getInt(cursorShL_Pr.getColumnIndex(PRODUCT_ID));
                    double quantityPr = cursorShL_Pr.getDouble(cursorShL_Pr.getColumnIndex(QUANTITY));

                    //get shopping list's product name measureID, categoryID
                    Cursor cursorPr = ourInstance.getWritableDatabase().rawQuery("SELECT " + NAME + "," + MEASURE_ID + "," + CATEGORY_ID + " FROM " + PRODUCT + " WHERE " + ID + " = ?", new String[]{String.valueOf(idPr)});
                    while (cursorPr.moveToNext()) {
                        String namePr = cursorPr.getString(cursorPr.getColumnIndex(NAME));
                        int idMeasure = cursorPr.getInt(cursorPr.getColumnIndex(MEASURE_ID));
                        int idCateg = cursorPr.getInt(cursorPr.getColumnIndex(CATEGORY_ID));
                        Product productToPut = new Product(namePr, idMeasure, idCateg);
                        productToPut.setId(idPr);
                        shoppingList.addProduct(productToPut);
                    }
                }
                households.get(nameHh).addShoppingLists(shoppingList);
            }

            //get recipe id
            Cursor cursorHh_Rec = ourInstance.getWritableDatabase().rawQuery("SELECT " + RECIPE_ID + " FROM " + HOUSEHOLD_RECIPE + " WHERE " + HOUSEHOLD_ID + " = ?", new String[]{String.valueOf(idHh)});
            while (cursorHh_Rec.moveToNext()) {
                int idRec = cursorHh_Rec.getInt(cursorHh_Rec.getColumnIndex(RECIPE_ID));

                //get Recipe API id, name, image, description
                Cursor cursorRec = ourInstance.getWritableDatabase().rawQuery("SELECT " + RECIPE_API_STRING + "," + NAME + "," + IMAGE + "," + DESCRIPTION + " FROM " + RECIPE + " WHERE " + ID + " = ?", new String[]{String.valueOf(idRec)});
                Recipe recipe = null;
                while (cursorRec.moveToNext()) {
                    String apiIdRec = cursorRec.getString(cursorRec.getColumnIndex(RECIPE_API_STRING));
                    String nameRec = cursorRec.getString(cursorRec.getColumnIndex(NAME));
                    String imageRec = cursorRec.getString(cursorRec.getColumnIndex(IMAGE));
                    String descrRec = cursorRec.getString(cursorRec.getColumnIndex(DESCRIPTION));
                    recipe = new Recipe(nameRec, descrRec, imageRec);
                    recipe.setId(idRec);
                }


                //get recipe's product id, quantity
                Cursor cursorRec_Pr = ourInstance.getWritableDatabase().rawQuery("SELECT " + PRODUCT_ID + "," + QUANTITY + " FROM " + RECIPE_PRODUCT + " WHERE " + RECIPE_ID + " = ?", new String[]{String.valueOf(idRec)});
                while (cursorRec_Pr.moveToNext()) {
                    int idPr = cursorRec_Pr.getInt(cursorRec_Pr.getColumnIndex(PRODUCT_ID));
                    double quantityPr = cursorRec_Pr.getDouble(cursorRec_Pr.getColumnIndex(QUANTITY));

                    //get recipe's product name, measureID, categoryID
                    Cursor cursorPr = ourInstance.getWritableDatabase().rawQuery("SELECT " + NAME + "," + MEASURE_ID + "," + CATEGORY_ID + " FROM " + PRODUCT + " WHERE " + PRODUCT_ID + " = ?", new String[]{String.valueOf(idPr)});
                    Product ingredient = null;
                    while (cursorPr.moveToNext()) {
                        String namePr = cursorPr.getString(cursorPr.getColumnIndex(NAME));
                        int idMeasure = cursorPr.getInt(cursorPr.getColumnIndex(MEASURE_ID));
                        int idCateg = cursorPr.getInt(cursorPr.getColumnIndex(CATEGORY_ID));
                        ingredient = new Product(namePr, idMeasure, idCateg);
                        ingredient.setId(idPr);
                        recipe.addIngredient(ingredient);
                    }
                }
                households.get(nameHh).addRecipe(recipe);
            }
            //get household's product id, quantity, purchaseDate, expiryTermID
            Cursor cursorHh_Pr = ourInstance.getWritableDatabase().rawQuery("SELECT " + PRODUCT_ID + "," + QUANTITY + "," + PURCHASE_DATE + "," + EXPIRY_TERM_ID + " FROM " + HOUSEHOLD_PRODUCT + " WHERE " + HOUSEHOLD_ID + " = ?", new String[]{String.valueOf(idHh)});
            while (cursorHh_Pr.moveToNext()) {
                int idPr = cursorHh_Pr.getInt(cursorHh_Pr.getColumnIndex(PRODUCT_ID));
                double quantityPr = cursorHh_Pr.getDouble(cursorHh_Pr.getColumnIndex(QUANTITY));
                long purchPr = cursorHh_Pr.getLong(cursorHh_Pr.getColumnIndex(PURCHASE_DATE));
                int idExp = cursorHh_Pr.getInt(cursorHh_Pr.getColumnIndex(EXPIRY_TERM_ID));

                //get household's product name, measureID, categoryID
                Cursor cursorPr = ourInstance.getWritableDatabase().rawQuery("SELECT " + NAME + "," + MEASURE_ID + "," + CATEGORY_ID + " FROM " + PRODUCT + " WHERE " + ID + " = ?", new String[]{String.valueOf(idPr)});
                Product product = null;
                while (cursorPr.moveToNext()) {
                    String namePr = cursorPr.getString(cursorPr.getColumnIndex(NAME));
                    int idMeasure = cursorPr.getInt(cursorPr.getColumnIndex(MEASURE_ID));
                    int idCateg = cursorPr.getInt(cursorPr.getColumnIndex(CATEGORY_ID));
                    product = new Product(namePr, idMeasure, idCateg);
                    product.setId(idPr);
                    product.setPurchaseDateInMilli(purchPr);
                    product.setExpiryTermID(idExp);
                    product.setQuantity(quantityPr);
                    households.get(nameHh).addProduct(product);
                }
            }
        }
        //get all measures
        Cursor cursor = ourInstance.getWritableDatabase().rawQuery("SELECT " + NAME + " FROM " + MEASURE, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(NAME));
            predefinedMeasures.add(name);
        }
        //get all categories
        cursor = ourInstance.getWritableDatabase().rawQuery("SELECT " + NAME + "," + IMAGE + " FROM " + CATEGORY, null);
        while ((cursor.moveToNext())) {
            String name = cursor.getString(cursor.getColumnIndex(NAME));
            int image = cursor.getInt(cursor.getColumnIndex(IMAGE));
            Category category = new Category(name, image);
            predefinedCategories.add(category);
        }
        //get all expiry terms
        cursor = ourInstance.getWritableDatabase().rawQuery("SELECT " + NAME + " FROM " + EXPIRY_TERM, null);
        while ((cursor.moveToNext())) {
            String name = cursor.getString(cursor.getColumnIndex(NAME));
            predefinedExpiryTerms.add(name);
        }
        //get all predefined products
        cursor = ourInstance.getWritableDatabase().rawQuery("SELECT " + NAME + "," + MEASURE_ID + "," + CATEGORY_ID + " FROM " + PRODUCT, null);
        while ((cursor.moveToNext())) {
            String name = cursor.getString(cursor.getColumnIndex(NAME));
            int measureID = cursor.getInt(cursor.getColumnIndex(MEASURE_ID));
            int categoryID = cursor.getInt(cursor.getColumnIndex(CATEGORY_ID));
            Product product = new Product(name, measureID, categoryID);
            predefinedProducts.put(name, product);
        }
    }


    private void insertPredefinedValues(SQLiteDatabase db) {

        ContentValues contentValues = new ContentValues();
        String[] expiryTerms = new String[]{"expires in", "2 minutes", "1 day", "3 days", "7 days", "2 weeks", "1 month"};
        for (int i = 0; i < expiryTerms.length; i++) {
            contentValues.put(NAME, expiryTerms[i]);
            db.insert(EXPIRY_TERM, null, contentValues);
            contentValues.clear();
        }
        String[] categories = new String[]{"food category", "bakery", "dairy", "dressing", "fruits", "grain", "meat", "sauce", "veggies"};
        int[] images = new int[]{R.drawable.bread, R.drawable.dairy, R.drawable.spice_oil, R.drawable.fruits, R.drawable.beans, R.drawable.meat, R.drawable.sauce, R.drawable.veggies};
        contentValues.put(NAME, categories[0]);
        db.insert(CATEGORY, null, contentValues);
        contentValues.clear();
        for (int i = 0; i < categories.length; i++) {
            contentValues.put(NAME, categories[i+1]);
            contentValues.put(IMAGE, images[i]);
            db.insert(CATEGORY, null, contentValues);
            contentValues.clear();
        }
        String[] measures = new String[]{"measure", "g", "kg", "ml", "liter", "item", "pack"};
        for (int i = 0; i < measures.length; i++) {
            contentValues.put(NAME, measures[i]);
            db.insert(MEASURE, null, contentValues);
            contentValues.clear();
        }
    }


    public void insertPredefinedProducts(SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        Product[] products = new Product[]{
                new Product("Apple", 2, 4),
                new Product("Apricot", 2, 4),
                new Product("Artichoke", 2, 8),
                new Product("Avocado", 2, 4),
                new Product("Bacon", 1, 6),
                new Product("Bagel", 5, 1),
                new Product("Baguette", 5, 1),
                new Product("Baking powder", 1, 1),
                new Product("Balsamic vinegar", 4, 3),
                new Product("Banana", 2, 4),
                new Product("Basil", 1, 3),
                new Product("Bay leaf", 1, 3),
                new Product("Beans", 2, 5),
                new Product("Beaf", 2, 6),
                new Product("Black pepper", 2, 3),
                new Product("Blackberry", 2, 4),
                new Product("Blueberry", 2, 4),
                new Product("Bread", 5, 1),
                new Product("Bri", 2, 2),
                new Product("Broccoli", 2, 8),
                new Product("Brussels sprouts", 2, 8),
                new Product("Butter", 6, 2),
                new Product("Cabbage", 2, 8),
                new Product("Carot", 2, 8),
                new Product("Caviar", 2, 6),
                new Product("Celery", 2, 8),
                new Product("Cheddar", 2, 2),
                new Product("Cheese", 2, 2),
                new Product("Cherry", 2, 4),
                new Product("Chicken", 2, 6),
                new Product("Chili", 2, 7),
                new Product("Cucumber", 2, 8),
                new Product("Dill", 1, 3),
                new Product("Duck", 2, 6),
                new Product("Egg", 5, 2),
                new Product("Feta", 2, 2),
                new Product("Fish", 2, 6),
                new Product("Flour", 2, 1),
                new Product("Garlic", 2, 8),
                new Product("Grapefruit", 2, 4),
                new Product("Grapes", 2, 4),
                new Product("Green beans", 2, 5),
                new Product("Ham", 2, 6),
                new Product("Honey", 2, 3),
                new Product("Hummus", 2, 3),
                new Product("Ketchup", 4, 7),
                new Product("Lemon", 2, 4),
                new Product("Lettuce", 2, 8),
                new Product("Liver", 2, 6),
                new Product("Lobster", 2, 6),
                new Product("Macaroni", 2, 1),
                new Product("Milk", 2, 2),
                new Product("Mozzarella", 2, 2),
                new Product("Mushroom", 2, 8),
                new Product("Oil", 4, 3),
                new Product("Olive", 2, 8),
                new Product("Onion", 2, 8),
                new Product("Peas", 2, 5),
                new Product("Pepper", 2, 8),
                new Product("Pork", 2, 6),
                new Product("Potato", 2, 8),
                new Product("Salmon", 2, 6),
                new Product("Tabasco", 3, 7),
                new Product("Tomato", 2, 8),
                new Product("Turkey", 2, 6),
                new Product("White pepper", 1, 3),
                new Product("Yoghurt", 3, 2),
        };

        for (int i = 0; i < products.length; i++) {
            contentValues.put(NAME, products[i].getName());
            contentValues.put(MEASURE_ID, products[i].getMeasureID());
            contentValues.put(CATEGORY_ID, products[i].getFoodCategoryID());
            db.insert(PRODUCT, null, contentValues);
            contentValues.clear();
        }
    }


}
