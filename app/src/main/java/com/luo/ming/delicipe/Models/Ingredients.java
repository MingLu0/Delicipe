package com.luo.ming.delicipe.Models;

import android.content.Context;

import com.luo.ming.delicipe.Data.DatabaseHandler;

import java.io.Serializable;
import java.util.ArrayList;

public class Ingredients implements Serializable {

    private DatabaseHandler db;

    public Ingredients() {

    }

    public ArrayList<Ingredient>getAllIngredientsFromDB(Context context){
        db = DatabaseHandler.getDataBase(context);
        return db.getAllIngredients();
    }
}
