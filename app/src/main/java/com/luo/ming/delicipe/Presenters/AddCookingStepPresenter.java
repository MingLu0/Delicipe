package com.luo.ming.delicipe.Presenters;

import com.luo.ming.delicipe.Models.UserRecipeIngredient;
import com.luo.ming.delicipe.Models.UserRecipeStep;

import java.util.ArrayList;

public class AddCookingStepPresenter {

    private View view;


    public AddCookingStepPresenter(View view) {
        this.view = view;
    }

    public void displayTableLayout(){
        view.addNewCookingStep(null);
    }

    public ArrayList<UserRecipeStep> getUserSteps() {

        return view.getStepsFromTableLayout();

    }

    public void displayTableRows(ArrayList<UserRecipeStep> userRecipeSteps) {

        for(UserRecipeStep i : userRecipeSteps){
            view.addNewCookingStep(i);
        }
    }

    public interface View{
        void addNewCookingStep(UserRecipeStep userRecipeStep);
        ArrayList<UserRecipeStep>getStepsFromTableLayout();
    }

}
