package com.luo.ming.delicipe.Views;

import androidx.appcompat.app.AlertDialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.luo.ming.delicipe.Helpers.BitmapUtility;
import com.luo.ming.delicipe.Helpers.DelicipeApplication;
import com.luo.ming.delicipe.Models.UserRecipeCover;
import com.luo.ming.delicipe.Presenters.UserRecipeFragmentPresenter;
import com.luo.ming.delicipe.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserRecipeFragmentViewAdapter extends RecyclerView.Adapter<UserRecipeFragmentViewAdapter.ViewHolder>{

    private UserRecipeFragmentPresenter presenter;
    private Context context;
    public static final String USER_RECIPE_ADPATER_MESSAGE = "package com.luo.ming.delicipe.Views.UserRecipeFragmentViewAdapter";


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
        Log.d("UserRecipeViewAdapter", String.valueOf(presenter.getItemRowsCount()));
        return presenter.getItemRowsCount();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements UserRecipeFragmentPresenter.UserRecipeRowView{

        @BindView(R.id.recipe_cover_image) ImageView coverImage;
        @BindView(R.id.recipe_cover_title) TextView coverName;
        @BindView(R.id.delete_user_recipe) ImageButton deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
            deleteBtn.setVisibility(View.VISIBLE);
        }

        @OnClick(R.id.delete_user_recipe)
        public void deleteUserRecipe(){
            new AlertDialog.Builder(context)
                    .setTitle(R.string.alert_dialog_delete_recipe_title)
                    .setMessage(R.string.alert_dialog_delete_recipe_message)
                    .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            presenter.deleteUserRecipe(getAdapterPosition());
                        }
                    })
                    .setNegativeButton(R.string.alert_dialog_cancel,null)
                    .show();
        }

        @OnClick(R.id.recipe_cover_image)
        public void displayUserRecipeDetails(){
            int position = getAdapterPosition();
            Intent intent = new Intent(context, RecipeDisplayActivity.class);
            UserRecipeCover userRecipeCover = presenter.getUserRecipeCover(position);
            intent.putExtra(USER_RECIPE_ADPATER_MESSAGE,userRecipeCover.getCoverID());
            context.startActivity(intent);
        }


        @Override
        public void setRowViewContent(UserRecipeCover userRecipeCover) {

            Glide.with(context)
                    .load(userRecipeCover.getImageBytes())
                    .centerCrop()
                    .into(coverImage);

            coverName.setText(userRecipeCover.getCoverName());

        }
    }
}
