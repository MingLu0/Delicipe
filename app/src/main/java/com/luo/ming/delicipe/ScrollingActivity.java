package com.luo.ming.delicipe;

import android.content.Context;
import android.os.Bundle;
import android.content.Intent;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.luo.ming.delicipe.Models.Ingredient;
import com.luo.ming.delicipe.Models.Recipe;
import com.luo.ming.delicipe.Presenters.ScrollingActivityPresenter;
import com.squareup.picasso.Picasso;

public class ScrollingActivity extends AppCompatActivity implements ScrollingActivityPresenter.View {

    private ImageView recipeImg;
    private ImageView personImg;
    private List<String> ingredients;
    private ScrollingActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        presenter = new ScrollingActivityPresenter(this,this);


        recipeImg = (ImageView)findViewById(R.id.imgRecipe);





        Intent intent = getIntent();
        String recipeID = intent.getStringExtra("recipeID");
        presenter.setUrl(recipeID);
        Log.d("ScrollingActivity",recipeID);

        presenter.getRecipe();

        //init();

        //String imageLink = "https://static.food2fork.com/best_pizza_dough_recipe1b20.jpg";
      //  ingredients = intent.getStringArrayListExtra("ingredients");





    }


    public void init(){

        presenter.getRecipePhoto();
        presenter.getTableLayout();

    }

    @Override
    public void displayRecipePhoto(String imageLink) {

        Picasso.with(this)
                .load(imageLink)
                .error(R.drawable.ic_launcher_foreground)
                .fit()
                .into(recipeImg);

    }

    @Override
    public void displayTableLayout(ArrayList<Ingredient> ingredients) {

        TableLayout tableLayout = (TableLayout)findViewById(R.id.table_main);

        for(int i=0;i<ingredients.size();i++){
            LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout row = (LinearLayout) inflater.inflate(R.layout.table_row,null);
            TextView textCount = (TextView)row.findViewById(R.id.count);
            TextView textUnit = (TextView)row.findViewById(R.id.unit);
            TextView textIngredient = (TextView)row.findViewById(R.id.ingredient);

            textCount.setText(String.valueOf(ingredients.get(i).getCount()));
            textUnit.setText(ingredients.get(i).getUnit());
            textIngredient.setText(ingredients.get(i).getIngredient());

            tableLayout.addView(row,i);
        }

    }


}
