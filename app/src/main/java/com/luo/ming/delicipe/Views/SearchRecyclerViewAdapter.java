package com.luo.ming.delicipe.Views;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.luo.ming.delicipe.Models.Recipe;
import com.luo.ming.delicipe.Presenters.SearchActivityPresenter;
import com.luo.ming.delicipe.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SearchRecyclerViewAdapter extends  RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder>{

    private final SearchActivityPresenter presenter;
    public static final String RECIPE_ID_MESSAGE = "package com.luo.ming.delicipe.Views.Recipe.ID";
    public static final String RECIPE_TITLE_MESSAGE = "package com.luo.ming.delicipe.Views.Recipe.TITLE";

    private Context context;

    public SearchRecyclerViewAdapter(SearchActivityPresenter presenter,Context context){
        this.context = context;
        this.presenter = presenter;

    }

    //inflate the viewholder with xml layout
    @Override
    public SearchRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_view,viewGroup,false);

        return new ViewHolder(view);
    }

    //bind the data with the viewholder in each row
    @Override
    public void onBindViewHolder( SearchRecyclerViewAdapter.ViewHolder viewHolder, int position) {

        presenter.onBindRecipeRowViewAtPosition(position,viewHolder);


    }

    //return the number of items in the recycler view
    @Override
    public int getItemCount() {
        return presenter.getRecipesRowsCount();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements SearchActivityPresenter.RecipeRowView{
        @BindView(R.id.recipe_cover_image) ImageView recipeImage;
        @BindView(R.id.source) TextView txtRecipePublisher;
        @BindView(R.id.recipe_cover_title) TextView txtRecipeTitle;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }

        @OnClick(R.id.recipe_cover_image)
        public void displayRecipeDetails(){

            Recipe recipe = presenter.getRecipeList().get(getAdapterPosition());
            //send the info about the specific view that the user has clicked on to the
            //the next recipe page
            Intent intent = new Intent(context, RecipeDisplayActivity.class);

            //send the recipe id to the next page
            intent.putExtra(RECIPE_ID_MESSAGE,recipe.getID());
            intent.putExtra(RECIPE_TITLE_MESSAGE,recipe.getTitle());

            context.startActivity(intent);
        }


        //implement reciperowview interface to set the image in each row
        @Override
        public void setRowViewImage(String imageLink) {

            Glide.with(context)
                    .load(imageLink)
                    .error(R.drawable.ic_launcher_background)
                    .centerCrop()
                    .into(recipeImage);

        }

        //implement the reciperowview interface to set the publisher name in each row
        @Override
        public void setRowViewPublisher(String publisher) {
            txtRecipePublisher.setText(publisher);
        }

        //implement the reciperowview interface to set the title name in each row
        @Override
        public void setRowViewTitle(String title) {
            txtRecipeTitle.setText(title);
        }
    }



}
