package com.superking75.triptracker_jasonbaldwin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.backendless.Backendless;

public class MainActivity extends AppCompatActivity {
EditText mEmailEditText, mPasswordEditText, mNameEditText;
Button mLoginButton, mSignUpButton;
TextView mSignUpTextView;
String APP_ID;
String API_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEmailEditText = findViewById(R.id.enter_email);
        mPasswordEditText = findViewById(R.id.enter_password);
        mNameEditText = findViewById(R.id.enter_name);
        mLoginButton = findViewById(R.id.login_button);
        mSignUpButton = findViewById(R.id.signup_button);
        mSignUpTextView = findViewById(R.id.sign_up_text);
        mSignUpTextView.setText(getString(R.string.signup_button_label));
        API_KEY = getString(R.string.API_KEY);
        APP_ID = getString(R.string.APP_ID);

        Backendless.initApp(this, APP_ID, API_KEY);

        //Switches between the signup functionality and the login functionality
        mSignUpTextView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (mSignUpTextView.getText().equals(getString(R.string.signup_button_label)))
                {
                    mLoginButton.setVisibility(View.GONE);
                    mNameEditText.setVisibility(View.VISIBLE);
                    mSignUpButton.setVisibility(View.VISIBLE);
                    mSignUpTextView.setText("Cancel");
                }
                else
                {
                    mLoginButton.setVisibility(View.VISIBLE);
                    mNameEditText.setVisibility(View.GONE);
                    mSignUpButton.setVisibility(View.GONE);
                    mSignUpTextView.setText(getString(R.string.signup_button_label));

                }
            }
        });

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySignMeOnClickListener signMeUpListener = new MySignMeUpOnClickListener();
                mSignMeUpButton.setOnClickListener(signMeUpListener);
            }
        });


    }


}
