package com.luo.ming.delicipe.Views;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luo.ming.delicipe.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCookStepFragment extends Fragment {


    public AddCookStepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_cook_step, container, false);
    }

}
