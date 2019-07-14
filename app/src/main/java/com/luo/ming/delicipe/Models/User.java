package com.luo.ming.delicipe.Models;

import android.app.Activity;
import android.net.Uri;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.luo.ming.delicipe.Helpers.DelicipeApplication;
import com.luo.ming.delicipe.Helpers.SignInCallBack;
import com.luo.ming.delicipe.Helpers.SignUpCallBack;
import com.luo.ming.delicipe.R;

import java.io.Serializable;

public class User implements Serializable {
    private String email;
    private String password;
    private String first_name;
    private String last_name;
    private String displayName;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    private String photoUrl;

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

    public  void signUpWithEmailAndPwd(String email, String password, Activity activity, final SignUpCallBack callBack){

        if(!email.isEmpty()&&!password.isEmpty()){

            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(activity,
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                callBack.onSuccess();
                                Log.d(TAG,"sign up success");
                            } else {

                                callBack.onFailure();
                            }
                        }
                    });

        }

    }

    public static void signInWithEmailAndPassword(String email, String password, final SignInCallBack callBack) {

        if(!email.isEmpty()&&!password.isEmpty()){

            final FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                 FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                 User user = new User();
                                 user.setDisplayName(firebaseUser.getDisplayName());
                                 user.setEmail(firebaseUser.getEmail());
                                 if(firebaseUser.getPhotoUrl()!=null){
                                     user.setPhotoUrl(firebaseUser.getPhotoUrl().toString());
                                 }
                                callBack.onSuccess(user);
                                Log.d(TAG,"user name "+user.getDisplayName());
                                Log.d(TAG,"user image " + user.getPhotoUrl());
                                Log.d(TAG,"user email " + user.getEmail());
                            } else {
                                callBack.onFailure(task.getException().toString());
                            }
                        }
                    });

        }
    }


    public static void firebaseAuthWithGoogle(Activity activity, GoogleSignInAccount account, final SignInCallBack callBack) {

        Log.d(TAG,"firebaseAuthWithGoogle:" +account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            User user = new User();
                            user.setDisplayName(firebaseUser.getDisplayName());
                            user.setEmail(firebaseUser.getEmail());
                            user.setPhotoUrl(firebaseUser.getPhotoUrl().toString());
                            callBack.onSuccess(user);
                            //update ui
                        } else {
                            Log.w(TAG,"signInWithCredential:failure",task.getException());
                            callBack.onFailure(task.getException().toString());
                            //update UI
                        }

                    }
                });

    }






}
