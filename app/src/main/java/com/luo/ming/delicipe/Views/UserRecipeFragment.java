package com.luo.ming.delicipe.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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





    //TODO Challenge: Right now, your buttons do not behave intuitively because they do not change their appearance when they are pressed. Android has another type of Drawable called StateListDrawable which allows for a different graphic to be used depending on the state of the object. For this challenge problem, create a Drawable resource that changes the background of the ImageButton to the same color as the border when the state of the ImageButton is "pressed". You should also set the color of the text inside the ImageButton elements to a selector that makes it white when the button is "pressed".

    public UserRecipeFragment() {
        // Required empty public constructor
    }


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

        presenter = new UserRecipeFragmentPresenter(getActivity(),this );

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
