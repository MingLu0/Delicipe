package com.luo.ming.delicipe.Views;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.luo.ming.delicipe.Models.User;
import com.luo.ming.delicipe.Presenters.MainActivityPresenter;
import com.luo.ming.delicipe.R;


public class MainActivity extends AppCompatActivity implements MainActivityPresenter.View {

    private Button browseButton;

    private FirebaseDatabase database;

    private FirebaseAuth mAuth;
    private TextInputLayout email_layout, password_layout;
    private TextInputEditText email_edit_text, password_edit_Text;
    private MaterialButton btn_sign_in;
    private TextView btn_sign_up;

    private ImageView backgroundImage;

    private SignInButton googleSignInBtn;
    private GoogleSignInClient mGoogleApiClient;
    private MainActivityPresenter presenter;

    private static final int RC_SIGN_IN = 9001;



    private static final String TAG = "MainActivity";
    public static final String MAIN_ACTIVITY_MESSAGE = "package com.luo.ming.delicipe.Views.MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        browseButton = findViewById(R.id.browseButton);
        email_layout = findViewById(R.id.email_input_layout);
        password_layout = findViewById(R.id.password_input_layout);
        email_edit_text = findViewById(R.id.email_edit_text);
        password_edit_Text = findViewById(R.id.password_edit_text);
        btn_sign_in = findViewById(R.id.btnSignIn);
        btn_sign_up = findViewById(R.id.btnSignUp);
        googleSignInBtn = findViewById(R.id.google_sign_in_button);
        googleSignInBtn.setSize(SignInButton.SIZE_WIDE);
        backgroundImage = findViewById(R.id.image_background);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        presenter = new MainActivityPresenter(this);


        googleSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.signInWithGoogleAcct();

            }
        });

        browseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TabbedActivity.class);
                startActivity(intent);

            }
        });

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = email_edit_text.getText().toString();
                String password = password_edit_Text.getText().toString();
                presenter.signInWithEmailAndPassword(email,password);

            }
        });

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });


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
