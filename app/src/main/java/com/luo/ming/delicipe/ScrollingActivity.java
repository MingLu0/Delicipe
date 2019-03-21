package com.luo.ming.delicipe;

import android.os.Bundle;
import android.content.Intent;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ScrollingActivity extends AppCompatActivity {

    private ImageView recipeImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recipeImg = (ImageView)findViewById(R.id.imgRecipe);

        Intent intent = getIntent();
        String imageLink = intent.getStringExtra("imageLink");

        Log.d("scroll imagelink",imageLink);

        Picasso.with(this)
                .load(imageLink)
                .error(R.drawable.ic_launcher_foreground)
                .fit()
                .into(recipeImg);


    }
}
