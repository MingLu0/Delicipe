package com.luo.ming.delicipe.Views;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;


import com.luo.ming.delicipe.Presenters.SearchActivityPresenter;
import com.luo.ming.delicipe.R;

public class SearchActivity extends AppCompatActivity implements SearchActivityPresenter.View{

    private SearchView searchView;
    private RecyclerView recyclerView;
    private SearchRecyclerViewAdapter searchRecyclerViewAdapter;
    private SearchActivityPresenter presenter;
    private  Context cxt;

    private String mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cxt = this.getApplicationContext();


        recyclerView = (RecyclerView)findViewById(R.id.search_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        presenter = new SearchActivityPresenter(this,this);

        Intent intent = getIntent();
        String keyword = intent.getStringExtra("keyword");

        presenter.setUrl(keyword);

        searchRecyclerViewAdapter = new SearchRecyclerViewAdapter(presenter,this);

        presenter.getRecipesList();

        searchRecyclerViewAdapter.notifyDataSetChanged();

        //handleIntent(getIntent());



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
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.options_menu,menu);

        //TODO SEARCHABLE NOT WORKING YET
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        //CharSequence query = searchView.getQuery();// get the query string currently in the text field

        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("search recipe");

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


    //TODO GOOGLE DOUCMENTATION BELOW DOES NOT WORK

//    @Override
//    protected void onNewIntent(Intent intent) {
//        handleIntent(intent);
//    }
//
//    public void handleIntent(Intent intent){
//        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
//            String query = intent.getStringExtra(SearchManager.QUERY);
//
//            Log.d("searchactivity query",query);
//        }
//    }


}
