package com.luo.ming.delicipe.Presenters;

import android.os.AsyncTask;

import com.luo.ming.delicipe.Helpers.DelicipeApplication;
import com.luo.ming.delicipe.Models.Ingredient;

import java.util.ArrayList;

public class ShoppingFragmentPresenter {

    private ArrayList<Ingredient>ingredients;
    private View view;


    public ShoppingFragmentPresenter(View view){
        this.view = view;
        new getIngredientsFromDB( ).execute();

    }

    public void saveNewItem(String item) {
        new saveNewItemToDB(item).execute();
    }

    public  class saveNewItemToDB extends AsyncTask<Void,Void,Void>{

        private String item;

        public saveNewItemToDB(String item) {
            this.item = item;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Ingredient.addShoppingItemToDB(item);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            view.resetPresenterAndAdapter();

        }
    }

    public void deleteAllShoppingItems() {

        new deleteAllShoppingItemsInDB().execute();
    }

    public  class deleteAllShoppingItemsInDB extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            Ingredient.deleteAllShoppingItems();
            ingredients = null;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            view.notifyShoppingDataChanged();
            view.displayItemAllDeletedMessage();
        }
    }

    private class getIngredientsFromDB extends AsyncTask<Void,Void,Void> {


        @Override
        protected Void doInBackground(Void... voids) {

            ingredients = Ingredient.getAllIngredientsFromDB(DelicipeApplication.getAppContext());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            view.notifyShoppingDataChanged();

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
        Ingredient.deleteShoppingItemFromDBById(deleteItem.getID());
        ingredients.remove(position);
        view.notifyShoppingItemRemoved(position);
    }



    public void editShoppingItem(int position){
        Ingredient editItem = ingredients.get(position);
        view.getEditedItem(editItem,position);
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
            newIngredient.updateShoppingItemFromDB(newIngredient);
            ingredients.set(position,newIngredient);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            view.notifyShoppingDataChanged();

        }
    }



    public interface View{
        void notifyShoppingItemRemoved(int position);
        void getEditedItem(Ingredient ingredient,int position);
        void resetPresenterAndAdapter();
        void notifyShoppingDataChanged();
        void displayItemAllDeletedMessage();
    }

    public interface ShoppingRowView{

         void setRowContent(Ingredient ingredient);
         void notifyItemRemoved(int position);

    }

}
