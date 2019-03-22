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
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.squareup.picasso.Picasso;

public class ScrollingActivity extends AppCompatActivity {

    private ImageView recipeImg;
    private ImageView personImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recipeImg = (ImageView)findViewById(R.id.imgRecipe);
        //personImg = (ImageView)findViewById(R.id.imgPerson);

        init();


        Intent intent = getIntent();
        //String imageLink = intent.getStringExtra("imageLink");

        String imageLink = "https://static.food2fork.com/best_pizza_dough_recipe1b20.jpg";

                Log.d("scroll imagelink",imageLink);

        Picasso.with(this)
                .load(imageLink)
                .error(R.drawable.ic_launcher_foreground)
                .fit()
                .into(recipeImg);


    }


    public void init(){

        TableLayout tableLayout = (TableLayout)findViewById(R.id.table_main);

        for(int i=0;i<6;i++){
            LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            RelativeLayout row = (RelativeLayout)inflater.inflate(R.layout.table_row,null);
            tableLayout.addView(row,i);
        }


    }

}
