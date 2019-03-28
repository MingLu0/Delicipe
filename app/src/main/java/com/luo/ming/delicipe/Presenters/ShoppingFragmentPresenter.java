package com.luo.ming.delicipe.Presenters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.luo.ming.delicipe.Data.DatabaseHandler;
import com.luo.ming.delicipe.Views.ShoppingListRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShoppingFragmentPresenter {

    private RecyclerView recyclerView;
    private ShoppingListRecyclerViewAdapter recyclerViewAdapter;
    private DatabaseHandler db;
    private View view;
    private Context context;
    private ArrayList<String>items;



  //  String[]  = {"1.5 tsp baking soda","2.0 large eggs","0.5 cup heavy cream","1.0 cup water"};


    public ShoppingFragmentPresenter(View view,Context context){
        this.view = view;
        this.context = context;
        db = new DatabaseHandler(context);
        items = new ArrayList<>();
        items.add("1.5 tsp baking soda");
        items.add("2.0 large eggs");
        items.add("0.5 cup heavy cream");
        items.add("1.0 cup water");
    }

    public void onBindItemRowViewAtPosition(int position, ShoppingRowView view){

        String item = items.get(position);
        view.setRowContent(item);

    }

    public int getItemRowsCount(){
        return items.size();
    }


    public interface View{

    }

    public interface ShoppingRowView{

         void setRowContent(String itemContent);

    }

}
