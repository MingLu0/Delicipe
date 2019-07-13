package com.luo.ming.delicipe.Presenters;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.luo.ming.delicipe.Helpers.DelicipeApplication;
import com.luo.ming.delicipe.Models.User;

public class SignUpActivityPresenter {

    private FirebaseAuth mAuth;
    private View view;

    public SignUpActivityPresenter(View view) {
        this.view = view;
    }

    public void signUpWithEmailAndPassWord(Activity activity, String email, String password) {
        User.signUpWithEmailAndPwd(email,password,activity);
    }

    public interface View{

    }
}
