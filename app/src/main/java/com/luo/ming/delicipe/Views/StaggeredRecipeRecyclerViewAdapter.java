package com.luo.ming.delicipe.Views;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luo.ming.delicipe.Presenters.RecommendationFragmentPresenter;
import com.luo.ming.delicipe.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

public class StaggeredRecipeRecyclerViewAdapter extends RecyclerView.Adapter<StaggeredRecipeRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private RecommendationFragmentPresenter presenter;

    public StaggeredRecipeRecyclerViewAdapter(Context context, RecommendationFragmentPresenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 3;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.staggered_recipe_first;
        switch (viewType){
            case 1:
                layoutId = R.layout.staggered_recipe_second;
                break;
            case 2:
                layoutId = R.layout.staggered_recipe_third;
        }

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(layoutId,parent,false);
        return new ViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {
            presenter.onBindViewHolder(holder, position);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return presenter.getCount();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements RecommendationFragmentPresenter.RowView{

        private ImageView recipeImage;
        private TextView recipeTitle, recipeSource;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeImage = itemView.findViewById(R.id.recipe_image);
            recipeTitle = itemView.findViewById(R.id.recipe_title);
            recipeSource = itemView.findViewById(R.id.recipe_source);
        }

        @Override
        public void setRecipePhoto(String imageLink) {
            Picasso.with(context)
                    .load(imageLink)
                    .error(R.drawable.ic_launcher_background)
                    .fit()
                    .into(recipeImage);


        }

        @Override
        public void setRecipeTitle(String title) {

            recipeTitle.setText(title);

        }

        @Override
        public void setRecipePublisher(String source) {

            recipeSource.setText(source);

        }
    }
}
