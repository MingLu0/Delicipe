package com.luo.ming.delicipe.Presenters;

import android.content.Context;

public class AddCookingStepPresenter {

    private Context context;
    private View view;


    public AddCookingStepPresenter(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    public void displayTableLayout(){
        view.displayTableLayout();
    }

    public interface View{
        void displayTableLayout();
    }

}
