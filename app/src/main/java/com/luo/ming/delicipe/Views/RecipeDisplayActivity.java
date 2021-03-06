package com.luo.ming.delicipe.Views;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.bumptech.glide.Glide;
import com.luo.ming.delicipe.Helpers.BitmapUtility;
import com.luo.ming.delicipe.Models.Recipe;
import com.luo.ming.delicipe.Models.UserRecipeStep;
import com.luo.ming.delicipe.Presenters.RecipeDisplayActivityPresenter;
import com.luo.ming.delicipe.R;
import com.luo.ming.delicipe.R2;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeDisplayActivity extends AppCompatActivity implements RecipeDisplayActivityPresenter.View {

    @BindView(R2.id.toolbarimageview) ImageView toolbarImage;
    @BindView(R2.id.btnCart) CheckBox btnCart;
    @BindView(R2.id.table_main) TableLayout ingredientTableLayout;
    @BindView(R2.id.table_steps) TableLayout stepsTableLayout;
    @BindView(R2.id.txtServing) TextView txtServing;
    @BindView(R2.id.text_recipe_title) TextView textTitle;
    @BindView(R2.id.txtSteps) TextView textSteps;
    @BindView(R2.id.txtCookingTime) TextView textCookingTime;
    @BindView(R2.id.txtComment) TextView textComment;
    @BindView(R2.id.toolbar) Toolbar toolbar;
    @BindView(R2.id.checkBox) CheckBox checkBox;
    @BindView(R2.id.btnGetDirection) Button btnGetDirection;

    private RecipeDisplayActivityPresenter presenter;
    private static int newServing;
    private ProgressDialog progressDialog;

    //todo handle rotation
    //todo add divider
    //todo add progress bar

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_scrolling,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int ID = item.getItemId();

        switch(ID){
            case android.R.id.home :
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling2);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_up);

        newServing = 4;
        handleIntents();
    }

    private void handleIntents() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();


        if(bundle.containsKey(FavouritesRecyclerViewAdapter.FAVOURITE_RECYCLER_VIEW_MESSAGE)){

            Recipe recipe = (Recipe) bundle.getSerializable(FavouritesRecyclerViewAdapter.FAVOURITE_RECYCLER_VIEW_MESSAGE);
            presenter = new RecipeDisplayActivityPresenter(this,recipe);
            presenter.displayFavouriteRecipe();

        } else if (bundle.containsKey(SearchRecyclerViewAdapter.RECIPE_ID_MESSAGE)){

            String recipeID = bundle.getString(SearchRecyclerViewAdapter.RECIPE_ID_MESSAGE);
            presenter = new RecipeDisplayActivityPresenter(this);
            presenter.setUrl(recipeID);
            presenter.displayOnlineRecipe();

        } else if(bundle.containsKey(UserRecipeFragmentViewAdapter.USER_RECIPE_ADPATER_MESSAGE)){

            String userRecipeID = bundle.getString(UserRecipeFragmentViewAdapter.USER_RECIPE_ADPATER_MESSAGE);
            presenter = new RecipeDisplayActivityPresenter(this,userRecipeID);
            presenter.displayUserRecipe();
        }
    }


    @OnClick(R2.id.checkBox)
    public void saveFavoriteRecipe(View v){

        Boolean checked = ((CheckBox)v).isChecked();
        if(checked){
            checkBox.setButtonDrawable(R.drawable.ic_favorite_green_24dp);

            presenter.saveFavouriteRecipe();

            Toast.makeText(getApplication(), "Recipe Saved!", Toast.LENGTH_SHORT).show();

        } else {
            checkBox.setButtonDrawable(R.drawable.ic_action_favourite);

            Toast.makeText(getApplication(), "Recipe Unsaved!", Toast.LENGTH_SHORT).show();

            presenter.unSaveFavouriteRecipe();
        }

    }

    @OnClick(R2.id.btnGetDirection)
    public void getCookingDirections(){
        presenter.displayDirectionsPage();
    }


    @Override
    public void displayRecipePhoto(String imageLink) {

        Picasso.with(this)
                .load(imageLink)
                .error(R.drawable.ic_launcher_foreground)
                .fit()
                .into(toolbarImage);
    }

    @Override
    public void displayRecipePhotoFromBitmap(Bitmap bitmap) {
        Glide.with(this)
                .load(bitmap)
                .centerCrop()
                .error(R.drawable.ic_launcher_foreground)
                .into(toolbarImage);

    }

    @Override
    public void displayIngredients(ArrayList<String> ingredientNames) {

        ingredientTableLayout.removeAllViews();

        for(int i=0;i<ingredientNames.size();i++){
            LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            ConstraintLayout row = (ConstraintLayout) inflater.inflate(R.layout.table_row_ingredient_api,null);

             final TextView itemName = row.findViewById(R.id.text_ingredient_item);

             itemName.setText(ingredientNames.get(i));

             final CheckBox checkBox = row.findViewById(R.id.checkBox_save_ingredient);
             checkBox.setChecked(false);
             checkBox.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if(checkBox.isChecked()){
                         presenter.saveSingleIngredient(itemName.getText().toString());
                         Toast.makeText(getApplication(),"Ingredient Saved", Toast.LENGTH_SHORT).show();

                     } else {
                         presenter.unsaveSingleIngredient(itemName.getText().toString());
                         Toast.makeText(getApplication(),"Ingredient Unsaved",Toast.LENGTH_SHORT).show();
                     }
                 }
             });

            ingredientTableLayout.addView(row,i);

        }

    }

    @Override
    public void displayCookingSteps(ArrayList<UserRecipeStep> userRecipeSteps) {

        stepsTableLayout.removeAllViews();

        for(int i=0;i<userRecipeSteps.size();i++){

            LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout row = (LinearLayout)inflater.inflate(R.layout.table_row_cooking_step,null);

            TextView stepCount = row.findViewById(R.id.text_step_count);
            TextView stepText = row.findViewById(R.id.text_step_name);
            ImageView stepImage = row.findViewById(R.id.image_step);

            stepCount.setText("Step "+String.valueOf(i+1));
            stepText.setText(userRecipeSteps.get(i).getStepText());
            byte[]imageBytes = userRecipeSteps.get(i).getImageBytes();
            Bitmap bitmap = BitmapUtility.covertBytesToBitmap(imageBytes);

            Glide.with(this)
                    .load(bitmap)
                    .into(stepImage);

            stepsTableLayout.addView(row,i);

        }

    }

    @Override
    public void displayDirectionsPage(String url) {

        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);

    }

    @Override
    public void displayShoppingCartButton() {

        btnCart.setButtonDrawable(R.drawable.ic_action_shopping_cart_add);
    }

    @Override
    public void displayProgressDialog() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Your recipe is on the way ...");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
        progressDialog.show();

    }

    @Override
    public void dismissProgessDialog() {

        if(progressDialog !=null){
            progressDialog.dismiss();
        }

    }

    @Override
    public void updateServingSize(int newSergving) {

        txtServing.setText(String.valueOf(newSergving));

    }

    @Override
    public void displayRecipeTitle(String recipeTitle) {

        textTitle.setText(recipeTitle);

    }

    @Override
    public void displayFavouriteButton(Boolean bool) {

        checkBox.setChecked(bool);

        if(bool){
            checkBox.setButtonDrawable(R.drawable.ic_favorite_green_24dp);
        } else {
            checkBox.setButtonDrawable(R.drawable.ic_action_favourite);
        }

    }

    @Override
    public void displayServingSize(int size) {

        txtServing.setText(String.valueOf(size));

    }

    @Override
    public void displayCookingTime(int cookingTime) {

        textCookingTime.setText(String.valueOf(cookingTime)+" Minutes");

    }

    @Override
    public void setIconVisibility(int a, int b, int c) {

        textSteps.setVisibility(a);
        btnGetDirection.setVisibility(b);
        checkBox.setVisibility(c);

    }

    @Override
    public void displayComment(String comment) {
        textComment.setText(comment);
    }


    @Override
    public void popupToast(String text) {

        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();

    }




}
