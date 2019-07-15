package com.luo.ming.delicipe.Views;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.luo.ming.delicipe.Presenters.SignUpActivityPresenter;
import com.luo.ming.delicipe.R;

public class SignUpActivity extends AppCompatActivity implements SignUpActivityPresenter.View {

    private Toolbar toolbar;
    private TextInputLayout email_layout, password_layout;
    private TextInputEditText email_text, password_text;
    private SignUpActivityPresenter presenter;
    private MaterialButton btnSignUp;
    private ImageView background;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        email_layout = findViewById(R.id.email_sign_up_layout);
        email_text = findViewById(R.id.email_sign_up_text);
        password_layout = findViewById(R.id.password_sign_up_layout);
        password_text = findViewById(R.id.password_sign_up_edit_text);
        btnSignUp = findViewById(R.id.button_sign_up);
        background = findViewById(R.id.image_background_sign_up);



        presenter = new SignUpActivityPresenter(this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_text.getText().toString();
                String password = password_text.getText().toString();
                presenter.signUpWithEmailAndPassWord(SignUpActivity.this,email,password);
            }
        });

        email_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                email_layout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                password_layout.setError(null);
                password_layout.setHelperText("Please enter a password with at least 6 characters");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Glide.with(this)
                .load(R.raw.background2_70)
                .centerCrop()
                .into(background);

    }

    @Override
    public void displayLogInSuccessMessage() {

        Toast.makeText(this, "Account has been created, please log in",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goBackToMainActivity() {

         Intent intent = new Intent(this,MainActivity.class);
         startActivity(intent);
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
