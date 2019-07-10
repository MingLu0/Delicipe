package com.luo.ming.delicipe.Views;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luo.ming.delicipe.Models.Recipe;
import com.luo.ming.delicipe.Presenters.FavouriteFragmentPresenter;
import com.luo.ming.delicipe.R;
import com.squareup.picasso.Picasso;

public class FavouritesRecyclerViewAdapter extends RecyclerView.Adapter<FavouritesRecyclerViewAdapter.ViewHolder>{

    private Context context;
    private FavouriteFragmentPresenter presenter;
    public static final String FAVOURITE_RECYCLER_VIEW_MESSAGE = "package com.luo.ming.delicipe.Views.FavouritesRecyclerAdapter";

    public FavouritesRecyclerViewAdapter(Context context, FavouriteFragmentPresenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view,parent,false);
        Log.d("onCreateViewHolder","onCreateViewHolder has been called");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        presenter.onBindRecipeRowViewAtPosition(position,holder);
        Log.d("onBindViewHolder","onBindViewHolder has been called");
    }

    @Override
    public int getItemCount() {
        return presenter.getItemCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements FavouriteFragmentPresenter.RowView {
        ImageView recipeImage;
        TextView txtRecipePublisher;
        TextView txtRecipeTitle;
       // Context context;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            recipeImage =  itemView.findViewById(R.id.recipe_cover_image);
            txtRecipeTitle = (TextView) itemView.findViewById(R.id.recipe_cover_title);
            txtRecipePublisher = (TextView) itemView.findViewById(R.id.source);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    Recipe recipe = presenter.getRecipeObjAtPosition(position);
                    Intent intent = new Intent(context, RecipeDisplayActivity.class);
                    intent.putExtra(FAVOURITE_RECYCLER_VIEW_MESSAGE,recipe);
                    context.startActivity(intent);

                }
            });
        }

        //implement reciperowview interface to set the image in each row
        @Override
        public void setRowViewImage(String imageLink) {

            Picasso.with(context)
                    .load(imageLink)
                    .error(R.drawable.ic_launcher_background)
                    .fit()
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
