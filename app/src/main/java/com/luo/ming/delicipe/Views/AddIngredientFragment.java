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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.luo.ming.delicipe.Models.Recipe;
import com.luo.ming.delicipe.Models.RecipeIngredient;
import com.luo.ming.delicipe.Presenters.AddIngredientFragmentPresenter;
import com.luo.ming.delicipe.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddIngredientFragment extends Fragment implements AddIngredientFragmentPresenter.View {

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

        Spinner spinner = row.findViewById(R.id.spinner_unit);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(),R.array.units_array,android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button deleteIngredientBtn = row.findViewById(R.id.button_delete_ingredient);

        deleteIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // row is your row, the parent of the clicked button
                View row = (View) v.getParent();
                // container contains all the rows, you could keep a variable somewhere else to the container which you can refer to here
                ViewGroup container = ((ViewGroup)row.getParent());
                // delete the row and invalidate your view so it gets redrawn
                container.removeView(row);
                container.invalidate();

            }
        });

//        EditText textCount = row.findViewById(R.id.edit_text_unit);
//        EditText textName = row.findViewById(R.id.edit_text_ingredient_name);

        rowCount = tableLayout.getChildCount();
        tableLayout.addView(row,rowCount);

    }


    public void saveIngredientListInfo() {

        ArrayList<RecipeIngredient> ingredientList = new ArrayList<RecipeIngredient>();

        rowCount = tableLayout.getChildCount();

        for(int i=0; i<rowCount; i++){
             View row = tableLayout.getChildAt(i);
             Spinner spinner = row.findViewById(R.id.spinner_unit);
             EditText textCount = row.findViewById(R.id.text_unit);
             EditText textIngredientName = row.findViewById(R.id.text_ingredient_name);

             Float count = Float.valueOf(textCount.getText().toString());
             String unit = spinner.getSelectedItem().toString();
             String ingredientName = textIngredientName.getText().toString();

             RecipeIngredient ingredient = new RecipeIngredient(count,unit,ingredientName);
             ingredientList.add(ingredient);

        }

        Bundle bundle = new Bundle();

        bundle.putParcelableArrayList(INGREDIENT_BUNDLE_TAG,ingredientList);

        listener.onAddIngredientFragmentInteraction(bundle);



    }



    public interface OnAddIngredientFragmentInteractionListener{

        void onAddIngredientFragmentInteraction(Bundle bundle);
    }

}
