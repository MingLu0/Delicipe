package com.luo.ming.delicipe.Models;

        import java.util.ArrayList;

public class RecipeByUser {

    private RecipeCover recipeCover;
    private ArrayList<RecipeIngredient> ingredientList;
    private ArrayList<RecipeStep> recipeStepList;

    public RecipeByUser(RecipeCover recipeCover, ArrayList<RecipeIngredient> ingredientList, ArrayList<RecipeStep> recipeStepList) {
        this.recipeCover = recipeCover;
        this.ingredientList = ingredientList;
        this.recipeStepList = recipeStepList;
    }
}
