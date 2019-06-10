package com.luo.ming.delicipe.Models;

        import android.content.Context;

        import com.luo.ming.delicipe.Data.DatabaseHandler;

        import java.util.ArrayList;

public class UserRecipe {

    private UserRecipeCover userRecipeCover;
    private ArrayList<UserRecipeIngredient> ingredientList;
    private ArrayList<UserRecipeStep> userRecipeStepList;
    private DatabaseHandler db;


    public UserRecipe(UserRecipeCover userRecipeCover, ArrayList<UserRecipeIngredient> ingredientList, ArrayList<UserRecipeStep> userRecipeStepList) {
        this.userRecipeCover = userRecipeCover;
        this.ingredientList = ingredientList;
        this.userRecipeStepList = userRecipeStepList;
    }

    public void saveUserRecipeToDatabase(Context context){

        db = DatabaseHandler.getDataBase(context);
        db.saveRecipeCover(userRecipeCover);

        int recipeCoverID = db.getUserRecipeCoverID(userRecipeCover.getCoverName());
        db.saveRecipeIngredients(ingredientList,recipeCoverID);
        db.saveRecipeSteps(userRecipeStepList, recipeCoverID);

    }


}
