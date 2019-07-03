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
import com.luo.ming.delicipe.Models.Recipe;
import com.luo.ming.delicipe.Models.UserRecipe;
import com.luo.ming.delicipe.Models.UserRecipeCover;
import com.luo.ming.delicipe.Models.UserRecipeIngredient;
import com.luo.ming.delicipe.Models.UserRecipeStep;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        // create shopping list table from ingredient list
        String CREATE_SHOPPINGLIST_TABLE ="CREATE TABLE "
                + Constants.TABLE_SHOPPING_LIST_NAME + "("
                + Constants.KEY_INGREDIENT_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Constants.KEY_ITEM_NAME + " TEXT"+ " );";

        String CREATE_USER_COVER_TABLE = "CREATE TABLE " + Constants.TABLE_USER_RECIPE_COVER + "("
                + Constants.KEY_COVER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Constants.KEY_COVER_IMAGE_BYTES + " BLOB,"
                + Constants.KEY_COVER_NAME + " TEXT NOT NULL,"
                + Constants.KEY_COVER_COOKING_TIME + " INTEGER,"
                + Constants.KEY_COVER_SERVING_SIZE + " INTEGER,"
                + Constants.KEY_COVER_COMMENT + " TEXT" +");";

        String CREATE_USER_INGREDIENT_TABLE = "CREATE TABLE "
                + Constants.TABLE_USER_INGREDIENT + "("
                + Constants.KEY_USER_INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Constants.KEY_USER_INGREDIENT_NAME + " TEXT,"
                + Constants.KEY_USER_INGREDIENT_COVER_ID + " INTEGER,"
                + " FOREIGN KEY (" + Constants.KEY_USER_INGREDIENT_COVER_ID + ") REFERENCES "
                + Constants.TABLE_USER_RECIPE_COVER
                + " (" + Constants.KEY_COVER_ID + "));";

        String CREATE_USER_COOKING_STEP_TABLE = "CREATE TABLE "
                + Constants.TABLE_USER_STEP + "("
                + Constants.KEY_USER_STEP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Constants.KEY_USER_STEP_IMAGE_BYTES + " BLOB,"
                + Constants.KEY_USER_STEP_TEXT + " TEXT,"
                + Constants.KEY_USER_STEP_COVER_ID + " INTEGER, "
                + " FOREIGN KEY (" + Constants.KEY_USER_STEP_COVER_ID + ") REFERENCES "
                + Constants.TABLE_USER_RECIPE_COVER
                + " (" + Constants.KEY_COVER_ID + "));";

        String CREATE_FAVOURITE_RECIPE_TABLE = "CREATE TABLE " + Constants.TABLE_FAVOURITE_RECIPE + "("
                + Constants.KEY_FAVOURITE_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Constants.KEY_FAVOURITE_RECIPE_IMAGE + " TEXT, "
                + Constants.KEY_FAVOURITE_RECIPE_ID_API + " TEXT NOT NULL, "
                + Constants.KEY_FAVOURITE_RECIPE_NAME + " TEXT, "
                + Constants.KEY_FAVOURITE_RECIPE_SERVING + " INTEGER, "
                + Constants.KEY_FAVOURITE_RECIPE_SOURCE_URL + " TEXT, "
                + Constants.KEY_FAVOURITE_RECIPE_PUBLISHER + " TEXT, "
                + Constants.KEY_FAVOURITE_RECIPE_CREATED_TIME + " DATE, "
                + Constants.KEY_FAVOURITE_RECIPE_COOKING_TIME + " INTEGER " + ");";

        String CREATE_FAVOURITE_RECIPE_INGREDIENTS_TABLE = "CREATE TABLE "
                + Constants.TABLE_FAVOURITE_RECIPE_INGREDIENTS + "("
                + Constants.KEY_FAVOURITE_RECIPE_INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Constants.KEY_FAVOURITE_RECIPE_INGREDIENT_NAME + " TEXT, "
                + Constants.KEY_FAVOURITE_RECIPE_INGREDIENT_ID_API + " TEXT NOT NULL "
                + ");";

        db.execSQL(CREATE_SHOPPINGLIST_TABLE);
        db.execSQL(CREATE_USER_COVER_TABLE);
        db.execSQL(CREATE_USER_INGREDIENT_TABLE);
        db.execSQL(CREATE_USER_COOKING_STEP_TABLE);
        db.execSQL(CREATE_FAVOURITE_RECIPE_TABLE);
        db.execSQL(CREATE_FAVOURITE_RECIPE_INGREDIENTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_SHOPPING_LIST_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_USER_RECIPE_COVER);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_USER_INGREDIENT);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_USER_STEP);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_FAVOURITE_RECIPE);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_FAVOURITE_RECIPE_INGREDIENTS);
        onCreate(db);

    }

    public void addIngredients(ArrayList<Ingredient>ingredients) {

        SQLiteDatabase db = this.getWritableDatabase();
        if (ingredients != null) {
            for (int i = 0; i < ingredients.size(); i++) {
                ContentValues values = new ContentValues();
                values.put(Constants.KEY_ITEM_NAME,ingredients.get(i).getIngredientItem());
                db.insert(Constants.TABLE_SHOPPING_LIST_NAME, null, values);
            }
        }
    }

    public ArrayList<Ingredient> getAllIngredients() {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Ingredient> ingredientList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_SHOPPING_LIST_NAME, new String[] {
               Constants.KEY_INGREDIENT_ITEM_ID,Constants.KEY_ITEM_NAME}, null, null, null, null, null );

        if (cursor.moveToFirst()) {
            do {
                Ingredient ingredient = new Ingredient();
                ingredient.setIngredientItemfromDB(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM_NAME)));
                ingredient.setID(cursor.getInt(cursor.getColumnIndex(Constants.KEY_INGREDIENT_ITEM_ID)));
                ingredientList.add(ingredient);

            }while (cursor.moveToNext());
        }

        return ingredientList;
    }


//
//    //Updated Shopping
//    public int updateShoppingListItem(Ingredient ingredient) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(Constants.KEY_COUNT, ingredient.getCount());
//        values.put(Constants.KEY_UNIT, ingredient.getUnit());
//        values.put(Constants.KEY_ITEM_NAME, ingredient.getIngredient());//get system time
//
//        //update row
//        return db.update(Constants.TABLE_SHOPPING_LIST_NAME, values, Constants.KEY_INGREDIENT_ITEM_ID + "=?", new String[] { ingredient.getID()} );
//    }


    public void saveRecipeCover(UserRecipeCover userRecipeCover) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Constants.KEY_COVER_IMAGE_BYTES, userRecipeCover.getImageBytes());
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
                values.put(Constants.KEY_USER_STEP_IMAGE_BYTES, userRecipeStepList.get(i).getImageBytes());
                values.put(Constants.KEY_USER_STEP_TEXT, userRecipeStepList.get(i).getStepText());

                db.insert(Constants.TABLE_USER_STEP, null,values);
            }
        }

        db.close();

    }

    public void saveFavouriteRecipe(Recipe recipe) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues recipeValues = new ContentValues();
        recipeValues.put(Constants.KEY_FAVOURITE_RECIPE_IMAGE, recipe.getImageLink());
        recipeValues.put(Constants.KEY_FAVOURITE_RECIPE_NAME, recipe.getTitle());
        recipeValues.put(Constants.KEY_FAVOURITE_RECIPE_COOKING_TIME, recipe.getCookingTime());
        recipeValues.put(Constants.KEY_FAVOURITE_RECIPE_SERVING,recipe.getServings());
        recipeValues.put(Constants.KEY_FAVOURITE_RECIPE_SOURCE_URL,recipe.getSourceURL());
        recipeValues.put(Constants.KEY_FAVOURITE_RECIPE_ID_API, recipe.getID());
        recipeValues.put(Constants.KEY_FAVOURITE_RECIPE_PUBLISHER, recipe.getPublisher());
        recipeValues.put(Constants.KEY_FAVOURITE_RECIPE_CREATED_TIME,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        db.insert(Constants.TABLE_FAVOURITE_RECIPE,null,recipeValues);

        ArrayList<Ingredient> ingredients = recipe.getIngredients();

        if(ingredients != null){

            for(int i=0;i<ingredients.size();i++){

                ContentValues ingredientValues = new ContentValues();
                ingredientValues.put(Constants.KEY_FAVOURITE_RECIPE_INGREDIENT_NAME, ingredients.get(i).getIngredientItem());
                ingredientValues.put(Constants.KEY_FAVOURITE_RECIPE_INGREDIENT_ID_API,recipe.getID());
                db.insert(Constants.TABLE_FAVOURITE_RECIPE_INGREDIENTS,null,ingredientValues);
            }
        }

        db.close();


    }

    public ArrayList<Recipe> getFavouriteRecipes() {

        ArrayList<Recipe>recipeList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLE_FAVOURITE_RECIPE, new String[]{Constants.KEY_FAVOURITE_RECIPE_ID,
                Constants.KEY_FAVOURITE_RECIPE_ID_API, Constants.KEY_FAVOURITE_RECIPE_NAME, Constants.KEY_FAVOURITE_RECIPE_IMAGE,
                Constants.KEY_FAVOURITE_RECIPE_SOURCE_URL, Constants.KEY_FAVOURITE_RECIPE_COOKING_TIME,
                Constants.KEY_FAVOURITE_RECIPE_SERVING, Constants.KEY_FAVOURITE_RECIPE_PUBLISHER}, null,null,null,null,Constants.KEY_FAVOURITE_RECIPE_CREATED_TIME+ " DESC");

        if(cursor.moveToFirst()){

            do{

                Recipe recipe = new Recipe();

                recipe.setID(cursor.getString(cursor.getColumnIndex(Constants.KEY_FAVOURITE_RECIPE_ID_API)));
                recipe.setSourceURL(cursor.getString(cursor.getColumnIndex(Constants.KEY_FAVOURITE_RECIPE_SOURCE_URL)));
                recipe.setCookingTime(cursor.getInt(cursor.getColumnIndex(Constants.KEY_FAVOURITE_RECIPE_COOKING_TIME)));
                recipe.setImageLink(cursor.getString(cursor.getColumnIndex(Constants.KEY_FAVOURITE_RECIPE_IMAGE)));
                recipe.setIngredients(getIngredientsForRecipe(recipe.getID()));
                recipe.setPublisher(cursor.getString(cursor.getColumnIndex(Constants.KEY_FAVOURITE_RECIPE_PUBLISHER)));
                recipe.setServings(cursor.getInt(cursor.getColumnIndex(Constants.KEY_FAVOURITE_RECIPE_SERVING)));
                recipe.setTitle(cursor.getString(cursor.getColumnIndex(Constants.KEY_FAVOURITE_RECIPE_NAME)));
                recipeList.add(recipe);


            }while(cursor.moveToNext());
        }


        db.close();
        return recipeList;

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

        db.close();
        return coverID;
    }

    public ArrayList<UserRecipeCover> getAllUserRecipeCovers() {

        ArrayList<UserRecipeCover> userRecipeCoversList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLE_USER_RECIPE_COVER, new String[]{Constants.KEY_COVER_ID,
        Constants.KEY_COVER_IMAGE_BYTES, Constants.KEY_COVER_NAME, Constants.KEY_COVER_COMMENT,
        Constants.KEY_COVER_COOKING_TIME, Constants.KEY_COVER_SERVING_SIZE},null,null,null,null,null);

        if(cursor.moveToFirst()){
            do {

                UserRecipeCover userRecipeCover = new UserRecipeCover();

                userRecipeCover.setCoverID(cursor.getString(cursor.getColumnIndex(Constants.KEY_COVER_ID)));
                userRecipeCover.setImageBytes(cursor.getBlob(1));
                userRecipeCover.setCoverName(cursor.getString(cursor.getColumnIndex(Constants.KEY_COVER_NAME)));
                userRecipeCover.setCookingTime(cursor.getInt(cursor.getColumnIndex(Constants.KEY_COVER_COOKING_TIME)));
                userRecipeCover.setServingSize(cursor.getInt(cursor.getColumnIndex(Constants.KEY_COVER_SERVING_SIZE)));
                userRecipeCover.setComment(cursor.getString(cursor.getColumnIndex(Constants.KEY_COVER_COMMENT)));

                userRecipeCoversList.add(userRecipeCover);

            } while (cursor.moveToNext());
        }

        db.close();
        return userRecipeCoversList;

    }




    public ArrayList<Ingredient> getIngredientsForRecipe(String id){

        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Ingredient> ingredients = new ArrayList<>();

        String whereClause = Constants.KEY_FAVOURITE_RECIPE_INGREDIENT_ID_API+" = ?";

        Cursor cursor = db.query(Constants.TABLE_FAVOURITE_RECIPE_INGREDIENTS, new String[]{
                Constants.KEY_FAVOURITE_RECIPE_INGREDIENT_NAME} , whereClause, new String[]{id}, null,null,null);

        if(cursor.moveToFirst()){

            do{

                Ingredient ingredient = new Ingredient();
                ingredient.setIngredientItem(cursor.getString(cursor.getColumnIndex(Constants.KEY_FAVOURITE_RECIPE_INGREDIENT_NAME)));
                ingredients.add(ingredient);

            }while(cursor.moveToNext());
        }

        db.close();

        return ingredients;

    }

    public Boolean checkIfRecipeSaved(String id) {

        SQLiteDatabase db = getReadableDatabase();

        String whereClause = Constants.KEY_FAVOURITE_RECIPE_ID_API+" = ?";
        Cursor cursor = db.query(Constants.TABLE_FAVOURITE_RECIPE, null, whereClause, new String[]{id}, null, null, null);

        if(cursor.moveToFirst()){
            db.close();
            return true;
        }
        db.close();
        return false;
    }

    public void deleteShoppingItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_SHOPPING_LIST_NAME, Constants.KEY_INGREDIENT_ITEM_ID + " = ?",
                new String[] {String.valueOf(id)});

        db.close();

    }

    public void unsaveFavouriteRecipe(String id){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_FAVOURITE_RECIPE, Constants.KEY_FAVOURITE_RECIPE_ID_API + " = ?",
                new String[]{id});
        db.delete(Constants.TABLE_FAVOURITE_RECIPE_INGREDIENTS, Constants.KEY_FAVOURITE_RECIPE_INGREDIENT_ID_API
        + " = ?", new String[]{id});
        db.close();

    }


    public void updateShoppingListItem(Ingredient ingredient) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_ITEM_NAME, ingredient.getIngredientItem());
        db.update(Constants.TABLE_SHOPPING_LIST_NAME, values, Constants.KEY_INGREDIENT_ITEM_ID +
                " = ?",new String[]{String.valueOf(ingredient.getID())});
        db.close();

    }

    public Boolean checkIfUserRecipeNameExists(Context context, String coverName) {

        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = Constants.KEY_COVER_NAME+" = ?";

        Cursor cursor = db.query(Constants.TABLE_USER_RECIPE_COVER, new String[]{
                Constants.KEY_COVER_NAME} , whereClause, new String[]{coverName}, null,null,null);

        if(!cursor.moveToFirst()){

            db.close();
            return false;
        }

        db.close();
        return true;
    }

    public ArrayList<UserRecipeIngredient> getRecipeIngredientsWithCoverId(String coverID) {

        ArrayList<UserRecipeIngredient>userIngredientsList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = Constants.KEY_USER_INGREDIENT_COVER_ID+" = ?";

        Cursor cursor = db.query(Constants.TABLE_USER_INGREDIENT, new String[]{
                Constants.KEY_USER_INGREDIENT_NAME} , whereClause, new String[]{coverID}, null,null,null);

        if(cursor.moveToFirst()){

            do{

                UserRecipeIngredient ingredient = new UserRecipeIngredient();
                ingredient.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_USER_INGREDIENT_NAME)));
                userIngredientsList.add(ingredient);

            }while(cursor.moveToNext());
        }

        db.close();

        return userIngredientsList;

    }

    public ArrayList<UserRecipeStep> getRecipeStepsWithCoverId(String coverID) {

        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<UserRecipeStep>userRecipeStepList = new ArrayList<>();

        String whereClause = Constants.KEY_USER_STEP_COVER_ID+" = ?";

        Cursor cursor = db.query(Constants.TABLE_USER_STEP, new String[]{
                Constants.KEY_USER_STEP_TEXT, Constants.KEY_USER_STEP_IMAGE_BYTES} , whereClause, new String[]{coverID}, null,null,null);

        if(cursor.moveToFirst()){

            do{

                UserRecipeStep userRecipeStep = new UserRecipeStep();
                userRecipeStep.setStepText(cursor.getString(cursor.getColumnIndex(Constants.KEY_USER_STEP_TEXT)));
                userRecipeStep.setImageBytes(cursor.getBlob(1));
                userRecipeStepList.add(userRecipeStep);

            }while(cursor.moveToNext());
        }

        db.close();


        return userRecipeStepList;
    }

    public UserRecipeCover getRecipeCoverWithId(String recipeID) {

        SQLiteDatabase db = this.getWritableDatabase();
        UserRecipeCover userRecipeCover = new UserRecipeCover();

        String whereClause = Constants.KEY_COVER_ID+" = ?";

        Cursor cursor = db.query(Constants.TABLE_USER_RECIPE_COVER, new String[]{Constants.KEY_COVER_ID,
                Constants.KEY_COVER_IMAGE_BYTES, Constants.KEY_COVER_NAME, Constants.KEY_COVER_COMMENT,
                Constants.KEY_COVER_COOKING_TIME, Constants.KEY_COVER_SERVING_SIZE},whereClause,new String[]{recipeID},null,null,null);


        cursor.moveToFirst();

       userRecipeCover.setCoverID(cursor.getString(cursor.getColumnIndex(Constants.KEY_COVER_ID)));
        userRecipeCover.setImageBytes(cursor.getBlob(1));
        userRecipeCover.setCoverName(cursor.getString(cursor.getColumnIndex(Constants.KEY_COVER_NAME)));
        userRecipeCover.setCookingTime(cursor.getInt(cursor.getColumnIndex(Constants.KEY_COVER_COOKING_TIME)));
        userRecipeCover.setServingSize(cursor.getInt(cursor.getColumnIndex(Constants.KEY_COVER_SERVING_SIZE)));
        userRecipeCover.setComment(cursor.getString(cursor.getColumnIndex(Constants.KEY_COVER_COMMENT)));

        return userRecipeCover;

    }
}
