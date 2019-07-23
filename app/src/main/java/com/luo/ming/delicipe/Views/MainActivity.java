package com.luo.ming.delicipe.Views;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.luo.ming.delicipe.Models.User;
import com.luo.ming.delicipe.Presenters.MainActivityPresenter;
import com.luo.ming.delicipe.R;
import com.luo.ming.delicipe.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity implements MainActivityPresenter.View {


    @BindView(R2.id.browseButton) Button browseButton;
    @BindView(R2.id.email_input_layout) TextInputLayout email_layout;
    @BindView(R2.id.password_input_layout) TextInputLayout password_layout;
    @BindView(R2.id.email_edit_text) TextInputEditText email_edit_text;
    @BindView(R2.id.password_edit_text) TextInputEditText password_edit_Text;
    @BindView(R2.id.btnSignIn) MaterialButton btn_sign_in;
    @BindView(R2.id.btnSignUp) TextView btn_sign_up;
    @BindView(R2.id.image_background) ImageView backgroundImage;
    @BindView(R2.id.google_sign_in_button) SignInButton googleSignInBtn;

    private GoogleSignInClient mGoogleApiClient;
    private MainActivityPresenter presenter;

    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "MainActivity";
    public static final String MAIN_ACTIVITY_MESSAGE = "package com.luo.ming.delicipe.Views.MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        googleSignInBtn.setSize(SignInButton.SIZE_WIDE);
        presenter = new MainActivityPresenter(this);

    }

    @OnClick(R2.id.google_sign_in_button)
    public void googleSignIn(){
        presenter.signInWithGoogleAcct();
    }

    @OnClick(R2.id.browseButton)
    public void browseWithoutSignIn(){
        Intent intent = new Intent(getApplicationContext(), TabbedActivity.class);
        startActivity(intent);
    }

    @OnClick(R2.id.btnSignIn)
    public void signInWithEmailAndPassword(){
        String email = email_edit_text.getText().toString();
        String password = password_edit_Text.getText().toString();
        presenter.signInWithEmailAndPassword(email,password);
    }

    @OnClick(R2.id.btnSignUp)
    public void goToSignUpPage(){
        Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
        startActivity(intent);
    }


    @Override
    public void signInWithGoogleAcct() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = GoogleSignIn.getClient(this,gso);
        Intent signInIntent = mGoogleApiClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);

    }

    @Override
    public void displayPasswordInputError(String message) {
        password_layout.setError(message);
    }

    @Override
    public void displayEmailInputError(String message) {
        email_layout.setError(message);
    }

    @Override
    public void displayBackgroundImage() {

        Glide.with(this)
                .load(R.raw.background40)
                .centerCrop()
                .into(backgroundImage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                presenter.firebaseAuthenWithGoogle(this,account);
            } catch (ApiException e){
                Log.w(TAG,"Google sign in failed");
            }

        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        presenter.checkIfUserHasLoggedIn();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }


    @Override
    public void displayToast(String text) {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goToHotRecipePageWithUserInfo(User user) {
        Intent intent = new Intent(this,TabbedActivity.class);
        intent.putExtra(MAIN_ACTIVITY_MESSAGE, user);
        startActivity(intent);
    }


}
