package com.luo.ming.delicipe.Views;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luo.ming.delicipe.Presenters.FavouriteFragmentPresenter;
import com.luo.ming.delicipe.R;


public class FavouritesFragment extends Fragment implements FavouriteFragmentPresenter.View {

    private RecyclerView recyclerView;
    private FavouritesRecyclerViewAdapter adapter;
    private FavouriteFragmentPresenter presenter;

    public FavouritesFragment() {

        Log.d("FavouriteFragment","constructor called");
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("FavouriteFragment","oncreate called");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("FavouriteFragment","oncreateview called");

        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView_favourites);
        presenter = new FavouriteFragmentPresenter(getActivity(), this);
        adapter = new FavouritesRecyclerViewAdapter(getActivity(),presenter);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(adapter);

        recyclerView.getAdapter().notifyDataSetChanged();

        Log.d("FavouriteFragment","onviewcreated called");


    }

    @Override
    public void refreshRecipeList() {

        recyclerView.getAdapter().notifyDataSetChanged();

    }

    @Override
    public void setRecyclerViewAdapter() {

        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();

        presenter = new FavouriteFragmentPresenter(getActivity(), this);
        adapter = new FavouritesRecyclerViewAdapter(getActivity(),presenter);

        Log.d("FavouriteFragment","onresume called");
    }
/**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnAddCoverFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onAddCoverFragmentInteraction(Uri uri);
//    }
}
