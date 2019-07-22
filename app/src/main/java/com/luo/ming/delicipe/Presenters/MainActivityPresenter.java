package com.luo.ming.delicipe.Presenters;

import android.app.Activity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.luo.ming.delicipe.Helpers.SignInCallBack;
import com.luo.ming.delicipe.Models.User;

public class MainActivityPresenter implements SignInCallBack {

    private View view;

    public MainActivityPresenter(View view) {
        this.view = view;
        view.displayBackgroundImage();
    }

    public void signInWithEmailAndPassword(String email, String password) {

        boolean validEmail = false;
        boolean validPassword = false;

        try{
            validEmail = User.checkEmailFormat(email);
        } catch (IllegalArgumentException e){
            view.displayEmailInputError(e.getMessage());
        }

        try{
            validPassword = User.checkPasswordLength(password);
        } catch (IllegalArgumentException e){
            view.displayPasswordInputError(e.getMessage());
        }

        if(validEmail&&validPassword){
            User.signInWithEmailAndPassword(email,password,this);
        }
    }

    @Override
    public void onSuccess(User user) {
        
        view.displayToast("Logged in Successfully");
        view.goToHotRecipePageWithUserInfo(user);

    }

    @Override
    public void onFailure(String exception) {

        view.displayEmailInputError(exception);
        view.displayPasswordInputError(exception);
    }

    public void signInWithGoogleAcct() {
        view.signInWithGoogleAcct();
    }

    public void firebaseAuthenWithGoogle(Activity activity, GoogleSignInAccount account) {

        User.firebaseAuthWithGoogle(activity,account,this);
    }

    public void checkIfUserHasLoggedIn() {

        if(User.checkIfCurrentUserExists()){
            User user = User.getCurrentUser();
            view.goToHotRecipePageWithUserInfo(user);
        }
    }

    public interface View{

        void displayToast(String text);

        void goToHotRecipePageWithUserInfo(User user);
        void signInWithGoogleAcct();
        void displayPasswordInputError(String message);
        void displayEmailInputError(String message);
        void displayBackgroundImage();
    }


}
