package com.luo.ming.delicipe.Presenters;

import android.content.Context;
import com.luo.ming.delicipe.Models.UserRecipeCover;


public class AddCoverFragmentPresenter {

    private Context context;
    private FragmentView view;


    public AddCoverFragmentPresenter(Context context, FragmentView view) {
        this.context = context;
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
                UserRecipeCover userRecipeCover = new UserRecipeCover(imageBytes,coverName,cookingTime,servingSize,comment);
                return userRecipeCover;
            }
            view.showNameExistsError();
        }

       return null;
    }

    private boolean checkIfNameExisits(String coverName) {

        UserRecipeCover userRecipeCover = new UserRecipeCover();
        userRecipeCover.setCoverName(coverName);
        Boolean bool = userRecipeCover.checkIfNameAlreadyExists(context);

        return bool;
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
