package com.luo.ming.delicipe.Views;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luo.ming.delicipe.Presenters.RecommendationFragmentPresenter;
import com.luo.ming.delicipe.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendationFragment extends Fragment implements RecommendationFragmentPresenter.View{


    public RecommendationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recommendations, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_recommendation);
        recyclerView.setHasFixedSize(true);

        RecommendationFragmentPresenter presenter = new RecommendationFragmentPresenter( this);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        RecommendedRecyclerViewAdapter adapter = new RecommendedRecyclerViewAdapter(presenter,getActivity());
        recyclerView.setAdapter(adapter);

    }
}
