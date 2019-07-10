package com.luo.ming.delicipe.Presenters;

import android.content.Context;

import com.luo.ming.delicipe.Models.UserRecipe;
import com.luo.ming.delicipe.Models.UserRecipeCover;
import com.luo.ming.delicipe.Models.UserRecipeIngredient;
import com.luo.ming.delicipe.Models.UserRecipeStep;

import java.util.ArrayList;

public class AddRecipeActivityPresenter {

    private View view;
    private Context context;

    private UserRecipeCover userRecipeCover;
    private ArrayList<UserRecipeIngredient> ingredientList;
    private ArrayList<UserRecipeStep> userRecipeStepList;

    private UserRecipe userRecipe;

    public AddRecipeActivityPresenter(View view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void setUserRecipeCover(UserRecipeCover userRecipeCover) {
        this.userRecipeCover = null;
        this.userRecipeCover = userRecipeCover;
    }

    public void setIngredientList(ArrayList<UserRecipeIngredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public void setUserRecipeStepList(ArrayList<UserRecipeStep> userRecipeStepList) {
        this.userRecipeStepList = userRecipeStepList;
    }

    public void saveRecipe() {

        if(userRecipeCover != null){
            userRecipe = new UserRecipe(userRecipeCover,ingredientList,userRecipeStepList);
            userRecipe.saveUserRecipeToDatabase(context);
            view.showSaveSuccessMessage();
        } else {

            view.showSaveFailedMessage();
        }

    }

    public interface View{

        void showSaveSuccessMessage();
        void showSaveFailedMessage();
    }
}
