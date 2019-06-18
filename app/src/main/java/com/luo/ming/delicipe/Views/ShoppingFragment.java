package com.luo.ming.delicipe.Views;

import android.graphics.Canvas;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.luo.ming.delicipe.Helpers.SwipeController;
import com.luo.ming.delicipe.Helpers.SwipeControllerActions;
import com.luo.ming.delicipe.Models.Ingredient;
import com.luo.ming.delicipe.Presenters.ShoppingFragmentPresenter;
import com.luo.ming.delicipe.R;


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



        recyclerView = view.findViewById(R.id.shoppingRecyclerView);
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

        presenter = new ShoppingFragmentPresenter(getActivity(),this);
        recyclerViewAdapter = new ShoppingListRecyclerViewAdapter(getContext(),presenter);
        recyclerView.setAdapter(recyclerViewAdapter);




    }

    @Override
    public void notifyShoppingItemChanged(int position,Ingredient newIngredient) {

        recyclerViewAdapter.notifyItemChanged(position,newIngredient);


    }

    @Override
    public void getEditedItem(final Ingredient editIngredit, final int position) {

        alertDialogBuilder = new AlertDialog.Builder(getContext());

        inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.shopping_edit_popup, null);

        final TextInputLayout editItemNameInputLayout = view.findViewById(R.id.text_input_layout_edit_shopping_item_outlined);
        final TextInputEditText editItemNameInputText = view.findViewById(R.id.text_input_edit_text_edit_shopping_outlined);
        Button btnSave =  view.findViewById(R.id.edit_saveButton);

        editItemNameInputText.setText(editIngredit.getIngredientItem());

        alertDialogBuilder.setView(view);
        dialog = alertDialogBuilder.create();
        dialog.show();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(! TextUtils.isEmpty(editItemNameInputText.getText())){

                    Ingredient newIngredient = new Ingredient();
                    newIngredient.setID(editIngredit.getID());
                    newIngredient.setIngredientItem(editItemNameInputText.getText().toString());
                    presenter.saveUpdatedItem(newIngredient,position);
                    dialog.dismiss();

                } else{
                    editItemNameInputLayout.setError("Item name required");
                }

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        presenter = new ShoppingFragmentPresenter(getContext(),this);
        recyclerViewAdapter = new ShoppingListRecyclerViewAdapter(getActivity(),presenter);

    }
}
