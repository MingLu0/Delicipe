package com.luo.ming.delicipe.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.luo.ming.delicipe.R;

public class MainActivity extends AppCompatActivity {

    private Button browseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        browseButton = findViewById(R.id.browseButton);

        browseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //presenter.login();  ONLY LINE BEFORE EDITING
                // Go to Home Page activity
                Intent intent = new Intent(getApplicationContext(), TabbedActivity.class);
                startActivity(intent);

            }
        });
    }
}
