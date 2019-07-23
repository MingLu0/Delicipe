package com.luo.ming.delicipe.Presenters;

import android.app.Activity;

import com.luo.ming.delicipe.Helpers.SignUpCallBack;
import com.luo.ming.delicipe.Models.User;

public class SignUpActivityPresenter implements SignUpCallBack {

    private View view;

    public SignUpActivityPresenter(View view) {
        this.view = view;
        view.displayBackgroundImage();
    }

    public void signUpWithEmailAndPassWord(Activity activity, String email, String password) {

        boolean emailvalid = false;
        boolean passwordValid = false;
        try{
            emailvalid = User.checkEmailFormat(email);
        } catch (IllegalArgumentException e){
            view.displayEmailErrorMessage(e.getMessage());
        }

        try{
            passwordValid = User.checkPasswordLength(password);
        } catch (IllegalArgumentException e){
            view.displayPasswordErrorMessage(e.getMessage());
        }

        if(emailvalid&&passwordValid){
            User.signUpWithEmailAndPwd(email,password,activity,this);
        }
    }

    @Override
    public void onSuccess() {

        view.goBackToMainActivity();
        view.displayLogInSuccessMessage();
    }

    @Override
    public void onFailure(String message) {

        view.displayEmailErrorMessage(message);
    }

    public interface View{

        void displayLogInSuccessMessage();
        void goBackToMainActivity();
        void displayBackgroundImage();
        void displayPasswordErrorMessage(String message);
        void displayEmailErrorMessage(String message);
    }
}
