package com.luo.ming.delicipe.Views;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.luo.ming.delicipe.R;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private Button browseButton;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private TextInputLayout email_layout, password_layout;
    private TextInputEditText email_edit_text, password_edit_Text;
    private MaterialButton btn_sign_in;

    private static final String TAG = "MainActivity";





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

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("message");
        mAuth = FirebaseAuth.getInstance();

        databaseReference.setValue("Hello Again");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Toast.makeText(MainActivity.this,value,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user!=null){
                    Log.d(TAG,"user signed in");
                } else {
                    Log.d(TAG,"user signed out");
                }
            }
        };

        browseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //presenter.login();  ONLY LINE BEFORE EDITING
                // Go to Home Page activity
                //Intent intent = new Intent(getApplicationContext(), TabbedActivity.class);
                Intent intent = new Intent(getApplicationContext(), TabbedActivity.class);
                startActivity(intent);

            }
        });

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = email_edit_text.getText().toString();
                String password = password_edit_Text.getText().toString();
                if(!email.isEmpty()&&!password.isEmpty()){

                    mAuth.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(MainActivity.this,"Log in success",Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(MainActivity.this,"Log in failed",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                }




            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(mAuthListner != null){
            mAuth.removeAuthStateListener(mAuthListner);
        }
    }
}
