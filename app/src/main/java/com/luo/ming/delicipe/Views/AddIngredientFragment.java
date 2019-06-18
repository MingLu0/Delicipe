package com.luo.ming.delicipe.Views;


import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.luo.ming.delicipe.Models.Ingredient;
import com.luo.ming.delicipe.Models.UserRecipeIngredient;
import com.luo.ming.delicipe.Presenters.AddIngredientFragmentPresenter;
import com.luo.ming.delicipe.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddIngredientFragment extends Fragment implements AddIngredientFragmentPresenter.View {

    private Button addIngredientBtn;
    private TableLayout tableLayout;
    private AddIngredientFragmentPresenter presenter;
    private OnAddIngredientFragmentInteractionListener listener;
    private int rowCount;

    private AlertDialog.Builder inputDialogBuilder;
    private AlertDialog inputDialog;
    private LayoutInflater inflater;


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

        addIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inputDialogBuilder = new AlertDialog.Builder(getContext());

                inflater = LayoutInflater.from(getContext());
                final View view = inflater.inflate(R.layout.shopping_edit_popup, null);

                final TextInputLayout editItemNameInputLayout = view.findViewById(R.id.text_input_layout_edit_shopping_item_outlined);
                final TextInputEditText editItemNameInputText = view.findViewById(R.id.text_input_edit_text_edit_shopping_outlined);
                Button btnSave =  view.findViewById(R.id.edit_saveButton);

                inputDialogBuilder.setView(view);
                inputDialog = inputDialogBuilder.create();
                inputDialog.show();

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(! TextUtils.isEmpty(editItemNameInputText.getText())){

                            LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            CardView row = (CardView) inflater.inflate(R.layout.table_row_shopping,null);

                            TextView itemName = row.findViewById(R.id.txt_item);
                            itemName.setText(editItemNameInputText.getText());

                            tableLayout.addView(row);



                            inputDialog.dismiss();
                           // presenter.displayTableLayout();

                        } else{
                            editItemNameInputLayout.setError("Item name required");
                        }

                    }
                });
            }
        });
    }

    @Override
    public void displayTableLayout() {

        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CardView row = (CardView) inflater.inflate(R.layout.table_row_shopping,null);

//
//
//        deleteIngredientBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // row is your row, the parent of the clicked button
//                View row = (View) v.getParent();
//                // container contains all the rows, you could keep a variable somewhere else to the container which you can refer to here
//                ViewGroup container = ((ViewGroup)row.getParent());
//                // delete the row and invalidate your view so it gets redrawn
//                container.removeView(row);
//                container.invalidate();
//
//            }
//        });

        rowCount = tableLayout.getChildCount();
        tableLayout.addView(row,rowCount);

    }


    public void saveIngredientListInfo() {

        ArrayList<UserRecipeIngredient> ingredientList = new ArrayList<UserRecipeIngredient>();

        rowCount = tableLayout.getChildCount();

        for(int i=0; i<rowCount; i++){
             View row = tableLayout.getChildAt(i);
             EditText textIngredientName = row.findViewById(R.id.txt_item);
             Float count = 0.0f;
             String unit = null;
             String ingredientName = null;

             if(!TextUtils.isEmpty(textIngredientName.getText())){
                 ingredientName = textIngredientName.getText().toString();
             }

             //todo finish
//             UserRecipeIngredient ingredient = new UserRecipeIngredient(count,unit,ingredientName);
//             ingredientList.add(ingredient);

        }

        Bundle bundle = new Bundle();

        bundle.putParcelableArrayList(INGREDIENT_BUNDLE_TAG,ingredientList);

        listener.onAddIngredientFragmentInteraction(bundle);



    }


    public interface OnAddIngredientFragmentInteractionListener{

        void onAddIngredientFragmentInteraction(Bundle bundle);
    }

}
