package com.luo.ming.delicipe.Views;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
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

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.shoppingRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //?

        presenter = new ShoppingFragmentPresenter(this,this);//?

        recyclerViewAdapter = new ShoppingListRecyclerViewAdapter(this,presenter);

        recyclerView.setAdapter(recyclerViewAdapter);
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

        IngredientInputDialogView dialog=new IngredientInputDialogView(this);
        dialog.setListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        resetPresenterAndAdapter();

    }

    @Override
    public void onSaveClicked(String item) {

        Ingredient newIngredient = new Ingredient();
        newIngredient.setID(editIngredit.getID());
        newIngredient.setIngredientItem(item);
        presenter.saveUpdatedItem(newIngredient,position);

    }

    @Override
    public void onCancelClicked() {

    }
}
