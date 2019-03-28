package com.luo.ming.delicipe;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luo.ming.delicipe.Data.DatabaseHandler;
import com.luo.ming.delicipe.Presenters.SearchActivityPresenter;
import com.luo.ming.delicipe.Presenters.ShoppingFragmentPresenter;
import com.luo.ming.delicipe.Views.ShoppingListRecyclerViewAdapter;


public class ShoppingFragment extends Fragment implements ShoppingFragmentPresenter.View {

    private RecyclerView recyclerView;
    private ShoppingListRecyclerViewAdapter recyclerViewAdapter;
    private ShoppingFragmentPresenter presenter;




    //private OnFragmentInteractionListener mListener;

    public ShoppingFragment() {
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
        return inflater.inflate(R.layout.fragment_shopping, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        recyclerView = (RecyclerView) view.findViewById(R.id.shoppingRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); //?

        presenter = new ShoppingFragmentPresenter(this,getContext());//?

        recyclerViewAdapter = new ShoppingListRecyclerViewAdapter(presenter,getContext());

        recyclerView.setAdapter(recyclerViewAdapter);









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


}
