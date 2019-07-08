package com.luo.ming.delicipe.Views;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.bumptech.glide.Glide;
import com.luo.ming.delicipe.Helpers.BitmapUtility;
import com.luo.ming.delicipe.Models.Ingredient;
import com.luo.ming.delicipe.Models.Recipe;
import com.luo.ming.delicipe.Models.UserRecipeIngredient;
import com.luo.ming.delicipe.Models.UserRecipeStep;
import com.luo.ming.delicipe.Presenters.ScrollingActivityPresenter;
import com.luo.ming.delicipe.R;
import com.squareup.picasso.Picasso;

public class RecipeDisplayActivity extends AppCompatActivity implements ScrollingActivityPresenter.View {

    private ImageView toolbarImage;
    private ScrollingActivityPresenter presenter;
    private ImageButton btnCart;
    private TableLayout ingredientTableLayout,stepsTableLayout;
    private TextView txtServing,textTitle,textSteps,textCookingTime,textComment;
    private static int newServing;
    private Toolbar toolbar;
    private CheckBox checkBox;
    private Button btnGetDirection;


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
         toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        btnCart = (ImageButton)findViewById(R.id.btnCart);
        ingredientTableLayout = (TableLayout)findViewById(R.id.table_main);
        stepsTableLayout = findViewById(R.id.table_steps);
        txtServing = (TextView)findViewById(R.id.txtServing);
        toolbarImage = findViewById(R.id.toolbarimageview);
        textTitle = findViewById(R.id.text_recipe_title);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        textSteps = findViewById(R.id.txtSteps);
        btnGetDirection = findViewById(R.id.btnGetDirection);
        textCookingTime = findViewById(R.id.txtCookingTime);
        textComment = findViewById(R.id.txtComment);


        newServing = 4;

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle.containsKey(FavouritesRecyclerViewAdapter.FAVOURITE_RECYCLER_VIEW_MESSAGE)){
            Recipe recipe = (Recipe) bundle.getSerializable(FavouritesRecyclerViewAdapter.FAVOURITE_RECYCLER_VIEW_MESSAGE);

            presenter = new ScrollingActivityPresenter(this,this,recipe);
            presenter.displayFavouriteRecipe();

        } else if (bundle.containsKey(SearchRecyclerViewAdapter.RECIPE_ID_MESSAGE)){

            String recipeID = bundle.getString(SearchRecyclerViewAdapter.RECIPE_ID_MESSAGE);

            presenter = new ScrollingActivityPresenter(this,this);
            presenter.setUrl(recipeID);
            presenter.displayOnlineRecipe();

        } else if(bundle.containsKey(UserRecipeFragmentViewAdapter.USER_RECIPE_ADPATER_MESSAGE)){

            String userRecipeID = bundle.getString(UserRecipeFragmentViewAdapter.USER_RECIPE_ADPATER_MESSAGE);

            presenter = new ScrollingActivityPresenter(this,this,userRecipeID);
            presenter.displayUserRecipe();
        }



        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.saveAllIngredients();
            }
        });

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean checked = ((CheckBox)v).isChecked();
                if(checked){
                    checkBox.setButtonDrawable(R.drawable.ic_action_favourited_button);

                    presenter.saveFavouriteRecipe();

                    Toast.makeText(getApplication(), "Recipe Saved!", Toast.LENGTH_SHORT).show();

                } else {
                    checkBox.setButtonDrawable(R.drawable.ic_action_favourite);

                    Toast.makeText(getApplication(), "Recipe Unsaved!", Toast.LENGTH_SHORT).show();

                    presenter.unSaveFavouriteRecipe();
                }
            }
        });

        btnGetDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.displayDirectionsPage();
            }
        });

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
                .error(R.drawable.ic_launcher_foreground)
                .into(toolbarImage);
    }

    @Override
    public void displayOnlineIngredients(ArrayList<Ingredient> ingredients) {

        ingredientTableLayout.removeAllViews();

        for(int i=0;i<ingredients.size();i++){
            LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            ConstraintLayout row = (ConstraintLayout) inflater.inflate(R.layout.table_row_ingredient_api,null);

             TextView itemName = row.findViewById(R.id.text_ingredient_item);

             itemName.setText(ingredients.get(i).getIngredientItem());

            ingredientTableLayout.addView(row,i);

        }

    }

    @Override
    public void displayUserIngredients(ArrayList<UserRecipeIngredient> ingredients) {

        ingredientTableLayout.removeAllViews();

        for(int i=0;i<ingredients.size();i++){
            LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            ConstraintLayout row = (ConstraintLayout) inflater.inflate(R.layout.table_row_ingredient_api,null);

            TextView itemName = row.findViewById(R.id.text_ingredient_item);

            itemName.setText(ingredients.get(i).getName());

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

        //android.view.ViewRootImpl$CalledFromWrongThreadException: Only the original thread that created a view hierarchy can touch its views.
        if(bool){
            checkBox.setButtonDrawable(R.drawable.ic_action_favourited_button);
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
