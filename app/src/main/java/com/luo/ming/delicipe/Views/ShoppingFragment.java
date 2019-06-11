package com.luo.ming.delicipe.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.luo.ming.delicipe.Data.DatabaseHandler;
import com.luo.ming.delicipe.Helpers.SwipeController;
import com.luo.ming.delicipe.Helpers.SwipeControllerActions;
import com.luo.ming.delicipe.Models.Ingredient;
import com.luo.ming.delicipe.Presenters.SearchActivityPresenter;
import com.luo.ming.delicipe.Presenters.ShoppingFragmentPresenter;
import com.luo.ming.delicipe.R;
import com.luo.ming.delicipe.Views.ShoppingListRecyclerViewAdapter;


public class ShoppingFragment extends Fragment implements ShoppingFragmentPresenter.View {

    private RecyclerView recyclerView;
    private ShoppingListRecyclerViewAdapter recyclerViewAdapter;
    private ShoppingFragmentPresenter presenter;

    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    private SwipeController swipeController;

    ItemTouchHelper itemTouchHelper;


    public ShoppingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        recyclerView = (RecyclerView) view.findViewById(R.id.shoppingRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); //?

        presenter = new ShoppingFragmentPresenter(getContext(),this);//?

        recyclerViewAdapter = new ShoppingListRecyclerViewAdapter(getContext(),presenter);

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onEditClicked(int position) {
                presenter.editShoppingItem(position);
            }

            @Override
            public void onDeleteClicked(int position) {
                super.onDeleteClicked(position);
            }
        }, getContext());
        itemTouchHelper = new ItemTouchHelper(swipeController);


        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }


    @Override
    public void notifyShoppingItemRemoved(int position) {

        recyclerViewAdapter.notifyItemRemoved(position);

    }

    @Override
    public void notifyShoppingItemChanged(int position,Ingredient newIngredient) {

        recyclerViewAdapter.notifyItemChanged(position,newIngredient);

    }

    @Override
    public void getEditedItem(final Ingredient editIngredit, final int position) {

        alertDialogBuilder = new AlertDialog.Builder(getContext());

        inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.edit_popup, null);

        final EditText txtCount = (EditText) view.findViewById(R.id.edit_count);
        final EditText txtUnit = (EditText) view.findViewById(R.id.edit_unit);
        final EditText txtItemName = (EditText) view.findViewById(R.id.edit_itemName);
        final TextView title = (TextView) view.findViewById(R.id.tile);
        Button btnSave = (Button) view.findViewById(R.id.edit_saveButton);

        txtCount.setText(String.valueOf(editIngredit.getCount()));
        txtUnit.setText(editIngredit.getUnit());
        txtItemName.setText(editIngredit.getIngredient());

        alertDialogBuilder.setView(view);
        dialog = alertDialogBuilder.create();
        dialog.show();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txtCount.getText().toString().isEmpty()||!txtItemName.getText().toString().isEmpty()
                        ||!txtUnit.getText().toString().isEmpty()){

                    Ingredient newIngredient = new Ingredient();
                    newIngredient.setID(editIngredit.getID());
                    newIngredient.setCount(Double.valueOf(txtCount.getText().toString()));
                    newIngredient.setUnit(txtUnit.getText().toString());
                    newIngredient.setIngredient(txtItemName.getText().toString());
                    presenter.saveUpdatedItem(newIngredient,position);
                    dialog.dismiss();
                }
            }
        });

    }
}
