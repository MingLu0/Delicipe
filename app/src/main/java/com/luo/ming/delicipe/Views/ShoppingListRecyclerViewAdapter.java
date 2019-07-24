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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShoppingListRecyclerViewAdapter extends RecyclerView.Adapter<ShoppingListRecyclerViewAdapter.ViewHolder> {

    private  ShoppingFragmentPresenter presenter;
    private Context context;

    public ShoppingListRecyclerViewAdapter(Context context, ShoppingFragmentPresenter presenter){
        this.context = context;
        this.presenter = presenter;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.table_row_shopping,viewGroup,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        presenter.onBindItemRowViewAtPosition(position,viewHolder);

    }

    @Override
    public int getItemCount() {
        return presenter.getItemRowsCount();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements ShoppingFragmentPresenter.ShoppingRowView {

        @BindView(R.id.chk_selected) CheckBox btnChk;
        @BindView(R.id.txt_item) TextView txtItem;
        @BindView(R.id.button_edit) ImageButton btnEdit;

        public ViewHolder(View itemView, final Context cxt){
            super(itemView);

            ButterKnife.bind(this,itemView);

        }

        @OnClick(R.id.button_edit)
        public void editShoppingItem(){
            presenter.editShoppingItem(getAdapterPosition());
        }

        @OnClick(R.id.chk_selected)
        public void deleteShoppingItem(){

            if(btnChk.isChecked()){
                presenter.deleteShoppingItem(getAdapterPosition());
                btnChk.setChecked(false);
            }
        }

        @Override
        public void setRowContent(Ingredient ingredient) {
            txtItem.setText(ingredient.getIngredientItem());

        }

        @Override
        public void notifyItemRemoved(int position) {
            this.notifyItemRemoved(position);
        }
    }

}
