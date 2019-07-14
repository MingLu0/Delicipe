package com.luo.ming.delicipe.Presenters;

import android.app.Activity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
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

    public void signInWithGoogleAcct() {
        view.signInWithGoogleAcct();
    }

    public void firebaseAuthenWithGoogle(Activity activity, GoogleSignInAccount account) {

        User.firebaseAuthWithGoogle(activity,account,this);
    }

    public interface View{

        void displayToast(String text);

        void goToHotRecipePageWithUserInfo(User user);
        void signInWithGoogleAcct();
    }


}
