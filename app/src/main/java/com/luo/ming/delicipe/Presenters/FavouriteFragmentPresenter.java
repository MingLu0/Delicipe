package com.luo.ming.delicipe.Presenters;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.luo.ming.delicipe.Models.Recipe;
import com.luo.ming.delicipe.Views.FavouritesRecyclerViewAdapter;

import java.util.ArrayList;

public class FavouriteFragmentPresenter {

    private Recipe recipe;
    private ArrayList<Recipe>recipeList;
    private Context context;
    private View view;

    public FavouriteFragmentPresenter(Context context, View view) {
        this.context = context;
        this.view = view;
        new getRecipesFromDB().execute();
        Log.d("FavouriteFragment", "presenter constructor called");
    }

    public void onBindRecipeRowViewAtPosition(int position, FavouritesRecyclerViewAdapter.ViewHolder holder) {
        recipe = recipeList.get(position);

        holder.setRowViewImage(recipe.getImageLink());
        holder.setRowViewPublisher(recipe.getPublisher());
        holder.setRowViewTitle(recipe.getTitle());
    }

    public int getItemCount() {

        if(recipeList != null){
            return recipeList.size();
        }

        return 0;

    }

    public Recipe getRecipeObjAtPosition(int layoutPosition) {

        return recipeList.get(layoutPosition);
    }


    public class getRecipesFromDB extends AsyncTask<Void,Void,Void>{


        @Override
        protected Void doInBackground(Void... voids) {

            Recipe recipe = new Recipe();


            recipeList = recipe.getFavouriteRecipesFromDB(context);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            view.setRecyclerViewAdapter();
            view.refreshRecipeList();
            Log.d("FavouriteFragment", "onpostexecute  called");

        }
    }

    // interface view for each recipe row
    public interface RowView{
        void setRowViewImage(String imageLink);
        void setRowViewPublisher(String publisher);
        void setRowViewTitle(String title);
    }

    //interface view for the activity class which implements the view
    public interface View{
        void refreshRecipeList();
        void setRecyclerViewAdapter();

    }
}
