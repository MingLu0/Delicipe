package com.luo.ming.delicipe.Views;

import android.content.Context;
import android.os.Bundle;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.luo.ming.delicipe.Models.Ingredient;
import com.luo.ming.delicipe.Models.Recipe;
import com.luo.ming.delicipe.Presenters.ScrollingActivityPresenter;
import com.luo.ming.delicipe.R;
import com.squareup.picasso.Picasso;

public class ScrollingActivity extends AppCompatActivity implements ScrollingActivityPresenter.View {

    private ImageView recipeImg;
    private ImageView personImg;
    private ImageView toolbarimage;
    private List<String> ingredients;
    private ScrollingActivityPresenter presenter;
    private ImageButton btnCart;
    private TableLayout tableLayout;
    private ImageButton btnPlus;
    private ImageButton btnMinus;
    private TextView txtServing,textTitle;
    private static int newServing;
    private Toolbar toolbar;
    private CheckBox checkBox;

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
        tableLayout = (TableLayout)findViewById(R.id.table_main);
        txtServing = (TextView)findViewById(R.id.txtServing);
        toolbarimage = findViewById(R.id.toolbarimageview);
        textTitle = findViewById(R.id.text_recipe_title);
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        newServing = 4;

        Intent intent = getIntent();
        String recipeID = intent.getStringExtra(SearchRecyclerViewAdapter.RECIPE_ID_MESSAGE);
        Recipe recipe = (Recipe) intent.getSerializableExtra(FavouritesRecyclerViewAdapter.FAVOURITE_RECYCLER_VIEW_MESSAGE);

        if(recipeID != null){

            presenter = new ScrollingActivityPresenter(this,this);
            presenter.setUrl(recipeID);
            Log.d("ScrollingActivity",recipeID);
            presenter.getRecipe();



        } else if (recipe != null){
            presenter = new ScrollingActivityPresenter(this,this,recipe);
            presenter.displayRecipeTitle();
            presenter.getRecipePhoto();
            presenter.getTableLayout();
            presenter.displayFavouriteButton();
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

    }


    @Override
    public void displayRecipePhoto(String imageLink) {


        Picasso.with(this)
                .load(imageLink)
                .error(R.drawable.ic_launcher_foreground)
                .fit()
                .into(toolbarimage);



    }

    @Override
    public void displayTableLayout(ArrayList<Ingredient> ingredients) {

        tableLayout.removeAllViews();

        for(int i=0;i<ingredients.size();i++){
            LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            LinearLayout row = (LinearLayout) inflater.inflate(R.layout.table_row_ingredient_api,null);

             TextView itemName = row.findViewById(R.id.text_ingredient_item);

             itemName.setText(ingredients.get(i).getIngredientItem());

            tableLayout.addView(row,i);

        }

    }

    @Override
    public void updateCountInTableLayout(ArrayList<Ingredient> ingredients) {

        for(int i=0;i<ingredients.size();i++){
            TableRow row = (TableRow)tableLayout.getChildAt(i);
            TextView txtCount = row.findViewById(R.id.count);
            txtCount.setText(String.valueOf(ingredients.get(i).getCount()));
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

        //android.view.ViewRootImpl$CalledFromWrongThreadException: Only the original thread that created a view hierarchy can touch its views.
        if(bool){
            checkBox.setButtonDrawable(R.drawable.ic_action_favourited_button);
        } else {
            checkBox.setButtonDrawable(R.drawable.ic_action_favourite);
        }

    }

    @Override
    public void popupToast(String text) {

        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();

    }




}
