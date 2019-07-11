package com.luo.ming.delicipe.Presenters;

import com.luo.ming.delicipe.Models.UserRecipeCover;


public class AddCoverFragmentPresenter {

    private FragmentView view;


    public AddCoverFragmentPresenter(FragmentView view) {
        this.view = view;
    }


    public UserRecipeCover getCoverInfoFromInput() {

        byte[]imageBytes = view.getUserImage();
        String coverName = view.getCoverName();
        int cookingTime = view.getCookingTime();
        int servingSize = view.getServingSize();
        String comment = view.getComment();

        if(coverName!=null){
            if(!checkIfNameExisits(coverName)){

                 return new UserRecipeCover(imageBytes,coverName,cookingTime,servingSize,comment);

            }
            view.showNameExistsError();
        }

       return null;
    }

    private boolean checkIfNameExisits(String coverName) {

        return UserRecipeCover.chkIfRecipeNameExists(coverName);

    }

    public interface FragmentView{

        byte[] getUserImage();
        String getCoverName();
        int getCookingTime();
        int getServingSize();
        String getComment();
        void showNameExistsError();
        void showNameEmptyError();

    }
}
