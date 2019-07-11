package com.luo.ming.delicipe.Views;


import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.luo.ming.delicipe.Models.UserRecipeIngredient;
import com.luo.ming.delicipe.Presenters.AddIngredientFragmentPresenter;
import com.luo.ming.delicipe.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddIngredientFragment extends Fragment implements AddIngredientFragmentPresenter.View,
IngredientInputDialogView.OnButtonStateClickedListener{

    private Button addIngredientBtn;
    private TableLayout tableLayout;
    private AddIngredientFragmentPresenter presenter;
    private OnAddIngredientFragmentInteractionListener listener;
    private int rowCount;


    public static final String INGREDIENT_BUNDLE_TAG = "package com.luo.ming.delicipe." +
            "Views.AddIngredientFragment";


    public AddIngredientFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof OnAddIngredientFragmentInteractionListener){
            listener = (OnAddIngredientFragmentInteractionListener)context;
        } else {
            throw new RuntimeException(context.toString()+
                    "must implement OnAddIngredientFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_ingredient, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        tableLayout = view.findViewById(R.id.tableLayout_ingredient);
        presenter = new AddIngredientFragmentPresenter(this);
        addIngredientBtn = view.findViewById(R.id.button_add_ingredient);

        if(savedInstanceState!=null){
            ArrayList<UserRecipeIngredient>ingredients = savedInstanceState.getParcelableArrayList("ingredients");
            presenter.displayTableRows(ingredients);
        }

        addIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IngredientInputDialogView dialog = new IngredientInputDialogView(getContext());
                dialog.setListener(AddIngredientFragment.this);

            }
        });
    }

    @Override
    public void addNewTableRow() {

        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CardView row = (CardView) inflater.inflate(R.layout.table_row_shopping,null);

        rowCount = tableLayout.getChildCount();
        tableLayout.addView(row,rowCount);

    }

    @Override
    public void addIngredientToLayout(String item ) {

        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CardView row = (CardView) inflater.inflate(R.layout.table_row_shopping,null);

        final TextView itemName = row.findViewById(R.id.txt_item);
        itemName.setText(item);

        final CheckBox checkBox = row.findViewById(R.id.chk_selected);

        ImageButton editButton = row.findViewById(R.id.button_edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IngredientInputDialogView dialogView = new IngredientInputDialogView(AddIngredientFragment.this.getActivity(),itemName.getText().toString());
                dialogView.setListener(AddIngredientFragment.this);
                View row = (View) v.getParent().getParent();
                int position = tableLayout.indexOfChild(row);
                presenter.setItemPosition(position);

            }
        });

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkBox.isChecked()){
                    View row = (View)v.getParent().getParent();
                    tableLayout.removeView(row);
                    tableLayout.invalidate();
                }

            }
        });

        tableLayout.addView(row);

    }

    @Override
    public void editIngredient(String item, int position) {

        View row = tableLayout.getChildAt(position);

        if(row!=null){
            TextView itemText = row.findViewById(R.id.txt_item);
            itemText.setText(item);
            tableLayout.invalidate();
        }
    }

    @Override
    public ArrayList<UserRecipeIngredient> getIngredientsFromTableLayout() {

        ArrayList<UserRecipeIngredient> ingredientList = new ArrayList<>();

        rowCount = tableLayout.getChildCount();

        for(int i=0; i<rowCount; i++){
            View row = tableLayout.getChildAt(i);
            TextView textIngredientName = row.findViewById(R.id.txt_item);
            String ingredientName = null;

            if(!TextUtils.isEmpty(textIngredientName.getText())){
                ingredientName = textIngredientName.getText().toString();
            }

            UserRecipeIngredient ingredient = new UserRecipeIngredient(ingredientName);
            ingredientList.add(ingredient);
        }
        return ingredientList;
    }


    @Override
    public void onSaveClicked(String item) {
        presenter.handleSaveButtonClickedEvent(item);
    }

    @Override
    public void onCancelClicked() {

    }

    public void sendDataBackToActivity(){

        ArrayList<UserRecipeIngredient> ingredientList = presenter.getAllIngredientsFromTB();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(INGREDIENT_BUNDLE_TAG,ingredientList);
        listener.onAddIngredientFragmentInteraction(bundle);

    }


    public interface OnAddIngredientFragmentInteractionListener{

        void onAddIngredientFragmentInteraction(Bundle bundle);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if(tableLayout.getChildCount()>0){
            ArrayList<UserRecipeIngredient> ingredientList = getIngredientsFromTableLayout();
            outState.putParcelableArrayList("ingredients",ingredientList);
        }
    }
}
