package com.luo.ming.delicipe.Views;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;


import com.google.android.material.snackbar.Snackbar;
import com.luo.ming.delicipe.Presenters.SearchActivityPresenter;
import com.luo.ming.delicipe.R;

public class SearchActivity extends AppCompatActivity implements SearchActivityPresenter.View{

    private RecyclerView recyclerView;
    private SearchRecyclerViewAdapter searchRecyclerViewAdapter;
    private SearchActivityPresenter presenter;
    private String mQuery;
    private CoordinatorLayout coordinatorLayout;
    private SearchView searchView;

    private static final String LOG_TAG = SearchActivity.class.getSimpleName();

    //todo backbutton on the recipe page will crash the app after another search
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.search_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        coordinatorLayout = findViewById(R.id.coordinatorLayout_search_activity);
        presenter = new SearchActivityPresenter(this,this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle!=null){
            if(bundle.containsKey(TabbedActivity.MESSAGE_FROM_TABBEDACTIVITY_TAG)){
                String keyword = bundle.getString(TabbedActivity.MESSAGE_FROM_TABBEDACTIVITY_TAG);

                presenter.setUrl(keyword);
                searchRecyclerViewAdapter = new SearchRecyclerViewAdapter(presenter,this);
                presenter.getRecipesList();
                searchRecyclerViewAdapter.notifyDataSetChanged();
            }
        }

    }


    @Override
    public void refreshRecipeList() {

        searchRecyclerViewAdapter.notifyDataSetChanged();

    }

    @Override
    public void setRecyclerViewAdapter() {
        recyclerView.setAdapter(searchRecyclerViewAdapter);

    }

    @Override
    public void displayInputErrorSnackBar() {

        Snackbar snackbar = Snackbar.make(coordinatorLayout,"No results found for this keyword",
                Snackbar.LENGTH_INDEFINITE);

        snackbar.setAction("Retry", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        snackbar.show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_search,menu);

        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();


        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Search Online Recipes");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                mQuery = query;
                Log.d("query",mQuery);

                presenter.setUrl(mQuery);

                searchRecyclerViewAdapter = new SearchRecyclerViewAdapter(presenter,getApplicationContext());

                presenter.getRecipesList();

                searchRecyclerViewAdapter.notifyDataSetChanged();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();

    }




}
