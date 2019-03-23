package com.luo.ming.delicipe.Models;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class Recipe {

    private String ID;
    private String title;
    private String imageLink;
    private String publisher;
    private String sourceURL;
    private ArrayList<String> ingredients;

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
    public ArrayList<String> getIngredients() { return ingredients; }
    public String getID() {
        return ID;
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

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = new ArrayList<>(ingredients);
    }

    public void setID(String id){
        this.ID = id;
    }


    public void getRecipeObj(String url,Context context,final VolleyCallBack callBack) {
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

                    ArrayList<String>ingredientList = new ArrayList<String>();
                    for(int j=0;j<jArray.length();j++){
                        ingredientList.add(jArray.getString(j));
                        Log.d("recipeModel",jArray.getString(j));
                    }

                    setIngredients(ingredientList);
                    Log.d("recipeModel",String.valueOf(getIngredients().size()));

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

    public interface VolleyCallBack {
        void onSuccess();
    }

}
