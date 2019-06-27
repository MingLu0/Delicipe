package com.luo.ming.delicipe.Presenters;

import android.content.Context;
import android.text.Editable;

import com.luo.ming.delicipe.Models.UserRecipeIngredient;
import com.luo.ming.delicipe.Views.AddIngredientFragment;

import java.util.ArrayList;

public class AddIngredientFragmentPresenter {

    private View view;
    private Context context;

    public AddIngredientFragmentPresenter(View view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void displayTableLayout(){
        view.displayTableLayout();
    }

    public void addIngredient(String text) {

        view.addIngredientToLayout(text);

    }

    public ArrayList<UserRecipeIngredient> getAllIngredientsFromTB() {

        ArrayList<UserRecipeIngredient>ingredients = view.getIngredientsFromTableLayout();
        return ingredients;
    }


    public interface View {
        void displayTableLayout();
        void addIngredientToLayout(String item);
        ArrayList<UserRecipeIngredient>getIngredientsFromTableLayout();
    }
}
