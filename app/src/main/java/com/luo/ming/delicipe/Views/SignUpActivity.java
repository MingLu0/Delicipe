package com.luo.ming.delicipe.Views;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.luo.ming.delicipe.Presenters.SignUpActivityPresenter;
import com.luo.ming.delicipe.R;
import com.luo.ming.delicipe.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class SignUpActivity extends AppCompatActivity implements SignUpActivityPresenter.View {

    @BindView(R2.id.email_sign_up_layout) TextInputLayout email_layout;
    @BindView(R2.id.password_sign_up_layout) TextInputLayout password_layout;
    @BindView(R2.id.email_sign_up_text) TextInputEditText email_text;
    @BindView(R2.id.password_sign_up_edit_text) TextInputEditText password_text;
    @BindView(R2.id.button_sign_up) MaterialButton btnSignUp;
    @BindView(R2.id.image_background_sign_up) ImageView background;

    SignUpActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        presenter = new SignUpActivityPresenter(this);

        ButterKnife.bind(this);

        //todo place this into the displaybackgroundimage method
        Glide.with(this)
                .load(R.raw.background2_70)
                .centerCrop()
                .into(background);

    }

    @OnTextChanged(value = R2.id.email_sign_up_text, callback=OnTextChanged.Callback.TEXT_CHANGED)
    public void emailInputChanged(){
        email_layout.setError(null);
    }

    @OnTextChanged(value=R2.id.password_sign_up_edit_text, callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void passwordInputChanged(){
        password_layout.setError(null);
        password_layout.setHelperText(getResources().getString(R.string.SIGN_UP_PASSWORD_HELPER_TEXT));
    }

    @OnClick(R2.id.button_sign_up)
    public void signUpWithEmailAndPassword(){
        String email = email_text.getText().toString();
        String password = password_text.getText().toString();
        presenter.signUpWithEmailAndPassWord(SignUpActivity.this,email,password);
    }

    @Override
    public void displayLogInSuccessMessage() {

        Toast.makeText(this, getResources().getString(R.string.SIGN_UP_SUCCESS_TOAST_MESSAGE),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goBackToMainActivity() {

         Intent intent = new Intent(this, SignInActivity.class);
         startActivity(intent);
    }

    @Override
    public void displayBackgroundImage() {

    }

    @Override
    public void displayPasswordErrorMessage(String message) {

        password_layout.setError(message);
    }

    @Override
    public void displayEmailErrorMessage(String message) {

        email_layout.setError(message);
    }

}
