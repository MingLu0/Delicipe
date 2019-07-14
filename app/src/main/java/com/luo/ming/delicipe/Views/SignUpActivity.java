package com.luo.ming.delicipe.Views;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        email_layout = findViewById(R.id.email_sign_up_layout);
        email_text = findViewById(R.id.email_sign_up_text);
        password_layout = findViewById(R.id.password_sign_up_layout);
        password_text = findViewById(R.id.password_sign_up_edit_text);
        btnSignUp = findViewById(R.id.button_sign_up);



        presenter = new SignUpActivityPresenter(this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_text.getText().toString();
                String password = password_text.getText().toString();
                presenter.signUpWithEmailAndPassWord(SignUpActivity.this,email,password);
            }
        });

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

}
