package com.luo.ming.delicipe.Models;

import android.content.Context;

import com.luo.ming.delicipe.Data.DatabaseHandler;
import com.luo.ming.delicipe.Helpers.DelicipeApplication;
import com.luo.ming.delicipe.Helpers.IngredientClassHelper;

import java.io.Serializable;

import java.util.Arrays;

public class Ingredient implements Serializable {
    private double count;
    private String unit;
    private String ingredient;
    private IngredientClassHelper _helper;
    private int ID;
    private String ingredientItem;

    public String getIngredientItem() {
        return ingredientItem;
    }

    public void setIngredientItem(String ingredientItem) {
        this.ingredientItem = stripParentheses(ingredientItem);
    }

    public void setIngredientItemfromDB(String ingredientItem){
        this.ingredientItem = ingredientItem;
    }

    private DatabaseHandler db;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getCount() {
        return count;
    }

    public String getUnit() {
        return unit;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setIngredient(String ingredient){
        this.ingredient = ingredient;
    }

    public Ingredient() {
    }

    public Ingredient parseIngredient(String ingredient){

        Ingredient newIngredientObj = new Ingredient();
        String shortUnits[]={"tbsp","tbsp","oz","oz","tsp","tsp","cup","pound","stick"};
        _helper = new IngredientClassHelper();

        ingredient = stripParentheses(ingredient);
        ingredient = uniformUnits(ingredient);
        ingredient = ingredient.replace("-"," ");
        ingredient = ingredient.replaceAll(",.*", "");
        //ingredient = ingredient.replaceAll("/..*", "");


        String words[]= ingredient.split("\\s+");

        int index=-1;

        for(int i=0;i<words.length;i++){
            for(int j=0;j<shortUnits.length;j++){
                if(words[i].equals(shortUnits[j])){
                    index = i;
                }
            }
        }

        if(index==1){
            //2 cups Sugar
            newIngredientObj.setCount(_helper.parseRatio(words[0]));
            newIngredientObj.setUnit(words[1]);

            String[]arr= Arrays.copyOfRange(words,2,words.length);

            String ingredientStr= _helper.convertArrayOfStringIntoString(arr);

            newIngredientObj.setIngredient(ingredientStr);

        } else if(index==2) {

            //1 3/4 teaspoons salt
            double count;
            count = _helper.parseRatio(words[0])+_helper.parseRatio(words[1]);
            newIngredientObj.setCount(count);
            newIngredientObj.setUnit(words[2]);
            String[]arr= Arrays.copyOfRange(words,3,words.length);
            String ingredientStr= _helper.convertArrayOfStringIntoString(arr);
            newIngredientObj.setIngredient(ingredientStr);

        } else if(index==3) {

            //1-3/4 stick Butter
            double count;
            count = _helper.parseRatio(words[0])+_helper.parseRatio(words[2]);
            newIngredientObj.setCount(count);
            newIngredientObj.setUnit(words[3]);
            String[]arr= Arrays.copyOfRange(words,4,words.length);
            String ingredientStr= _helper.convertArrayOfStringIntoString(arr);
            newIngredientObj.setIngredient(ingredientStr);

        } else if (index==-1&&_helper.isDouble(words[0])){
            //1/4 Semolina flour OR cornmeal for dusting
            double count;
            count = _helper.parseRatio(words[0]);
            newIngredientObj.setCount(count);
            newIngredientObj.setUnit("");
            String[]arr= Arrays.copyOfRange(words,1,words.length);
            String ingredientStr= _helper.convertArrayOfStringIntoString(arr);
            newIngredientObj.setIngredient(ingredientStr);

        } else if (index==-1){

            //Semolina flour OR cornmeal for dusting
            newIngredientObj.setCount(1);
            newIngredientObj.setUnit("");
            newIngredientObj.setIngredient(ingredient);

        }

        return newIngredientObj;
    }

    public String uniformUnits(String ingredient){

        //TODO " 1 1/4 lb ground turkey"
        String longUnits[]={"tablespoons","tablespoon","ounces","ounce","teaspoons","teaspoon","cups","pounds","sticks"};
        String shortUnits[]={"tbsp","tbsp","oz","oz","tsp","tsp","cup","pound","stick"};
        String newIngredient;
        ingredient = ingredient.toLowerCase();

        for(int i=0; i<8 ;i++){
            if(ingredient.contains(longUnits[i])){
                newIngredient=ingredient.replace(longUnits[i],shortUnits[i]);
                return newIngredient;
            }
        }
        return ingredient;

    }

    public String stripParentheses(String ingredient){
        //TODO paranthese removal does not cover all the cases, need more testing
        String newIngredient = ingredient.replaceAll("\\s*\\([^\\)]*\\)\\s*", " ");

       // String newIngredient = ingredient.replaceAll("\\(.*\\) ", "");

        return newIngredient;
    }

    public static void deleteShoppingItemFromDBById(int id){
        DatabaseHandler db = new DatabaseHandler(DelicipeApplication.getAppContext());
        db.deleteShoppingItemById(id);
    }

    public static void deleteShoppingItemFromDBByName(String name){
        DatabaseHandler db = DatabaseHandler.getDataBase();
        db.deleteShoppingItemByName(name);
    }

    public static void updateShoppingItemFromDB(Ingredient ingredient){
        DatabaseHandler db = new DatabaseHandler(DelicipeApplication.getAppContext());
        db.updateShoppingListItem(ingredient);
    }

    public static void  addShoppingItemToDB(String item){
        DatabaseHandler db = new DatabaseHandler(DelicipeApplication.getAppContext());
        db.addShoppingListItem(item);
    }



}
