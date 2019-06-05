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
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import com.luo.ming.delicipe.Presenters.SearchActivityPresenter;
import com.luo.ming.delicipe.R;



public class SearchFragment extends Fragment implements SearchActivityPresenter.View{

    private SearchView searchView;
    private RecyclerView recyclerView;
    private SearchRecyclerViewAdapter searchRecyclerViewAdapter;
    private SearchActivityPresenter presenter;
    private FloatingActionButton createBtn;

    private EditText editText;
    private Button button;

    private String mQuery;


    //TODO SAVE INSTANCE STATE

    //TODO Challenge: Right now, your buttons do not behave intuitively because they do not change their appearance when they are pressed. Android has another type of Drawable called StateListDrawable which allows for a different graphic to be used depending on the state of the object. For this challenge problem, create a Drawable resource that changes the background of the ImageButton to the same color as the border when the state of the ImageButton is "pressed". You should also set the color of the text inside the ImageButton elements to a selector that makes it white when the button is "pressed".

    public SearchFragment() {
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
        return inflater.inflate(R.layout.fragment_my_recipe, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_my_recipe);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        createBtn = view.findViewById(R.id.addRecipe);
        presenter = new SearchActivityPresenter(this,getContext());

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddRecipeActivity.class);
                startActivity(intent);
            }
        });




    }




    @Override
    public void refreshRecipeList() {
        searchRecyclerViewAdapter.notifyDataSetChanged();

    }

    @Override
    public void setRecyclerViewAdapter() {
        recyclerView.setAdapter(searchRecyclerViewAdapter);

    }




}
