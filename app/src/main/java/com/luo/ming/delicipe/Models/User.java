package com.luo.ming.delicipe.Models;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.luo.ming.delicipe.Helpers.DelicipeApplication;

public class User {
    private String email;
    private String password;
    private String first_name;
    private String last_name;

    private static final String TAG = "User";

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public static void signUpWithEmailAndPwd(String email, String password,Activity activity){

        if(!email.isEmpty()&&!password.isEmpty()){

            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(activity,
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Log.d(TAG,"sign up success");
                            }
                        }
                    });

        }

    }

    public Activity getActivity() {
        Context context = DelicipeApplication.getAppContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }


}
