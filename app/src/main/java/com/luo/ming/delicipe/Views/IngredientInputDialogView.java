package com.luo.ming.delicipe.Views;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.luo.ming.delicipe.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IngredientInputDialogView extends LinearLayout {

    private AlertDialog.Builder inputDialogBuilder;
    private AlertDialog inputDialog;
    private OnButtonStateClickedListener listener;

    public String getInputText() {
        return inputText;
    }
    private String inputText;
    private String item;

    public IngredientInputDialogView(Context context) {
        super(context);

        initView(context);
    }

    public IngredientInputDialogView(Context context, String item){
        super(context);
        this.item = item;
        initView(context);
    }

    public IngredientInputDialogView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public IngredientInputDialogView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @BindView(R.id.text_input_layout_edit_shopping_item_outlined) TextInputLayout editItemNameInputLayout;
    @BindView(R.id.text_input_edit_text_edit_shopping_outlined) TextInputEditText editItemNameInputText;
    @BindView(R.id.edit_saveButton)Button btnSave;
    @BindView(R.id.edit_cancelButton) Button btnCancel;

    public void setListener(OnButtonStateClickedListener listener){
        this.listener =  listener;
    }


    private void initView(Context context) {
        inputDialogBuilder = new AlertDialog.Builder(getContext());

       final View view = LayoutInflater.from(context).inflate(R.layout.shopping_edit_popup,this);
        ButterKnife.bind(this,view);
        editItemNameInputText.setText(item);
        inputDialogBuilder.setView(view);
        inputDialog = inputDialogBuilder.create();
        inputDialog.show();
        
    }

    @OnClick(R.id.edit_saveButton)
    public void saveIngredientDetails(){

        if(! TextUtils.isEmpty(editItemNameInputText.getText())){

            inputText = editItemNameInputText.getText().toString();

            listener.onSaveClicked(editItemNameInputText.getText().toString());
            inputDialog.dismiss();

        } else{
            editItemNameInputLayout.setError(getResources().getString(R.string.INGREDIENT_INPUT_DIALOG_VIEW_ITEM_NAME_MISSING_ERROR));
        }
    }
    
    @OnClick(R.id.edit_cancelButton)
    public void cancelButtonPressed(){
        inputDialog.dismiss();

    }


    public interface OnButtonStateClickedListener{
        void onSaveClicked(String item);
        void onCancelClicked();
    }


}
