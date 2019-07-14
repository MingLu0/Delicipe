package com.luo.ming.delicipe.Presenters;

import com.luo.ming.delicipe.Helpers.SignInCallBack;
import com.luo.ming.delicipe.Models.User;

public class MainActivityPresenter implements SignInCallBack {

    private View view;

    public MainActivityPresenter(View view) {
        this.view = view;
    }

    public void signInWithEmailAndPassword(String email, String password) {

        User.signInWithEmailAndPassword(email,password,this);
    }

    @Override
    public void onSuccess(User user) {
        
        view.displayToast("Log in Successfully");
        view.goToHotRecipePageWithUserInfo(user);

    }

    @Override
    public void onFailure(String exception) {

        view.displayToast(exception);
    }

    public interface View{

        void displayToast(String text);

        void goToHotRecipePageWithUserInfo(User user);
    }


}
