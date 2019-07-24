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
import android.widget.Toast;

import com.luo.ming.delicipe.Presenters.UserRecipeFragmentPresenter;
import com.luo.ming.delicipe.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class UserRecipeFragment extends Fragment implements UserRecipeFragmentPresenter.View {

    @BindView(R.id.recyclerView_user_recipe) RecyclerView recyclerView;
    @BindView(R.id.addRecipe) FloatingActionButton addRecipeButton;
    private UserRecipeFragmentPresenter presenter;
    private UserRecipeFragmentViewAdapter adapter;

    public UserRecipeFragment() {
        // Required empty public constructor
    }

    //todo fix showing lagging problem

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_user_recipe, container, false);
         ButterKnife.bind(this,view);
         return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {


        presenter = new UserRecipeFragmentPresenter(this );
        adapter = new UserRecipeFragmentViewAdapter(presenter,getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.getAdapter().notifyDataSetChanged();

    }

    @OnClick(R.id.addRecipe)
    public void addUserRecipe(){
        Intent intent = new Intent(getActivity(),AddRecipeActivity.class);
        startActivity(intent);
    }

    @Override
    public void notifyDataSetHasChanged() {

        recyclerView.getAdapter().notifyDataSetChanged();

    }

    @Override
    public void displayRecipeDeletedMessage() {

        Toast.makeText(getActivity(),"Recipe has been deleted successfully",Toast.LENGTH_SHORT).show();
    }
}
