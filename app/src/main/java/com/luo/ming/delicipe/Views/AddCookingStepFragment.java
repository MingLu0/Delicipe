package com.luo.ming.delicipe.Views;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.luo.ming.delicipe.Presenters.AddCookingStepPresenter;
import com.luo.ming.delicipe.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCookingStepFragment extends Fragment implements AddCookingStepPresenter.View {

    private TableLayout tableLayout;
    private Button addStepButton;
    private AddCookingStepPresenter presenter;


    public AddCookingStepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_cook_step, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tableLayout = view.findViewById(R.id.tableLayout_steps);
        addStepButton = view.findViewById(R.id.add_step_button);

        presenter = new AddCookingStepPresenter(getActivity(),this);

        addStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.displayTableLayout();
            }
        });

    }

    @Override
    public void displayTableLayout() {

        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout row = (LinearLayout) inflater.inflate(R.layout.table_row_step,null);
//        EditText textCount = row.findViewById(R.id.edit_text_unit);
//        EditText textName = row.findViewById(R.id.edit_text_ingredient_name);

        tableLayout.addView(row,0);

    }
}
