package com.luo.ming.delicipe.Views;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.luo.ming.delicipe.Presenters.AddIngredientFragmentPresenter;
import com.luo.ming.delicipe.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddIngredientFragment extends Fragment implements AddIngredientFragmentPresenter.View {

    //private FloatingActionButton addBtn;
    private Button addIngredientBtn;
    private TableLayout tableLayout;
    private AddIngredientFragmentPresenter presenter;

    public AddIngredientFragment() {
        // Required empty public constructor
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

        presenter = new AddIngredientFragmentPresenter(this,getActivity());

        addIngredientBtn = view.findViewById(R.id.button_add_ingredient);

//        addBtn = view.findViewById(R.id.floatingActionButtonAddIngredient);
//
        addIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                presenter.displayTableLayout();
            }
        });
    }

    @Override
    public void displayTableLayout() {

        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout row = (LinearLayout) inflater.inflate(R.layout.table_row_ingredient,null);

        EditText textCount = row.findViewById(R.id.edit_text_unit);
        EditText textName = row.findViewById(R.id.edit_text_ingredient_name);


        tableLayout.addView(row,0);

    }
}
