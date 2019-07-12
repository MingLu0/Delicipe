package com.luo.ming.delicipe.Views;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

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

        presenter = new ShoppingFragmentPresenter(this);//?

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
                break;

            case R.id.delete_all_ingredients:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.alert_dialog_shopping_title)
                        .setMessage(R.string.alert_dialog_shopping_message)
                        .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                doPositiveClick();
                            }
                        })
                        .setNegativeButton(R.string.alert_dialog_cancel,null)
                        .show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void doPositiveClick(){

        presenter.deleteAllShoppingItems();

    }

    @Override
    public void notifyShoppingItemRemoved(int position) {

        recyclerViewAdapter.notifyItemRemoved(position);

    }


    @Override
    public void resetPresenterAndAdapter(){
        presenter = new ShoppingFragmentPresenter(this);
        recyclerViewAdapter = new ShoppingListRecyclerViewAdapter(this,presenter);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void notifyShoppingDataChanged() {

        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void displayItemAllDeletedMessage() {

        Toast.makeText(this,"All shopping item has been deleted",Toast.LENGTH_SHORT).show();
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
        }
    }

    @Override
    public void onCancelClicked() {

    }
}
