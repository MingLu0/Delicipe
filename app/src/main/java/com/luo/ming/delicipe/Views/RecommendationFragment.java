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

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,
                GridLayoutManager.HORIZONTAL, false);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position % 3 ==2 ? 2: 1;
            }
        });

        RecommendationFragmentPresenter presenter = new RecommendationFragmentPresenter(getActivity(), this);

        recyclerView.setLayoutManager(gridLayoutManager);
        StaggeredRecipeRecyclerViewAdapter adapter = new StaggeredRecipeRecyclerViewAdapter(getActivity(),presenter);
        recyclerView.setAdapter(adapter);

        int largePadding = getResources().getDimensionPixelSize(R.dimen.staggered_recipe_spacing_large);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.staggered_recipe_spacing_small);

        recyclerView.addItemDecoration(new RecipeItemDecoration(largePadding,smallPadding));

    }
}
