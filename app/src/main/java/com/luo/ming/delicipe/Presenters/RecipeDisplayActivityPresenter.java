package com.luo.ming.delicipe.Presenters;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import com.luo.ming.delicipe.Helpers.BitmapUtility;
import com.luo.ming.delicipe.Helpers.VolleyCallBack;
import com.luo.ming.delicipe.Models.Ingredient;
import com.luo.ming.delicipe.Models.Recipe;
import com.luo.ming.delicipe.Models.UserRecipe;
import com.luo.ming.delicipe.Models.UserRecipeCover;
import com.luo.ming.delicipe.Models.UserRecipeIngredient;
import com.luo.ming.delicipe.Models.UserRecipeStep;

public class RecipeDisplayActivityPresenter {

    private String url;
    private View view;
    private Recipe recipe;
    private UserRecipe userRecipe;
    private UserRecipeCover userRecipeCover;
    private String recipeID;
    private ArrayList<Ingredient>ingredients;



    private final String baseUrl = "https://www.food2fork.com/api/get?key=";
    private final String key = "f5b73a553a6a92ccfabca695807bdaeb";//


    //set the url using the base url and the user input keyword
    public void setUrl(String id) {

        url = baseUrl+key+"&rId="+id;
        Log.d("scrollingpresenter",url);

    }

    public RecipeDisplayActivityPresenter(View view){
        this.view = view;
        recipe = new Recipe();
        ingredients = new ArrayList<>();

    }

    public RecipeDisplayActivityPresenter(View view, Recipe recipe){

        this.view = view;
        this.recipe = recipe;
        ingredients = recipe.getIngredients();

    }

    public RecipeDisplayActivityPresenter(View view, String recipeID){
        this.view = view;
        this.recipeID = recipeID;
        userRecipe = new UserRecipe();
        userRecipe = userRecipe.getUserRecipeWithID(recipeID);
    }


    public void saveFavouriteRecipe(){

        new saveUnsaveFavouriteRecipeTask(recipe,true).execute();
    }

    public void unSaveFavouriteRecipe() {
        new saveUnsaveFavouriteRecipeTask(recipe,false).execute();
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
        ArrayList<String>ingredientNames = new ArrayList<>();
        for(UserRecipeIngredient i : ingredients){
            ingredientNames.add(i.getName());
        }
        view.displayIngredients(ingredientNames);
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

        Ingredient.addShoppingItemToDB(item);
    }

    public void unsaveSingleIngredient(String item) {
        Ingredient ingredient = new Ingredient();
        ingredient.deleteShoppingItemFromDBByName(item);
    }

    public static class saveUnsaveFavouriteRecipeTask extends AsyncTask<Void,Void,Void>{

        private Recipe recipe;
        private Boolean save;

        public saveUnsaveFavouriteRecipeTask(Recipe recipe, Boolean save){

            this.recipe = recipe;
            this.save = save;

        }

        @Override
        protected Void doInBackground(Void... voids) {

            if(save){
                recipe.saveFavouriteRecipeToDB();
                return null;
            }

            recipe.unsaveFavouriteRecipetoDB();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }


    public void displayOnlineRecipe(){
        view.setIconVisibility(android.view.View.INVISIBLE,android.view.View.VISIBLE,android.view.View.VISIBLE);

        recipe.getRecipeObj(url, new VolleyCallBack() {
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
        ArrayList<String>ingredientNames = new ArrayList<>();
        for(Ingredient i : ingredients){
            ingredientNames.add(i.getIngredientItem());
        }
        view.displayIngredients(ingredientNames);

    }

    public void saveAllIngredients(){

        if(this.recipe!=null){
            recipe.addIngredientToDB(ingredients);
            view.popupToast("All Ingredients have been saved!");
        } else if(this.userRecipe!=null){
            userRecipe.addIngredientsToDB(userRecipe.getIngredientList());
            view.popupToast("All Ingredients have been saved!");
        }


    }

    public void displayFavBtnForAPIRecipes(){

       Boolean bool = recipe.checkIfRecipeSaved();
        view.displayFavouriteButton(bool);
    }

    public void displayFavouriteButton(){

        new DisplayFavouriteButtonState(view,recipe).execute();

    }

    private static class DisplayFavouriteButtonState extends AsyncTask<Void,Void,Void>{

        Boolean bool;
        View view;
        Recipe recipe;

        public DisplayFavouriteButtonState( View view, Recipe recipe) {
            this.view = view;
            this.recipe = recipe;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            bool = recipe.checkIfRecipeSaved();
            view.displayFavouriteButton(bool);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
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
        void displayIngredients(ArrayList<String>ingredients);
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
