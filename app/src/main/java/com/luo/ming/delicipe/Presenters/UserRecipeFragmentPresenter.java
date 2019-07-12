package com.luo.ming.delicipe.Presenters;

import android.os.AsyncTask;
import android.util.Log;

import com.luo.ming.delicipe.Models.UserRecipe;
import com.luo.ming.delicipe.Models.UserRecipeCover;

import java.util.ArrayList;

public class UserRecipeFragmentPresenter {

    private ArrayList<UserRecipeCover>userRecipeCoverList;
    private View view;

    public UserRecipeFragmentPresenter(View view) {
        this.view = view;
        if(userRecipeCoverList==null){
            new GetAllRecipeCoversTask().execute();
        }
    }

    public void onBindUserRecipeViewHolder(int position, UserRecipeRowView view){

        view.setRowViewContent(userRecipeCoverList.get(position));

    }

    public int getItemRowsCount(){

        if(userRecipeCoverList==null||userRecipeCoverList.isEmpty()){
            return 0;
        }
        return userRecipeCoverList.size();
    }


    public UserRecipeCover getUserRecipeCover(int position) {

        return userRecipeCoverList.get(position);
    }

    public void deleteUserRecipe(int position) {

        new deleteUserRecipeByID(userRecipeCoverList.get(position).getCoverID(),position).execute();
    }

    private  class deleteUserRecipeByID extends AsyncTask<Void,Void,Void>{

        private String id;
        private int position;

        public deleteUserRecipeByID(String id, int position) {
            this.id = id;
            this.position = position;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            UserRecipe.deleteUserRecipeByID(id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            userRecipeCoverList.remove(position);
            view.notifyDataSetHasChanged();
            view.displayRecipeDeletedMessage();

        }
    }

    private class GetAllRecipeCoversTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            userRecipeCoverList = UserRecipeCover.getAllRecipeCoversFromDB();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            view.notifyDataSetHasChanged();

        }
    }


    public interface View{

        void notifyDataSetHasChanged();
        void displayRecipeDeletedMessage();

    }


    public interface UserRecipeRowView{

        void setRowViewContent(UserRecipeCover userRecipeCover);

    }
}
