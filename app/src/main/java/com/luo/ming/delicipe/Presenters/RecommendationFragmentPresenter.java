package com.luo.ming.delicipe.Presenters;


import com.luo.ming.delicipe.Models.Recipe;
import com.luo.ming.delicipe.Views.RecommendedRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecommendationFragmentPresenter {

    private JSONArray recipeJsonArray;
    private View view;

    public RecommendationFragmentPresenter(View view) {
        this.view = view;
        recipeJsonArray = Recipe.loadRecommendedRecipeJsonArray();
    }

    public void onBindViewHolder(RecommendedRecyclerViewAdapter.ViewHolder holder, int position) throws JSONException {

        JSONObject jsonObject= recipeJsonArray.getJSONObject(position);
        holder.setRecipePhoto(Recipe.getOnlineUrl(jsonObject.getString("image_url")));
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
