package com.luo.ming.delicipe.Presenters;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.content.Context;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import com.luo.ming.delicipe.Helpers.BitmapUtility;
import com.luo.ming.delicipe.Helpers.VolleyCallBack;
import com.luo.ming.delicipe.Models.Ingredient;
import com.luo.ming.delicipe.Models.Recipe;
import com.luo.ming.delicipe.Models.UserRecipe;
import com.luo.ming.delicipe.Models.UserRecipeCover;
import com.luo.ming.delicipe.Models.UserRecipeIngredient;
import com.luo.ming.delicipe.Models.UserRecipeStep;

public class ScrollingActivityPresenter {

    private String url;
    private View view;
    private Recipe recipe;
    private UserRecipe userRecipe;
    private UserRecipeCover userRecipeCover;
    private String recipeID;
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

    public ScrollingActivityPresenter(View view, Context context, String recipeID){
        this.view = view;
        this.context =  context;
        this.recipeID = recipeID;
        userRecipe = new UserRecipe();
        userRecipe = userRecipe.getUserRecipeWithID(recipeID,context);
    }

    public ScrollingActivityPresenter(View view, Context context, UserRecipeCover userRecipeCover){
        this.view = view;
        this.context = context;
        this.userRecipeCover = userRecipeCover;


    }

    public void saveFavouriteRecipe(){

        new saveUnsaveFavouriteRecipeTask(recipe,context,true).execute();
    }

    public void unSaveFavouriteRecipe() {
        new saveUnsaveFavouriteRecipeTask(recipe,context,false).execute();
    }

    public void displayUserRecipe() {
        displayUserRecipePhoto();
        displayUserRecipeTitle();
        displayComment();
        displayUserRecipeServing();
        displayCookingTime(userRecipe.getUserRecipeCover().getCookingTime());
        displayUserIngredients();
        view.setIconVisibility(android.view.View.VISIBLE,android.view.View.INVISIBLE,android.view.View.INVISIBLE);
        displayUserSteps();
    }

    private void displayComment() {
        String comment = userRecipe.getUserRecipeCover().getComment();
        view.displayComment(comment);
    }

    private void displayUserIngredients() {
        ArrayList<UserRecipeIngredient>ingredients = userRecipe.getIngredientList();
        view.displayUserIngredients(ingredients);
    }
    
    private void displayUserSteps(){
        ArrayList<UserRecipeStep>userRecipeSteps = userRecipe.getUserRecipeStepList();
        view.displayCookingSteps(userRecipeSteps);
    }

    private void displayCookingTime(int cookingTime) {

        view.displayCookingTime(cookingTime);
    }

    private void displayUserRecipeServing() {
        int size = userRecipe.getUserRecipeCover().getServingSize();
        view.displayServingSize(size);
    }

    public void displayFavouriteRecipe() {
        view.setIconVisibility(android.view.View.INVISIBLE,android.view.View.VISIBLE,android.view.View.VISIBLE);
        displayRecipeTitle();
        getRecipePhoto();
        displayCookingTime(recipe.getCookingTime());
        displayIngredientsTableLayout();
        displayFavouriteButton();
    }

    public void saveSingleIngredient(String item) {
        Ingredient ingredient = new Ingredient();
        ingredient.addShoppingItemToDB(item, context);
    }

    public void unsaveSingleIngredient(String item) {
        Ingredient ingredient = new Ingredient();
        ingredient.deleteShoppingItemFromDBByName(item,context);
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


    public void displayOnlineRecipe(){
        view.setIconVisibility(android.view.View.INVISIBLE,android.view.View.VISIBLE,android.view.View.VISIBLE);

        recipe.getRecipeObj(url, context, new VolleyCallBack() {
            @Override
            public void onSuccess() {
                getRecipePhoto();
                displayIngredientsTableLayout();
                displayRecipeTitle();
                displayFavBtnForAPIRecipes();
                displayCookingTime(recipe.getCookingTime());
                displayCartButton();

            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void displayCartButton() {
        view.displayShoppingCartButton();
    }

    public void getRecipePhoto(){

        String imageLink = recipe.getImageLink();

        view.displayRecipePhoto(imageLink);

    }

    public void displayUserRecipePhoto(){
        Bitmap bitmap = BitmapUtility.covertBytesToBitmap(userRecipe.getUserRecipeCover().getImageBytes());
        view.displayRecipePhotoFromBitmap(bitmap);
    }

    public void displayRecipeTitle(){
        String recipeTitle = recipe.getTitle();
        view.displayRecipeTitle(recipeTitle);
    }

    public void displayUserRecipeTitle(){
        String userRecipeTitle = userRecipe.getUserRecipeCover().getCoverName();
        view.displayRecipeTitle(userRecipeTitle);
    }

    public void displayIngredientsTableLayout(){

        ingredients = recipe.getIngredients();
        view.displayOnlineIngredients(ingredients);

    }

    public void saveAllIngredients(){

        if(this.recipe!=null){
            recipe.addIngredientToDB(context,ingredients);
            view.popupToast("All Ingredients have been saved!");
        } else if(this.userRecipe!=null){
            userRecipe.addIngredientsToDB(context,userRecipe.getIngredientList());
            view.popupToast("All Ingredients have been saved!");
        }


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
        view.displayOnlineIngredients(ingredients);

    }

    public void updateServing(int newServing) {
        view.updateServingSize(newServing);
    }

    public void displayDirectionsPage(){
        view.displayDirectionsPage(Recipe.getOnlineUrl(recipe.getSourceURL()));
    }


    public interface View {

        void displayRecipePhoto(String imageLink);
        void displayRecipePhotoFromBitmap(Bitmap bitmap);
        void displayOnlineIngredients(ArrayList<Ingredient>ingredients);
        void displayUserIngredients(ArrayList<UserRecipeIngredient>ingredients);
        void popupToast(String text);
        void updateServingSize(int newSergving);
        void displayRecipeTitle(String recipeTitle);
        void displayFavouriteButton(Boolean bool);
        void displayServingSize(int size);
        void displayCookingTime(int cookingTime);
        void setIconVisibility(int a, int b, int c);
        void displayComment(String comment);
        void displayCookingSteps(ArrayList<UserRecipeStep> userRecipeSteps);
        void displayDirectionsPage(String Url);
        void displayShoppingCartButton();
    }



}
