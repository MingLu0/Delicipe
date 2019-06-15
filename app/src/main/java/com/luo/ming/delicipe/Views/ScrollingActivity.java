package com.luo.ming.delicipe.Views;

import android.content.Context;
import android.os.Bundle;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
    private TextView txtServing;
    private static int newServing;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling2);
         toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new ScrollingActivityPresenter(this,this);




        recipeImg = (ImageView)findViewById(R.id.imgRecipe);
        btnCart = (ImageButton)findViewById(R.id.btnCart);
        tableLayout = (TableLayout)findViewById(R.id.table_main);
        btnPlus = (ImageButton)findViewById(R.id.btnPlus);
        btnMinus = (ImageButton)findViewById(R.id.btnMinus);
        txtServing = (TextView)findViewById(R.id.txtServing);
        toolbarimage = findViewById(R.id.toolbarimageview);





        newServing = 4;

        Intent intent = getIntent();
        String recipeID = intent.getStringExtra(SearchRecyclerViewAdapter.RECIPE_ID_MESSAGE);
        String recipeTitle = intent.getStringExtra(SearchRecyclerViewAdapter.RECIPE_TITLE_MESSAGE);
        presenter.setUrl(recipeID);
        Log.d("ScrollingActivity",recipeID);

        getSupportActionBar().setTitle(recipeTitle);
        //getSupportActionBar().setHideOnContentScrollEnabled(true);

        presenter.getRecipe();

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.saveAllIngredients();
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++newServing;
                Log.d("serving plus",String.valueOf(newServing));
                presenter.updateIngredientCount(newServing);
                presenter.updateServing(newServing);
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                --newServing;
                if(newServing>=1){
                    Log.d("serving minus",String.valueOf(newServing));
                    presenter.updateIngredientCount(newServing);
                    presenter.updateServing(newServing);
                } else {
                    Log.d("serving minus",String.valueOf(newServing));
                    newServing =1;
                    //presenter.updateServing(newServing);
                }
            }
        });

    }




    @Override
    public void displayRecipePhoto(String imageLink) {

//        Picasso.with(this)
//                .load(imageLink)
//                .error(R.drawable.ic_launcher_foreground)
//                .fit()
//                .into(recipeImg);

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
//            ConstraintLayout row = (ConstraintLayout) inflater.inflate(R.layout.table_row,null);
//            TextView textCount = (TextView)row.findViewById(R.id.count);
//            TextView textUnit = (TextView)row.findViewById(R.id.unit);
//            TextView textIngredient = (TextView)row.findViewById(R.id.ingredient);
//
//            textCount.setText(String.valueOf(ingredients.get(i).getCount()));
//            textUnit.setText(ingredients.get(i).getUnit());
//            textIngredient.setText(ingredients.get(i).getIngredient());
//
//            tableLayout.addView(row,i);

            LinearLayout row = (LinearLayout) inflater.inflate(R.layout.table_row_ingredient_api,null);
//            TextView textCount = (TextView)row.findViewById(R.id.count);
//            TextView textUnit = (TextView)row.findViewById(R.id.unit);
//            TextView textIngredient = (TextView)row.findViewById(R.id.ingredient);

             TextView itemName = (TextView)row.findViewById(R.id.text_ingredient_item);

             itemName.setText(ingredients.get(i).getIngredientItem());


//            textCount.setText(String.valueOf(ingredients.get(i).getCount()));
//            textUnit.setText(ingredients.get(i).getUnit());
//            textIngredient.setText(ingredients.get(i).getIngredient());

            tableLayout.addView(row,i);

        }

    }

    @Override
    public void updateCountInTableLayout(ArrayList<Ingredient> ingredients) {

        for(int i=0;i<ingredients.size();i++){
            TableRow row = (TableRow)tableLayout.getChildAt(i);
            TextView txtCount = (TextView)row.findViewById(R.id.count);
            txtCount.setText(String.valueOf(ingredients.get(i).getCount()));
        }

    }

    @Override
    public void updateServingSize(int newSergving) {

        txtServing.setText(String.valueOf(newSergving));

    }

    @Override
    public void displayRecipeTitle(String recipeTitle) {



//        getSupportActionBar().setTitle(recipeTitle);
//        Log.d("ScrollingActivitytitle",recipeTitle);

        toolbar.setTitle(recipeTitle);

    }

    @Override
    public void popupToast(String text) {

        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();

    }




}
