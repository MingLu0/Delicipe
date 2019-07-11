package com.luo.ming.delicipe.Presenters;

import com.luo.ming.delicipe.Models.UserRecipeIngredient;

import java.util.ArrayList;

public class AddIngredientFragmentPresenter {

    private View view;
    private int editItemPosition;

    public AddIngredientFragmentPresenter(View view) {
        this.view = view;
        editItemPosition = -1;
    }

    public void displayTableLayout(){
        view.displayTableLayout();
    }

    public void addIngredient(String text) {

        view.addIngredientToLayout(text);

    }

    public ArrayList<UserRecipeIngredient> getAllIngredientsFromTB() {

        return view.getIngredientsFromTableLayout();
    }

    public void editIngredient(String item) {
        view.editIngredient(item, editItemPosition);
        editItemPosition = -1;
    }

    public void handleSaveButtonClickedEvent(String item) {

        if (editItemPosition >= 0) {
            editIngredient(item);
        } else {
            addIngredient(item);
        }

    }

    public void setItemPosition(int position) {
        editItemPosition = position;
    }


    public interface View {
        void displayTableLayout();
        void addIngredientToLayout(String item);
        void editIngredient(String item, int position);
        ArrayList<UserRecipeIngredient>getIngredientsFromTableLayout();
    }
}
