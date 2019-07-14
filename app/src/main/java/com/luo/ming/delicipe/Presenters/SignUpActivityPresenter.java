package com.luo.ming.delicipe.Presenters;

import android.app.Activity;

import com.luo.ming.delicipe.Helpers.SignUpCallBack;
import com.luo.ming.delicipe.Models.User;

public class SignUpActivityPresenter implements SignUpCallBack {

    private View view;

    public SignUpActivityPresenter(View view) {
        this.view = view;
    }

    public void signUpWithEmailAndPassWord(Activity activity, String email, String password) {

        User user = new User();
        user.signUpWithEmailAndPwd(email,password,activity,this);
    }

    @Override
    public void onSuccess() {

        view.goBackToMainActivity();
        view.displayLogInSuccessMessage();
    }

    @Override
    public void onFailure() {

    }

    public interface View{

        void displayLogInSuccessMessage();
        void goBackToMainActivity();

    }
}
