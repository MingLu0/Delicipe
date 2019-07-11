package com.luo.ming.delicipe.Models;

import android.content.Context;

import com.luo.ming.delicipe.Data.DatabaseHandler;
import com.luo.ming.delicipe.Helpers.DelicipeApplication;

import java.io.Serializable;
import java.util.ArrayList;

public class Ingredients implements Serializable {

    //todo delete this class


    public Ingredients() {

    }

    public static ArrayList<Ingredient>getAllIngredientsFromDB(Context context){
        DatabaseHandler db = DatabaseHandler.getDataBase();
        return db.getAllIngredients();
    }

    public static void deleteAllShoppingItems(){

        DatabaseHandler db = DatabaseHandler.getDataBase();
        db.deleteAllShoppingItems();
    }
}
