package com.luo.ming.delicipe.Presenters;

import android.content.Context;

import com.luo.ming.delicipe.Models.Recipe;
import com.luo.ming.delicipe.Views.StaggeredRecipeRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecommendationFragmentPresenter {

    private JSONArray recipeJsonArray;
    private Recipe recipe;
    private Context context;
    private View view;

    public RecommendationFragmentPresenter(Context context,View view) {
        this.context = context;
        this.view = view;
        recipeJsonArray = Recipe.loadRecommendedRecipeJsonArray(context.getResources());
        if(recipeJsonArray!=null){

        }
    }

    public void onBindViewHolder(StaggeredRecipeRecyclerViewAdapter.ViewHolder holder, int position) throws JSONException {

        JSONObject jsonObject= recipeJsonArray.getJSONObject(position);
        holder.setRecipePhoto(Recipe.getOnlineImageLink(jsonObject.getString("image_url")));
        holder.setRecipePublisher(jsonObject.getString("publisher"));
        holder.setRecipeTitle(jsonObject.getString("title"));
    }

    public int getCount() {

        if(recipeJsonArray!=null){
            return recipeJsonArray.length();
        }
        return 0;
    }

    public String getRecipeID(int position) throws JSONException {

        JSONObject jsonObject = recipeJsonArray.getJSONObject(position);

        return jsonObject.getString("recipe_id");
    }

    public interface RowView{
        void setRecipePhoto(String imageLink);
        void setRecipeTitle(String title);
        void setRecipePublisher(String source);
    }

    public interface View{

        //void setAdapter();
       // void referesh

    }
}
