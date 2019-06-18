package com.luo.ming.delicipe.Presenters;

import android.os.AsyncTask;
import android.util.Log;
import android.content.Context;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import com.luo.ming.delicipe.Helpers.VolleyCallBack;
import com.luo.ming.delicipe.Models.Ingredient;
import com.luo.ming.delicipe.Models.Recipe;

public class ScrollingActivityPresenter {

    private String url;
    private View view;
    private Recipe recipe;
    private RequestQueue queue;
    private Context context;
    private ArrayList<Ingredient>ingredients;



    private final String baseUrl = "https://www.food2fork.com/api/get?key=";
    private final String key = "f5b73a553a6a92ccfabca695807bdaeb";//


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

    public ScrollingActivityPresenter(View view, Context context, Recipe recipe){

        this.view = view;
        this.context = context;
        this.recipe = recipe;
        ingredients = recipe.getIngredients();


    }

    public void saveFavouriteRecipe(){

        new saveUnsaveFavouriteRecipeTask(recipe,context,true).execute();
    }

    public void unSaveFavouriteRecipe() {
        new saveUnsaveFavouriteRecipeTask(recipe,context,false).execute();
    }

    public static class saveUnsaveFavouriteRecipeTask extends AsyncTask<Void,Void,Void>{

        private Recipe recipe;
        private Context context;
        private Boolean save;

        public saveUnsaveFavouriteRecipeTask(Recipe recipe, Context context, Boolean save){

            this.recipe = recipe;
            this.context = context;
            this.save = save;

        }

        @Override
        protected Void doInBackground(Void... voids) {

            if(save){
                recipe.saveFavouriteRecipeToDB(context);
                return null;
            }

            recipe.unsaveFavouriteRecipetoDB(context);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


        }
    }


    public void getRecipe(){
        recipe.getRecipeObj(url, context, new VolleyCallBack() {
            @Override
            public void onSuccess() {
                getRecipePhoto();
                getTableLayout();
                displayRecipeTitle();
                displayFavBtnForAPIRecipes();

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

    public void displayFavBtnForAPIRecipes(){

       Boolean bool = recipe.checkIfRecipeSaved(context);
        view.displayFavouriteButton(bool);
    }

    public void displayFavouriteButton(){

        new DisplayFavouriteButtonState(context, view,recipe).execute();

    }

    private static class DisplayFavouriteButtonState extends AsyncTask<Void,Void,Void>{

        Boolean bool;
        Context context;
        View view;
        Recipe recipe;

        public DisplayFavouriteButtonState( Context context, View view, Recipe recipe) {
            this.context = context;
            this.view = view;
            this.recipe = recipe;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            bool = recipe.checkIfRecipeSaved(context);
            view.displayFavouriteButton(bool);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
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
        void displayFavouriteButton(Boolean bool);


    }



}
