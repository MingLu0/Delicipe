package com.luo.ming.delicipe.Models;

import android.content.Context;

import com.luo.ming.delicipe.Data.DatabaseHandler;

import java.util.ArrayList;

public class Ingredients {

    private DatabaseHandler db;

    public Ingredients() {

    }

    public ArrayList<Ingredient>getAllIngredientsFromDB(Context context){
        db = new DatabaseHandler(context);
        return db.getAllIngredients();
    }
}
