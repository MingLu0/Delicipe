package com.luo.ming.delicipe.Presenters;

import android.os.AsyncTask;
import android.util.Log;
import android.content.Context;
import java.util.List;

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

import com.luo.ming.delicipe.Helpers.VolleyCallBack;
import com.luo.ming.delicipe.Models.Ingredient;
import com.luo.ming.delicipe.Models.Recipe;
import com.luo.ming.delicipe.R;

public class ScrollingActivityPresenter {

    private String url;
    private View view;
    private Recipe recipe;
    private RequestQueue queue;
    private Context context;
    private ArrayList<Ingredient>ingredients;



    private final String baseUrl = "https://www.food2fork.com/api/get?key=";
    //private final String key = "4d78d05d9f20215c272d04f6974c04db"; //50 calls limit per day
    private final String key = "f5b73a553a6a92ccfabca695807bdaeb";//50 calls limit per day
    // private final String key = "bba82bc3b0c0d5036c7d521014b02b62";//50 calls limit per day
    // private final String key = "2066d15049b02e6f8ea0b11f77f9fd30";//50 calls limit per day
    //private final String key = "3092e7c11f93c302283e456ed92207e4"; //50 calls limit per day

    //set the url using the base url and the user input keyword
    public void setUrl(String id) {

        url = baseUrl+key+"&rId="+id;
        Log.d("scrollingpresenter",url);

    }

    public ScrollingActivityPresenter(View view, Context context){

        this.view = view;
        this.context = context;
        recipe = new Recipe();
        ingredients = new ArrayList<>();


    }

    public void saveFavouriteRecipe(){

        new saveFavouriteRecipeTask(recipe,context).execute();
    }

    public static class saveFavouriteRecipeTask extends AsyncTask<Void,Void,Void>{

        private Recipe recipe;
        private Context context;

        public saveFavouriteRecipeTask(Recipe recipe, Context context) {

            this.recipe = recipe;
            this.context = context;

        }

        @Override
        protected Void doInBackground(Void... voids) {

            recipe.saveFavouriteRecipeToDB(context);

            return null;
        }
    }


    public void getRecipe(){
        recipe.getRecipeObj(url, context, new VolleyCallBack() {
            @Override
            public void onSuccess() {
                getRecipePhoto();
                getTableLayout();
                displayRecipeTitle();

            }

            @Override
            public void onFailure() {

            }
        });
    }

    public void getRecipePhoto(){

        String imageLink = recipe.getImageLink();

        view.displayRecipePhoto(imageLink);

    }

    public void displayRecipeTitle(){
        String recipeTitle = recipe.getTitle();
        view.displayRecipeTitle(recipeTitle);
    }

    public void getTableLayout(){

        ingredients = recipe.getIngredients();
        view.displayTableLayout(ingredients);

    }

    public void saveAllIngredients(){

        recipe.addIngredientToDB(context,ingredients);
        view.popupToast("All Ingredients have been saved!");

    }

    public void updateIngredientCount(int newServing){

        ingredients=recipe.updateIngredientCount(newServing);
        view.displayTableLayout(ingredients);

    }

    public void updateServing(int newServing) {
        view.updateServingSize(newServing);
    }


    public interface View {

        void displayRecipePhoto(String imageLink);
        void displayTableLayout(ArrayList<Ingredient>ingredients);
        void popupToast(String text);
        void updateCountInTableLayout(ArrayList<Ingredient> ingredients);
        void updateServingSize(int newSergving);
        void displayRecipeTitle(String recipeTitle);


    }



}
