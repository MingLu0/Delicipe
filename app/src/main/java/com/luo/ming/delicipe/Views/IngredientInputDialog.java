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

public class IngredientInputDialog extends LinearLayout {

    private AlertDialog.Builder inputDialogBuilder;
    private AlertDialog inputDialog;
    private OnButtonStateClickedListener listener;

    public String getInputText() {
        return inputText;
    }

    private String inputText;



    public IngredientInputDialog(Context context) {
        super(context);

        initView(context);
    }

    public IngredientInputDialog(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public IngredientInputDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private TextInputLayout editItemNameInputLayout;
    private TextInputEditText editItemNameInputText;
    Button btnSave;
    Button btnCancel;

    public void setListener(OnButtonStateClickedListener listener){
        this.listener =  listener;
    }


    private void initView(Context context) {



        inputDialogBuilder = new AlertDialog.Builder(getContext());

       final View view = LayoutInflater.from(context).inflate(R.layout.shopping_edit_popup,this);


        editItemNameInputLayout = findViewById(R.id.text_input_layout_edit_shopping_item_outlined);
        editItemNameInputText = findViewById(R.id.text_input_edit_text_edit_shopping_outlined);
        btnSave =  findViewById(R.id.edit_saveButton);
        btnCancel = findViewById(R.id.edit_cancelButton);

        inputDialogBuilder.setView(view);
        inputDialog = inputDialogBuilder.create();
        inputDialog.show();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(! TextUtils.isEmpty(editItemNameInputText.getText())){

                    inputText = editItemNameInputText.getText().toString();

                    listener.onSaveClicked(editItemNameInputText.getText().toString());
                    inputDialog.dismiss();

                } else{
                    editItemNameInputLayout.setError("Item name required");
                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputDialog.dismiss();

            }
        });
    }


    public interface OnButtonStateClickedListener{
        void onSaveClicked(String item);
        void onCancelClicked();
    }


}
