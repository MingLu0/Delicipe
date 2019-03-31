package com.luo.ming.delicipe.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;
import com.luo.ming.delicipe.Helpers.Constants;
import com.luo.ming.delicipe.Models.Ingredient;
import com.luo.ming.delicipe.Models.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private Context context;
    public DatabaseHandler(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d("dbhandler","onCreate has been called");

        // create shopping list table from ingredient list
        String CREATE_SHOPPINGLIST_TABLE ="CREATE TABLE " + Constants.TABLE_SHOPPINGLIST_NAME + "("
                + Constants.KEY_INGREDIENT_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Constants.KEY_COUNT + " DOUBLE,"
                + Constants.KEY_UNIT + " TEXT,"+ Constants.KEY_ITEMNAME + " TEXT"+ " );";

        db.execSQL(CREATE_SHOPPINGLIST_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_SHOPPINGLIST_NAME);
        onCreate(db);

    }

    public void addIngredient(ArrayList<Ingredient>ingredients) {

        SQLiteDatabase db = this.getWritableDatabase();
        if (ingredients != null) {
            for (int i = 0; i < ingredients.size(); i++) {
                ContentValues values = new ContentValues();
                values.put(Constants.KEY_COUNT, ingredients.get(i).getCount());
                values.put(Constants.KEY_UNIT,ingredients.get(i).getUnit());
                values.put(Constants.KEY_ITEMNAME,ingredients.get(i).getIngredient());
                db.insert(Constants.TABLE_SHOPPINGLIST_NAME, null, values);
            }
        }
    }

    public ArrayList<Ingredient> getAllIngredients() {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Ingredient> ingredientList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_SHOPPINGLIST_NAME, new String[] {
                Constants.KEY_INGREDIENT_ITEM_ID,Constants.KEY_COUNT,Constants.KEY_UNIT,Constants.KEY_ITEMNAME}, null, null, null, null, null );

        if (cursor.moveToFirst()) {
            do {
                Ingredient ingredient = new Ingredient();
                ingredient.setID(cursor.getString(cursor.getColumnIndex(Constants.KEY_INGREDIENT_ITEM_ID)));
                Log.d("setID",cursor.getString(cursor.getColumnIndex(Constants.KEY_INGREDIENT_ITEM_ID)));
                ingredient.setCount(Double.parseDouble(cursor.getString(cursor.getColumnIndex(Constants.KEY_COUNT))));
                //Log.d("database",ingredient.getItemName());
                ingredient.setUnit(cursor.getString(cursor.getColumnIndex(Constants.KEY_UNIT)));
                ingredient.setIngredient(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEMNAME)));

                ingredientList.add(ingredient);

            }while (cursor.moveToNext());
        }

        return ingredientList;
    }

    public void deleteShoppingItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_SHOPPINGLIST_NAME, Constants.KEY_INGREDIENT_ITEM_ID + " = ?",
                new String[] {String.valueOf(id)});

        db.close();

    }



}
