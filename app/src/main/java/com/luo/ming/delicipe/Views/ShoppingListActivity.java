package com.luo.ming.delicipe.Views;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.luo.ming.delicipe.Models.Ingredient;
import com.luo.ming.delicipe.Presenters.ShoppingFragmentPresenter;
import com.luo.ming.delicipe.R;

public class ShoppingListActivity extends AppCompatActivity implements ShoppingFragmentPresenter.View ,IngredientInputDialogView.OnButtonStateClickedListener{

    private RecyclerView recyclerView;
    private ShoppingListRecyclerViewAdapter recyclerViewAdapter;
    private ShoppingFragmentPresenter presenter;

    private int position;
    private Ingredient editIngredit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.shoppingRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //?

        presenter = new ShoppingFragmentPresenter(this,this);//?

        recyclerViewAdapter = new ShoppingListRecyclerViewAdapter(this,presenter);

        recyclerView.setAdapter(recyclerViewAdapter);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.shopping_options_menu,menu);


        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.add_recipe:
                IngredientInputDialogView dialogView = new IngredientInputDialogView(this);
                dialogView.setListener(this);

            case R.id.delete_all_ingredients:
                presenter.deleteAllShoppingItems();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setRecyclerViewAdapter() {

        recyclerView.setAdapter(recyclerViewAdapter);

    }

    @Override
    public void refreshRecyclerViewList() {

        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void notifyShoppingItemRemoved(int position) {

        recyclerViewAdapter.notifyItemRemoved(position);

    }

    @Override
    public void notifyShoppingItemChanged() {
        resetPresenterAndAdapter();

    }

    public void resetPresenterAndAdapter(){
        presenter = new ShoppingFragmentPresenter(this,this);
        recyclerViewAdapter = new ShoppingListRecyclerViewAdapter(this,presenter);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void getEditedItem(final Ingredient editIngredit, final int position) {

        this.position = position;
        this.editIngredit = editIngredit;

        IngredientInputDialogView dialog = new IngredientInputDialogView(this,editIngredit.getIngredientItem());
        dialog.setListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        resetPresenterAndAdapter();

    }

    @Override
    public void onSaveClicked(String item) {

        if(editIngredit!=null){

            Ingredient newIngredient = new Ingredient();
            newIngredient.setID(editIngredit.getID());
            newIngredient.setIngredientItem(item);
            presenter.saveUpdatedItem(newIngredient,position);
            editIngredit = null;

        } else {
            presenter.saveNewItem(item);
            refreshRecyclerViewList();
        }



    }

    @Override
    public void onCancelClicked() {

    }
}
