package com.luo.ming.delicipe.Presenters;

import android.content.Context;
import android.os.AsyncTask;

import com.luo.ming.delicipe.Models.Ingredient;
import com.luo.ming.delicipe.Models.Ingredients;

import java.util.ArrayList;

public class ShoppingFragmentPresenter {

    private Context context;

    private ArrayList<Ingredient>ingredients;
    private Ingredients ingredientsObj;
    private View view;


    public ShoppingFragmentPresenter(Context context, View view){
        this.view = view;
        this.context = context;
        new getIngredientsFromDB( ).execute();

    }

    private class getIngredientsFromDB extends AsyncTask<Void,Void,Void> {


        @Override
        protected Void doInBackground(Void... voids) {

            ingredientsObj = new Ingredients();
            ingredients = ingredientsObj.getAllIngredientsFromDB(context);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            view.setRecyclerViewAdapter();
            view.refreshRecyclerViewList();
        }
    }



    public void onBindItemRowViewAtPosition(int position, ShoppingRowView view){

        Ingredient ingredient = ingredients.get(position);
        view.setRowContent(ingredient);

    }

    public int getItemRowsCount(){

        if(ingredients!=null){
            return ingredients.size();
        }
        return 0;
    }

    public void deleteShoppingItem(int position) {

        Ingredient deleteItem = ingredients.get(position);
        deleteItem.deleteShoppingItemFromDB(deleteItem.getID(),context);
        ingredients.remove(position);
        view.notifyShoppingItemRemoved(position);
    }



    public void editShoppingItem(int position){
        Ingredient editItem = ingredients.get(position);
        view.getEditedItem(editItem,position);
//        editItem.updateShoppingItemFromDB(newItem,context);


    }

    public void saveUpdatedItem(Ingredient newIngredient, int position){

        new saveUpdatedItemTask(newIngredient,position).execute();


    }

    public class saveUpdatedItemTask extends AsyncTask<Void,Void,Void>{

        private Ingredient newIngredient;
        private int position;

        public saveUpdatedItemTask(Ingredient newIngredient, int position) {

            this.newIngredient = newIngredient;
            this.position = position;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            newIngredient.updateShoppingItemFromDB(newIngredient,context);


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            view.notifyShoppingItemChanged();

        }
    }





    public interface View{

        void setRecyclerViewAdapter();
        void refreshRecyclerViewList();
        void notifyShoppingItemRemoved(int position);
        void notifyShoppingItemChanged();
        void getEditedItem(Ingredient ingredient,int position);
    }

    public interface ShoppingRowView{

         void setRowContent(Ingredient ingredient);
         void notifyItemRemoved(int position);

    }

}
