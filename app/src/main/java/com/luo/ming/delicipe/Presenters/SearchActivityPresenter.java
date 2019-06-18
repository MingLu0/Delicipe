package com.luo.ming.delicipe.Presenters;

import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.util.Log;
import android.content.Context;
import java.util.List;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import com.luo.ming.delicipe.Helpers.VolleyCallBack;
import com.luo.ming.delicipe.Models.Recipe;
import com.luo.ming.delicipe.Views.SearchRecyclerViewAdapter;

public class SearchActivityPresenter implements VolleyCallBack {

    private  List<Recipe> recipeList;
    private String url;
    private View view;
    private Recipe recipe;
    private Context context;
    private RecyclerView recyclerView;
    private SearchRecyclerViewAdapter recipeRecyclerViewAdapter;

    private final String baseUrl = "https://www.food2fork.com/api/search?key=";
    private final String key = "f5b73a553a6a92ccfabca695807bdaeb";//



    public List<Recipe> getRecipeList() {
        return recipeList;
    }


    //set the url using the base url and the user input keyword
    public void setUrl(String keyword) {

        url = baseUrl+key+"&q="+keyword;
        Log.d("searchpresenter",url);

    }

    public SearchActivityPresenter(View view, Context context){

        this.view = view;
        this.context = context;
        this.recipe = new Recipe();
        recipeList = new ArrayList<>();

    }


    //implement onBindViewHolder function in recipesearchactivityadapter class
    public void onBindRecipeRowViewAtPosition(int position, RecipeRowView rowView) {

        Recipe recipe = recipeList.get(position);
        rowView.setRowViewImage(recipe.getImageLink());
        Log.d("setRowViewImage",recipe.getImageLink());
        rowView.setRowViewPublisher(recipe.getPublisher());
        rowView.setRowViewTitle(recipe.getTitle());

    }

    //implement getitemcount function in recipesearchactivityadapter class
    public int getRecipesRowsCount(){
        Log.d("recipelist",String.valueOf(recipeList.size()));
        return recipeList.size();
    }

    public void getRecipesList() {

        recipeList.clear();
        recipeList = recipe.getRecipes(url,context,this);

    }

    @Override
    public void onSuccess() {
        view.setRecyclerViewAdapter();
        view.refreshRecipeList();
    }

    @Override
    public void onFailure() {

        view.displayInputErrorSnackBar();

    }


    // interface view for each recipe row
    public interface RecipeRowView{
        void setRowViewImage(String imageLink);
        void setRowViewPublisher(String publisher);
        void setRowViewTitle(String title);
    }

    //interface view for the activity class which implements the view
    public interface View{
        void refreshRecipeList();
        void setRecyclerViewAdapter();
        void displayInputErrorSnackBar();

    }

}
