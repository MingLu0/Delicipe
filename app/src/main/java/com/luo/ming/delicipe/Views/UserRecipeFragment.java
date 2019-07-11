package com.luo.ming.delicipe.Views;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luo.ming.delicipe.Presenters.UserRecipeFragmentPresenter;
import com.luo.ming.delicipe.R;



public class UserRecipeFragment extends Fragment implements UserRecipeFragmentPresenter.View {

    private RecyclerView recyclerView;
    private UserRecipeFragmentPresenter presenter;
    private UserRecipeFragmentViewAdapter adapter;
    private FloatingActionButton addRecipeButton;

    public UserRecipeFragment() {
        // Required empty public constructor
    }

    //todo fix showing lagging problem
    //todo add delete function

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_recipe, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        recyclerView = view.findViewById(R.id.recyclerView_user_recipe);
        addRecipeButton = view.findViewById(R.id.addRecipe);

        presenter = new UserRecipeFragmentPresenter(this );
        adapter = new UserRecipeFragmentViewAdapter(presenter,getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        recyclerView.getAdapter().notifyDataSetChanged();

        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddRecipeActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void notifyDataSetHasChanged() {

        recyclerView.getAdapter().notifyDataSetChanged();

    }
}
