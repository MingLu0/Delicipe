package com.luo.ming.delicipe.Presenters;

import android.content.Context;

import com.luo.ming.delicipe.Models.UserRecipeStep;

import java.util.ArrayList;

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

    public ArrayList<UserRecipeStep> getUserSteps() {

        ArrayList<UserRecipeStep>userRecipeSteps = view.getStepsFromTableLayout();

        return userRecipeSteps;
    }

    public interface View{
        void displayTableLayout();
        ArrayList<UserRecipeStep>getStepsFromTableLayout();
    }

}
