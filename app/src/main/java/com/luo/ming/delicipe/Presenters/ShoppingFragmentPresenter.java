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
    private View view;
    private Context context;
    private Ingredients ingredientsObj;
    private ArrayList<Ingredient>ingredients;
    private Ingredient ingredient1;
    private Ingredient ingredient2;
    private Ingredient ingredient3;



  //  String[]  = {"1.5 tsp baking soda","2.0 large eggs","0.5 cup heavy cream","1.0 cup water"};


    public ShoppingFragmentPresenter(View view,Context context){
        this.view = view;
        this.context = context;
        ingredientsObj = new Ingredients();
        ingredients = new ArrayList<>();
        ingredients = ingredientsObj.getAllIngredientsFromDB(context);

//        ingredient1 = new Ingredient();
//        ingredient2 = new Ingredient();
//        ingredient3 = new Ingredient();
//
//        ingredient1.setCount(1.5);
//        ingredient1.setUnit("tsp");
//        ingredient1.setIngredient("baking soda");
//
//        ingredient2.setCount(2.0);
//        ingredient2.setUnit("");
//        ingredient2.setIngredient("large eggs");
//
//        ingredient3.setCount(0.33);
//        ingredient3.setUnit("cup");
//        ingredient3.setIngredient("chocolate fudge cake mix with pudding");
//
//        ingredients = new ArrayList<>();
//
//        ingredients.add(ingredient1);
//        ingredients.add(ingredient2);
//        ingredients.add(ingredient3);




    }

    public void onBindItemRowViewAtPosition(int position, ShoppingRowView view){

        Ingredient ingredient = ingredients.get(position);
        view.setRowContent(ingredient);

    }

    public int getItemRowsCount(){
        return ingredients.size();
    }


    public interface View{


    }

    public interface ShoppingRowView{

         void setRowContent(Ingredient ingredient);

    }

}
