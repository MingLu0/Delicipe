package com.luo.ming.delicipe.Models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;

import com.luo.ming.delicipe.Data.DatabaseHandler;
import java.util.ArrayList;

public class UserRecipe implements Parcelable {

    private UserRecipeCover userRecipeCover;
    private ArrayList<UserRecipeIngredient> ingredientList;
    private ArrayList<UserRecipeStep> userRecipeStepList;
    private DatabaseHandler db;


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

    public void saveUserRecipeToDatabase(Context context){

        db = DatabaseHandler.getDataBase(context);
        db.saveRecipeCover(userRecipeCover);
        int recipeCoverID = db.getUserRecipeCoverID(userRecipeCover.getCoverName());
        db.saveRecipeIngredients(ingredientList,recipeCoverID);
        db.saveRecipeSteps(userRecipeStepList, recipeCoverID);

    }


    public UserRecipe getUserRecipeObjFromCoverObj(UserRecipeCover recipeCover,Context context) {

        db = DatabaseHandler.getDataBase(context);
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

    public UserRecipe getUserRecipeWithID(String recipeID, Context context) {

        db = DatabaseHandler.getDataBase(context);

        userRecipeCover = db.getRecipeCoverWithId(recipeID);
        ingredientList = db.getRecipeIngredientsWithCoverId(recipeID);
        userRecipeStepList = db.getRecipeStepsWithCoverId(recipeID);

        return this;
    }
}
