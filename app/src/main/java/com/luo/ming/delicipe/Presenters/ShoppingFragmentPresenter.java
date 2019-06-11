package com.luo.ming.delicipe.Presenters;

import android.content.Context;

import com.luo.ming.delicipe.Models.Ingredient;
import com.luo.ming.delicipe.Models.Ingredients;

import java.util.ArrayList;

public class ShoppingFragmentPresenter {

    private Context context;
    private Ingredients ingredientsObj;
    private ArrayList<Ingredient>ingredients;
    private View view;


    public ShoppingFragmentPresenter(Context context, View view){
        this.view = view;
        this.context = context;
        ingredientsObj = new Ingredients();
        ingredients = new ArrayList<>();
        ingredients = ingredientsObj.getAllIngredientsFromDB(context);

    }

    public void onBindItemRowViewAtPosition(int position, ShoppingRowView view){

        Ingredient ingredient = ingredients.get(position);
        view.setRowContent(ingredient);

    }

    public int getItemRowsCount(){
        return ingredients.size();
    }

    public void deleteShoppingItem(int position) {

        Ingredient deleteItem = ingredients.get(position);

        //TODO MIGHT NEED TO CHANGE ID FROM STRING TO INT
        deleteItem.deleteShoppingItemFromDB(Integer.valueOf(deleteItem.getID()),context);
        ingredients.remove(position);
        view.notifyShoppingItemRemoved(position);

    }

    public void editShoppingItem(int position){
        Ingredient editItem = ingredients.get(position);
        view.getEditedItem(editItem,position);
//        editItem.updateShoppingItemFromDB(newItem,context);


    }

    public void saveUpdatedItem(Ingredient newIngredient, int position){
        newIngredient.updateShoppingItemFromDB(newIngredient,context);
        view.notifyShoppingItemChanged(position,newIngredient);

    }


    public interface View{

        void notifyShoppingItemRemoved(int position);
        void notifyShoppingItemChanged(int position,Ingredient newIngredient);
        void getEditedItem(Ingredient ingredient,int position);


    }

    public interface ShoppingRowView{

         void setRowContent(Ingredient ingredient);
         void notifyItemRemoved(int position);

    }

}
