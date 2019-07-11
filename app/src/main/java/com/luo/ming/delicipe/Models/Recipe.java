package com.luo.ming.delicipe.Models;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.luo.ming.delicipe.Data.DatabaseHandler;
import com.luo.ming.delicipe.Helpers.DelicipeApplication;
import com.luo.ming.delicipe.Helpers.VolleyCallBack;
import com.luo.ming.delicipe.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;


public class Recipe implements Serializable {

    private String ID;
    private String title;
    private String imageLink;
    private String publisher;
    private String sourceURL;
    private int cookingTime;
    private int servings;

    private ArrayList<Ingredient> ingredients;

    public Recipe(){this.servings=4;}

    // Getters
    public String getTitle() {
        return title;
    }
    public String getImageLink() {
        return imageLink;
    }
    public String getPublisher() {
        return publisher;
    }
    public String getSourceURL() {
        return sourceURL;
    }
    public ArrayList<Ingredient> getIngredients() { return ingredients; }
    public String getID() {
        return ID;
    }
    public int getCookingTime() {
        return cookingTime;
    }
    public int getServings() {
        return servings;
    }

    // Setters
    public void setTitle(String title){

        this.title = title;
    }

    public void setImageLink(String imageLink){
        this.imageLink = getOnlineUrl(imageLink);
    }

    public static String getOnlineUrl(String imageLink){
        String imageUrl = imageLink.replace("http","https");
        if(imageUrl.contains("httpss")){
            imageUrl = imageUrl.replace("httpss","https");
            return imageUrl;
        }
        return imageUrl;
    }

    public void setPublisher(String publisher){

        this.publisher = publisher;
    }
    public void setSourceURL(String sourceURL) {
        // Swapping http with https so image is found and displayed
        this.sourceURL = sourceURL.replace("http","https");
        if(this.sourceURL.contains("httpss")){
            this.sourceURL = this.sourceURL.replace("httpss","https");
        }
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = new ArrayList<>(ingredients);
    }

    public void setID(String id){

        this.ID = id;
    }

    public void setCookingTime( ) {
        int numberOfIngredients = ingredients.size();
        int periods = (int)(Math.ceil(numberOfIngredients/3));
        this.cookingTime = 15*periods;
    }

    public void setCookingTime(int cookingTime){
        this.cookingTime = cookingTime;
    }

    public void setServings() {
        this.servings = 4;
    }

    public void setServings (int size){
        servings = size;
    }

    public void getRecipeObj(String url, final VolleyCallBack callBack) {
        Log.d("recipeModel","getRecipeObj called");

        RequestQueue queue = Volley.newRequestQueue(DelicipeApplication.getAppContext());

        List<Recipe> recipeList = new ArrayList<>();

        final JsonObjectRequest request =
                new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                try{

                    //TODO CONSIDER USING GSON
                    Log.d("recipeModel","getRecipeObj jsonreuqestcalled");
                    JSONObject recipeObj = response.getJSONObject("recipe");
                    setImageLink(recipeObj.getString("image_url"));
                    Log.d("recipeModel",getImageLink());
                    setTitle(recipeObj.getString("title"));
                    Log.d("recipeModel",getTitle());
                    setPublisher(recipeObj.getString("publisher"));
                    Log.d("recipeModel",getPublisher());
                    setSourceURL(recipeObj.getString("source_url"));
                    Log.d("recipeModel",getSourceURL());
                    setID(recipeObj.getString("recipe_id"));

                    JSONArray jArray = recipeObj.getJSONArray("ingredients");

                    ArrayList<Ingredient>ingredientList = new ArrayList<Ingredient>();

                    for(int j=0;j<jArray.length();j++){
                        if(!Character.isUpperCase(jArray.getString(j).toCharArray()[0])&&!jArray.getString(j).contains("___")){
                            Ingredient newIngredient = new Ingredient();
                            newIngredient.setIngredientItem(jArray.getString(j));
                            ingredientList.add(newIngredient);
                            Log.d("recipeModel",jArray.getString(j));

                        }

                    }

                    setIngredients(ingredientList);
                    Log.d("recipeModel",String.valueOf(getIngredients().size()));

                    setCookingTime();
                    setServings();

                    callBack.onSuccess();

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("recipeModel", "Error occurred ", error);

            }
        });

        queue.add(request);
        Log.d("recipeModel","request added ");

    }

    //get a list of recipes from the API server based on user input
    public static List<Recipe> getRecipesFromAPI(String url, final VolleyCallBack callBack) {
        Log.d("getrecipe","getrecipecalled");

        final ArrayList<Recipe>recipeList = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(DelicipeApplication.getAppContext());


        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                Log.d("recipe onresponse","onresponse has been called");
                try{

                    Log.d("jsonrequest","jsonreuqestcalled");
                    JSONArray recipesArray = response.getJSONArray("recipes");

                    Log.d("recipesArray",recipesArray.toString());
                    for (int i = 0; i < recipesArray.length(); i++) {

                        JSONObject recipeObj = recipesArray.getJSONObject(i);

                        Recipe recipe = new Recipe();
                        recipe.setImageLink(recipeObj.getString("image_url"));
                        recipe.setTitle(recipeObj.getString("title"));
                        Log.d("titile",recipe.getTitle());
                        Log.d("imageLink",recipe.getImageLink());
                        recipe.setPublisher(recipeObj.getString("publisher"));
                        recipe.setID(recipeObj.getString("recipe_id"));
                        recipe.setSourceURL(recipeObj.getString("source_url"));
                        recipeList.add(recipe);
                        Log.d("request",String.valueOf(recipeList.size()));

                    }

                    if(!recipeList.isEmpty()){
                        callBack.onSuccess();
                    } else {
                        callBack.onFailure();
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                // TODO Auto-generated method stub

            }
        });


        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", "Error occurred ", error);
            }
        };

        queue.add(request);

        return recipeList;

    }

    public void addIngredientToDB(ArrayList<Ingredient>ingredients){
        DatabaseHandler db = DatabaseHandler.getDataBase();
        db.addIngredients(ingredients);
    }

    public ArrayList<Ingredient> updateIngredientCount(int newServing){

        if(ingredients.isEmpty()){
            Log.d("Recipe","ingredient list is empty when updating count");
        }
        for(int i=0;i<ingredients.size();i++){

            Log.d("Recipe",ingredients.get(i).getIngredient());
            double count = ingredients.get(i).getCount();
            Log.d("Recipe count",String.valueOf(count));
            Log.d("Recipe newserving",String.valueOf(newServing));
            count = count*((double) newServing/servings);
            Log.d("Recipe new count",String.valueOf(count));
            count=(double)Math.round(count * 100d) / 100d;
            ingredients.get(i).setCount(count);

            Log.d("Recipe","");

        }

        this.servings = newServing;
        return ingredients;

    }



    public static ArrayList<Recipe> getFavouriteRecipesFromDB() {

        DatabaseHandler db = DatabaseHandler.getDataBase();

        return db.getFavouriteRecipes();

    }

    public static JSONArray loadRecommendedRecipeJsonArray(){

        Resources resources = DelicipeApplication.getAppContext().getResources();
        StringBuilder stringBuilder = new StringBuilder();
        InputStream in = resources.openRawResource(R.raw.recipes);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

        String line;
        try{
            while((line = bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            return jsonObject.getJSONArray("recipes");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }




    public Boolean checkIfRecipeSaved() {

        DatabaseHandler db = DatabaseHandler.getDataBase();
        return db.checkIfRecipeSaved(this.ID);

    }

    public void saveFavouriteRecipeToDB(){
        DatabaseHandler db = DatabaseHandler.getDataBase();
        db.saveFavouriteRecipe(this);
    }

    public void unsaveFavouriteRecipetoDB() {

        DatabaseHandler db = DatabaseHandler.getDataBase();
        db.unsaveFavouriteRecipe(this.ID);
    }
}
