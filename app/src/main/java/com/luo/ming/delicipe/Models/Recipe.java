package com.luo.ming.delicipe.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.luo.ming.delicipe.Data.DatabaseHandler;
import com.luo.ming.delicipe.Helpers.Constants;
import com.luo.ming.delicipe.Helpers.VolleyCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class Recipe  {

    private String ID;
    private String title;
    private String imageLink;
    private String publisher;
    private String sourceURL;
    private int cookingTime;
    private int servings;
    private DatabaseHandler db;

    private ArrayList<Ingredient> ingredients;

    public Recipe(){}

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
        // Swapping http with https so image is found and displayed
        this.imageLink = imageLink.replace("http","https");
        if(this.imageLink.contains("httpss")){
            this.imageLink = this.imageLink.replace("httpss","https");
        }
    }
    public void setPublisher(String publisher){

        this.publisher = publisher;
    }
    public void setSourceURL(String sourceURL) {
        // Swapping http with https so web page is found and displayed
        this.sourceURL = sourceURL.replace("http","https");
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

    public void setServings() {
        this.servings = 4;
    }

    public void getRecipeObj(String url, Context context, final VolleyCallBack callBack) {
        Log.d("recipeModel","getRecipeObj called");


        Log.d("recipeModel",url);

        RequestQueue queue = Volley.newRequestQueue(context);

        final JsonObjectRequest request =
                new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                try{

                    Log.d("recipeModel","getRecipeObj jsonreuqestcalled");
                    JSONObject recipeObj = response.getJSONObject("recipe");
                    setImageLink(recipeObj.getString("image_url"));
                    Log.d("recipeModel",getImageLink());
                    setTitle(recipeObj.getString("title"));
                    Log.d("recipeModel",getTitle());
                    setPublisher(recipeObj.getString("publisher"));
                    Log.d("recipeModel",getPublisher());
                    setSourceURL("source_url");
                    Log.d("recipeModel",getSourceURL());

                    JSONArray jArray = recipeObj.getJSONArray("ingredients");

                    ArrayList<Ingredient>ingredientList = new ArrayList<Ingredient>();
                    Ingredient newIngredient = new Ingredient();
                    for(int j=0;j<jArray.length();j++){
                        if(!Character.isUpperCase(jArray.getString(j).toCharArray()[0])&&!jArray.getString(j).contains("___")){

                            newIngredient= newIngredient.parseIngredient(jArray.getString(j));
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

    public void addIngredientToDB(Context context,ArrayList<Ingredient>ingredients){
        db = new DatabaseHandler(context);
        db.addIngredient(ingredients);
    }





}
