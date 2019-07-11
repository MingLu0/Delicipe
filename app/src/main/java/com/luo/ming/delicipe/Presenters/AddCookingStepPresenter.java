package com.luo.ming.delicipe.Presenters;

import com.luo.ming.delicipe.Models.UserRecipeStep;

import java.util.ArrayList;

public class AddCookingStepPresenter {

    private View view;


    public AddCookingStepPresenter(View view) {
        this.view = view;
    }

    public void displayTableLayout(){
        view.displayTableLayout();
    }

    public ArrayList<UserRecipeStep> getUserSteps() {

        return view.getStepsFromTableLayout();

    }

    public interface View{
        void displayTableLayout();
        ArrayList<UserRecipeStep>getStepsFromTableLayout();
    }

}
