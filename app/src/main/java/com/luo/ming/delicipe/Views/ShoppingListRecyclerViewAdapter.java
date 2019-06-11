package com.luo.ming.delicipe.Views;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.luo.ming.delicipe.Models.Ingredient;
import com.luo.ming.delicipe.Presenters.ShoppingFragmentPresenter;
import com.luo.ming.delicipe.R;

public class ShoppingListRecyclerViewAdapter extends RecyclerView.Adapter<ShoppingListRecyclerViewAdapter.ViewHolder> {

    private  ShoppingFragmentPresenter presenter;
    private Context context;
    private ShoppingFragmentPresenter.ShoppingRowView view;

    public ShoppingListRecyclerViewAdapter(Context context, ShoppingFragmentPresenter presenter){
        this.context = context;
        this.presenter = presenter;


    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shopping_table_row,viewGroup,false);
        Log.d("ShoppingListAdapter","onCreateViewHolder has been called");
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        presenter.onBindItemRowViewAtPosition(position,viewHolder);
        Log.d("ShoppingListAdapter","onBindViewHolder has been called");

    }

    @Override
    public int getItemCount() {
        return presenter.getItemRowsCount();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements ShoppingFragmentPresenter.ShoppingRowView {

        private CheckBox btnChk;
        private TextView txtItem;
        private ImageButton btnEdit;
        private TextView txtCount;
        private TextView txtUnit;

        public ViewHolder(View itemView, final Context cxt){
            super(itemView);

            btnChk = (CheckBox)itemView.findViewById(R.id.chk_selected);
            txtItem = (TextView)itemView.findViewById(R.id.txt_item);

            //btnEdit = (ImageButton)itemView.findViewById(R.id.btn_edit);
            txtCount = (TextView)itemView.findViewById(R.id.txt_count);
            txtUnit = (TextView)itemView.findViewById(R.id.txt_unit);

            btnChk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(btnChk.isChecked()){

                        presenter.deleteShoppingItem(getAdapterPosition());


                    }

                }
            });

//            btnEdit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    presenter.editShoppingItem(getAdapterPosition());
//                }
//            });


        }

        @Override
        public void setRowContent(Ingredient ingredient) {
            txtCount.setText(String.valueOf(ingredient.getCount()));
            txtUnit.setText(ingredient.getUnit());
            txtItem.setText(ingredient.getIngredient());

        }

        @Override
        public void notifyItemRemoved(int position) {
            this.notifyItemRemoved(position);
        }
    }

}
