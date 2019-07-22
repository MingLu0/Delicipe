package com.luo.ming.delicipe.Views;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.luo.ming.delicipe.Presenters.MainActivityPresenter;
import com.luo.ming.delicipe.Presenters.RecommendationFragmentPresenter;
import com.luo.ming.delicipe.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

public class RecommendedRecyclerViewAdapter extends RecyclerView.Adapter<RecommendedRecyclerViewAdapter.ViewHolder> {

    private RecommendationFragmentPresenter presenter;
    private Context context;


    public RecommendedRecyclerViewAdapter(RecommendationFragmentPresenter presenter, Context context) {
        this.presenter = presenter;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {
            presenter.onBindViewHolder(holder,position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {

        return presenter.getCount();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements RecommendationFragmentPresenter.RowView{

        ImageView recipeImage;
        TextView txtRecipePublisher;
        TextView txtRecipeTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeImage =  itemView.findViewById(R.id.recipe_cover_image);
            txtRecipeTitle = itemView.findViewById(R.id.recipe_cover_title);
            txtRecipePublisher = itemView.findViewById(R.id.source);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,RecipeDisplayActivity.class);

                    try {
                        intent.putExtra(SearchRecyclerViewAdapter.RECIPE_ID_MESSAGE,presenter.getRecipeID(getAdapterPosition()));
                        context.startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public void setRecipePhoto(String imageLink) {

            Glide.with(context)
                    .load(imageLink)
                    .error(R.drawable.ic_launcher_background)
                    .centerCrop()
                    .into(recipeImage);
        }

        @Override
        public void setRecipeTitle(String title) {

            txtRecipeTitle.setText(title);

        }

        @Override
        public void setRecipePublisher(String source) {

            txtRecipePublisher.setText(source);
        }
    }

}
