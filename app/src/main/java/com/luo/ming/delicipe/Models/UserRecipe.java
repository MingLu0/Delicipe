package com.luo.ming.delicipe.Models;

import android.os.Parcel;
import android.os.Parcelable;
import com.luo.ming.delicipe.Data.DatabaseHandler;
import java.util.ArrayList;

public class UserRecipe implements Parcelable {

    private UserRecipeCover userRecipeCover;
    private ArrayList<UserRecipeIngredient> ingredientList;
    private ArrayList<UserRecipeStep> userRecipeStepList;

    public UserRecipeCover getUserRecipeCover() {
        return userRecipeCover;
    }

    public ArrayList<UserRecipeIngredient> getIngredientList() {
        return ingredientList;
    }

    public ArrayList<UserRecipeStep> getUserRecipeStepList() {
        return userRecipeStepList;
    }

    public UserRecipe(UserRecipeCover userRecipeCover, ArrayList<UserRecipeIngredient> ingredientList, ArrayList<UserRecipeStep> userRecipeStepList) {
        this.userRecipeCover = userRecipeCover;
        this.ingredientList = ingredientList;
        this.userRecipeStepList = userRecipeStepList;
    }

    public UserRecipe() {
    }

    protected UserRecipe(Parcel in) {
        userRecipeCover = in.readParcelable(UserRecipeCover.class.getClassLoader());

        ingredientList = in.createTypedArrayList(UserRecipeIngredient.CREATOR);
        userRecipeStepList = in.createTypedArrayList(UserRecipeStep.CREATOR);
    }

    public static final Creator<UserRecipe> CREATOR = new Creator<UserRecipe>() {
        @Override
        public UserRecipe createFromParcel(Parcel in) {
            return new UserRecipe(in);
        }

        @Override
        public UserRecipe[] newArray(int size) {
            return new UserRecipe[size];
        }
    };

    public void saveUserRecipeToDatabase(){

        DatabaseHandler db = DatabaseHandler.getDataBase();
        db.saveRecipeCover(userRecipeCover);
        int recipeCoverID = db.getUserRecipeCoverID(userRecipeCover.getCoverName());
        db.saveRecipeIngredients(ingredientList,recipeCoverID);
        db.saveRecipeSteps(userRecipeStepList, recipeCoverID);

    }


    public UserRecipe getUserRecipeObjFromCoverObj(UserRecipeCover recipeCover) {

        DatabaseHandler db = DatabaseHandler.getDataBase();
        userRecipeCover = recipeCover;
        ingredientList = db.getRecipeIngredientsWithCoverId(recipeCover.getCoverID());
        userRecipeStepList = db.getRecipeStepsWithCoverId(recipeCover.getCoverID());

        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(userRecipeCover, flags);
        dest.writeTypedList(ingredientList);
        dest.writeTypedList(userRecipeStepList);
    }

    public UserRecipe getUserRecipeWithID(String recipeID) {

        DatabaseHandler db = DatabaseHandler.getDataBase();
        userRecipeCover = db.getRecipeCoverWithId(recipeID);
        ingredientList = db.getRecipeIngredientsWithCoverId(recipeID);
        userRecipeStepList = db.getRecipeStepsWithCoverId(recipeID);

        return this;
    }

    public void addIngredientsToDB(ArrayList<UserRecipeIngredient> ingredientList) {

        DatabaseHandler db = DatabaseHandler.getDataBase();
        db.addUserIngredients(ingredientList);
    }

    public static void deleteUserRecipeByID(String id){
        DatabaseHandler db = DatabaseHandler.getDataBase();
        db.deleteUserRecipeByID(id);
    }
}
