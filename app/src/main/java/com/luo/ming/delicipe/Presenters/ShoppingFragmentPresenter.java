package com.luo.ming.delicipe.Presenters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.luo.ming.delicipe.Data.DatabaseHandler;
import com.luo.ming.delicipe.Models.Ingredient;
import com.luo.ming.delicipe.Models.Ingredients;
import com.luo.ming.delicipe.Views.ShoppingListRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShoppingFragmentPresenter {

    private RecyclerView recyclerView;
    private ShoppingListRecyclerViewAdapter recyclerViewAdapter;
    private Context context;
    private Ingredients ingredientsObj;
    private ArrayList<Ingredient>ingredients;
    private ShoppingRowView rowView;
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


    public interface View{

        void notifyShoppingItemRemoved(int position);


    }

    public interface ShoppingRowView{

         void setRowContent(Ingredient ingredient);
         void notifyItemRemoved(int position);

    }

}
