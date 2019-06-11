package com.luo.ming.delicipe.Views;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.luo.ming.delicipe.Models.UserRecipeCover;
import com.luo.ming.delicipe.Presenters.UserRecipeFragmentPresenter;
import com.luo.ming.delicipe.R;

public class UserRecipeFragmentViewAdapter extends RecyclerView.Adapter<UserRecipeFragmentViewAdapter.ViewHolder>{

    private UserRecipeFragmentPresenter presenter;
    private Context context;


    public UserRecipeFragmentViewAdapter(UserRecipeFragmentPresenter presenter, Context context) {

        this.presenter = presenter;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_view,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        presenter.onBindUserRecipeViewHolder(i,viewHolder);

    }



    @Override
    public int getItemCount() {
        Log.d("UserRecipeFragmentViewAdapter", String.valueOf(presenter.getItemRowsCount()));
        return presenter.getItemRowsCount();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements UserRecipeFragmentPresenter.UserRecipeRowView{

        private ImageView coverImage;
        private TextView coverName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            coverImage = itemView.findViewById(R.id.recipe_cover_image);
            coverName = itemView.findViewById(R.id.recipe_cover_title);

        }

        @Override
        public void setRowViewContent(UserRecipeCover userRecipeCover) {

//            Log.d("RecipeViewAdapter", userRecipeCover.getImageUri());
            Log.d("RecipeViewAdapter",userRecipeCover.getCoverName());

//            Uri imageUri = Uri.parse(userRecipeCover.getImageUri());
//
//            Picasso.with(context)
//                    .load(imageUri)
//                    .fit()
//                    .into(coverImage);


            coverName.setText(userRecipeCover.getCoverName());

        }
    }
}
