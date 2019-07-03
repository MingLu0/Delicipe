package com.luo.ming.delicipe.Presenters;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.luo.ming.delicipe.Models.UserRecipe;
import com.luo.ming.delicipe.Models.UserRecipeCover;

import java.util.ArrayList;

public class UserRecipeFragmentPresenter {

    private Context context;
    private ArrayList<UserRecipeCover>userRecipeCoverList;
    private ArrayList<UserRecipe>userRecipeList;
    private UserRecipeCover userRecipeCover;
    private View view;

    public UserRecipeFragmentPresenter(Context context,View view) {
        this.context = context;
        this.view = view;
        userRecipeCover = new UserRecipeCover();
        //userRecipeCoverList = userRecipeCover.getAllRecipeCoversFromDB(context);
        new GetAllRecipeCoversTask().execute();

    }

    public void onBindUserRecipeViewHolder(int position, UserRecipeRowView view){

        UserRecipeCover recipeCover = userRecipeCoverList.get(position);
        view.setRowViewContent(recipeCover);

    }

    public int getItemRowsCount(){

        if(userRecipeCoverList==null){
            return 0;
        }

        if(userRecipeCoverList.isEmpty()){
            return 0;
        }
        return userRecipeCoverList.size();
    }

    public UserRecipe getUserRecipeObject(int position) {

        UserRecipeCover recipeCover = userRecipeCoverList.get(position);

        UserRecipe userRecipe = new UserRecipe();

        userRecipe = userRecipe.getUserRecipeObjFromCoverObj(recipeCover,context);

        return userRecipe;

    }

    public UserRecipeCover getUserRecipeCover(int position) {

        return userRecipeCoverList.get(position);
    }

    private class GetAllRecipeCoversTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            userRecipeCoverList = userRecipeCover.getAllRecipeCoversFromDB(context);
            Log.d("RecipeFragmentPresenter","retrived usercover list");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
           // view.notifyDataSetHasChanged();

        }
    }


    public interface View{

        void notifyDataSetHasChanged();

    }


    public interface UserRecipeRowView{

        void setRowViewContent(UserRecipeCover userRecipeCover);

    }
}
