package com.luo.ming.delicipe.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcelable;
import android.util.Log;

import com.luo.ming.delicipe.Helpers.Constants;
import com.luo.ming.delicipe.Models.Ingredient;
import com.luo.ming.delicipe.Models.UserRecipe;
import com.luo.ming.delicipe.Models.UserRecipeCover;
import com.luo.ming.delicipe.Models.UserRecipeIngredient;
import com.luo.ming.delicipe.Models.UserRecipeStep;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {


    private static DatabaseHandler INSTANCE;

    public DatabaseHandler(final Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    public static DatabaseHandler getDataBase(Context context){

        if (INSTANCE == null ){
            synchronized (DatabaseHandler.class){
                INSTANCE = new DatabaseHandler(context);
                return INSTANCE;
            }
        }
        return INSTANCE;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d("dbhandler","onCreate has been called");

        // create shopping list table from ingredient list
        String CREATE_SHOPPINGLIST_TABLE ="CREATE TABLE " + Constants.TABLE_SHOPPING_LIST_NAME + "("
                + Constants.KEY_INGREDIENT_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Constants.KEY_COUNT + " DOUBLE,"
                + Constants.KEY_UNIT + " TEXT,"+ Constants.KEY_ITEM_NAME + " TEXT"+ " );";

        String CREATE_USER_COVER_TABLE = "CREATE TABLE " + Constants.TABLE_USER_RECIPE_COVER + "("
                + Constants.KEY_COVER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Constants.KEY_COVER_IMAGE_URI + " TEXT,"
                + Constants.KEY_COVER_NAME + " TEXT NOT NULL," + Constants.KEY_COVER_COOKING_TIME + " INTEGER,"
                + Constants.KEY_COVER_SERVING_SIZE + " INTEGER," + Constants.KEY_COVER_COMMENT + " TEXT" +");";

        String CREATE_USER_INGREDIENT_TABLE = "CREATE TABLE " + Constants.TABLE_USER_INGREDIENT + "("
                + Constants.KEY_USER_INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Constants.KEY_USER_INGREDIENT_UNIT + " TEXT,"
                + Constants.KEY_USER_INGREDIENT_NAME + " TEXT," + Constants.KEY_USER_INGREDIENT_AMOUNT + " FLOAT,"
                + Constants.KEY_USER_INGREDIENT_COVER_ID + " INTEGER,"
                + " FOREIGN KEY (" + Constants.KEY_USER_INGREDIENT_COVER_ID + ") REFERENCES " + Constants.TABLE_USER_RECIPE_COVER
                + " (" + Constants.KEY_COVER_ID + "));";

        String CREATE_USER_COOKING_STEP_TABLE = "CREATE TABLE " + Constants.TABLE_USER_STEP + "("
                + Constants.KEY_USER_STEP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Constants.KEY_USER_STEP_IMAGE_URI + " TEXT,"
                + Constants.KEY_USER_STEP_TEXT + " TEXT," + Constants.KEY_USER_STEP_COVER_ID + " INTEGER,"
                + " FOREIGN KEY (" + Constants.KEY_USER_STEP_COVER_ID + ") REFERENCES " + Constants.TABLE_USER_RECIPE_COVER
                + " (" + Constants.KEY_COVER_ID + "));";

        db.execSQL(CREATE_SHOPPINGLIST_TABLE);
        db.execSQL(CREATE_USER_COVER_TABLE);
        db.execSQL(CREATE_USER_INGREDIENT_TABLE);
        db.execSQL(CREATE_USER_COOKING_STEP_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_SHOPPING_LIST_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_USER_RECIPE_COVER);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_USER_INGREDIENT);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_USER_STEP);
        onCreate(db);

    }

    public void addIngredient(ArrayList<Ingredient>ingredients) {

        SQLiteDatabase db = this.getWritableDatabase();
        if (ingredients != null) {
            for (int i = 0; i < ingredients.size(); i++) {
                ContentValues values = new ContentValues();
                values.put(Constants.KEY_COUNT, ingredients.get(i).getCount());
                values.put(Constants.KEY_UNIT,ingredients.get(i).getUnit());
                values.put(Constants.KEY_ITEM_NAME,ingredients.get(i).getIngredient());
                db.insert(Constants.TABLE_SHOPPING_LIST_NAME, null, values);
            }
        }
    }

    public ArrayList<Ingredient> getAllIngredients() {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Ingredient> ingredientList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_SHOPPING_LIST_NAME, new String[] {
                Constants.KEY_INGREDIENT_ITEM_ID,Constants.KEY_COUNT,Constants.KEY_UNIT,Constants.KEY_ITEM_NAME}, null, null, null, null, null );

        if (cursor.moveToFirst()) {
            do {
                Ingredient ingredient = new Ingredient();
                ingredient.setID(cursor.getString(cursor.getColumnIndex(Constants.KEY_INGREDIENT_ITEM_ID)));
                Log.d("setID",cursor.getString(cursor.getColumnIndex(Constants.KEY_INGREDIENT_ITEM_ID)));
                ingredient.setCount(Double.parseDouble(cursor.getString(cursor.getColumnIndex(Constants.KEY_COUNT))));
                //Log.d("database",ingredient.getItemName());
                ingredient.setUnit(cursor.getString(cursor.getColumnIndex(Constants.KEY_UNIT)));
                ingredient.setIngredient(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM_NAME)));

                ingredientList.add(ingredient);

            }while (cursor.moveToNext());
        }

        return ingredientList;
    }

    public void deleteShoppingItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_SHOPPING_LIST_NAME, Constants.KEY_INGREDIENT_ITEM_ID + " = ?",
                new String[] {String.valueOf(id)});

        db.close();

    }

    //Updated Shopping
    public int updateShoppingListItem(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_COUNT, ingredient.getCount());
        values.put(Constants.KEY_UNIT, ingredient.getUnit());
        values.put(Constants.KEY_ITEM_NAME, ingredient.getIngredient());//get system time

        //update row
        return db.update(Constants.TABLE_SHOPPING_LIST_NAME, values, Constants.KEY_INGREDIENT_ITEM_ID + "=?", new String[] { ingredient.getID()} );
    }


    public void saveRecipeCover(UserRecipeCover userRecipeCover) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Constants.KEY_COVER_IMAGE_URI, userRecipeCover.getImageUri());
        values.put(Constants.KEY_COVER_NAME, userRecipeCover.getCoverName());
        values.put(Constants.KEY_COVER_COOKING_TIME, userRecipeCover.getCookingTime());
        values.put(Constants.KEY_COVER_SERVING_SIZE, userRecipeCover.getServingSize());
        values.put(Constants.KEY_COVER_COMMENT, userRecipeCover.getComment());

        db.insert(Constants.TABLE_USER_RECIPE_COVER,null,values);
        db.close();

    }

    public void saveRecipeIngredients(ArrayList<UserRecipeIngredient> ingredientList, int coverID) {

        SQLiteDatabase db = this.getWritableDatabase();

        if(ingredientList != null){
            for(int i=0; i< ingredientList.size(); i++){

                ContentValues values = new ContentValues();

                values.put(Constants.KEY_USER_INGREDIENT_COVER_ID,coverID);
                values.put(Constants.KEY_USER_INGREDIENT_AMOUNT,ingredientList.get(i).getAmount());
                values.put(Constants.KEY_USER_INGREDIENT_UNIT,ingredientList.get(i).getUnit());
                values.put(Constants.KEY_USER_INGREDIENT_NAME, ingredientList.get(i).getName());

                db.insert(Constants.TABLE_USER_INGREDIENT, null,values);
            }
        }

        db.close();

    }

    public void saveRecipeSteps(ArrayList<UserRecipeStep> userRecipeStepList, int coverID) {

        SQLiteDatabase db = this.getWritableDatabase();

        if(userRecipeStepList != null){

            for(int i=0;i<userRecipeStepList.size();i++){

                ContentValues values = new ContentValues();
                values.put(Constants.KEY_USER_STEP_COVER_ID, coverID);
                values.put(Constants.KEY_USER_STEP_IMAGE_URI, userRecipeStepList.get(i).getImageUri());
                values.put(Constants.KEY_USER_STEP_TEXT, userRecipeStepList.get(i).getStepText());

                db.insert(Constants.TABLE_USER_STEP, null,values);
            }
        }

        db.close();

    }

    public int getUserRecipeCoverID(String coverName) {

        int coverID = -1;

        SQLiteDatabase db = this.getReadableDatabase();

        String whereClause = Constants.KEY_COVER_NAME+" = ?";

        Cursor cursor = db.query(Constants.TABLE_USER_RECIPE_COVER, new String[] {
                Constants.KEY_COVER_ID}, whereClause, new String[]{coverName}, null, null, null );

        if(cursor.moveToFirst()){
            coverID = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_COVER_ID)));
        }

        Log.d("Database",String.valueOf(coverID));

        return coverID;
    }

    public ArrayList<UserRecipeCover> getAllUserRecipeCovers() {

        ArrayList<UserRecipeCover> userRecipeCoversList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLE_USER_RECIPE_COVER, new String[]{Constants.KEY_COVER_ID,
        Constants.KEY_COVER_IMAGE_URI, Constants.KEY_COVER_NAME, Constants.KEY_COVER_COMMENT,
        Constants.KEY_COVER_COOKING_TIME, Constants.KEY_COVER_SERVING_SIZE},null,null,null,null,null);

        if(cursor.moveToFirst()){
            do {

                UserRecipeCover userRecipeCover = new UserRecipeCover();

                userRecipeCover.setCoverID(cursor.getString(cursor.getColumnIndex(Constants.KEY_COVER_ID)));
                userRecipeCover.setImageUri(cursor.getString(cursor.getColumnIndex(Constants.KEY_COVER_IMAGE_URI)));
                userRecipeCover.setCoverName(cursor.getString(cursor.getColumnIndex(Constants.KEY_COVER_NAME)));
                userRecipeCover.setCookingTime(cursor.getInt(cursor.getColumnIndex(Constants.KEY_COVER_COOKING_TIME)));
                userRecipeCover.setServingSize(cursor.getInt(cursor.getColumnIndex(Constants.KEY_COVER_SERVING_SIZE)));
                userRecipeCover.setComment(cursor.getString(cursor.getColumnIndex(Constants.KEY_COVER_COMMENT)));

                userRecipeCoversList.add(userRecipeCover);

            } while (cursor.moveToNext());
        }

        return userRecipeCoversList;

    }
}
