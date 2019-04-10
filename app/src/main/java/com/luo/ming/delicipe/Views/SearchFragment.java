package com.luo.ming.delicipe.Views;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        presenter = new SearchActivityPresenter(this,getContext());

//        searchView = (SearchView)view.findViewById(R.id.searchView);
//        CharSequence query = searchView.getQuery();// get the query string currently in the text field
//
//        searchView.setIconifiedByDefault(false);
//        searchView.setQueryHint("search recipe");
//
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//
//                mQuery = query;
//                Log.d("query",mQuery);
//
//                presenter.setUrl(mQuery);
//
//                searchRecyclerViewAdapter = new SearchRecyclerViewAdapter(presenter,getContext());
//
//                presenter.getRecipesList();
//
//                searchRecyclerViewAdapter.notifyDataSetChanged();
//
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
       // mListener = null;
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
