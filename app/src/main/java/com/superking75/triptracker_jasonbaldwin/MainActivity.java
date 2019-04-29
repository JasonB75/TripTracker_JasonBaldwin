package com.superking75.triptracker_jasonbaldwin;

import android.app.ProgressDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getName() + "12121";
    EditText mEmailEditText, mPasswordEditText, mNameEditText;
    Button mLoginButton, mSignUpButton;
    TextView mSignUpTextView;
    String APP_ID;
    String API_KEY;
    BackendlessUser mBackendlessUser;

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
        mSignUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSignUpTextView.getText().equals(getString(R.string.signup_button_label))) {
                    mLoginButton.setVisibility(View.GONE);
                    mNameEditText.setVisibility(View.VISIBLE);
                    mSignUpButton.setVisibility(View.VISIBLE);
                    mSignUpTextView.setText("Cancel");
                } else {
                    mLoginButton.setVisibility(View.VISIBLE);
                    mNameEditText.setVisibility(View.GONE);
                    mSignUpButton.setVisibility(View.GONE);
                    mSignUpTextView.setText(getString(R.string.signup_button_label));

                }
            }
        });
        // MySignUpOnClickListener signMeUpListener = new MySignUpOnClickListener();
        //mSignUpButton.setOnClickListener(signMeUpListener);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = mEmailEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                String name = mNameEditText.getText().toString();
                Log.i(TAG, password);
                userEmail = userEmail.trim();
                password = password.trim();
                name = name.trim();


                if (!userEmail.isEmpty() && !name.isEmpty() && !password.isEmpty())
                {
                    if (checkForCase(password))
                    {
                        if (userEmail.contains("@") && userEmail.contains("."))
                        {
                            mBackendlessUser = new BackendlessUser();
                            mBackendlessUser.setEmail(userEmail);
                            mBackendlessUser.setPassword(password);
                            mBackendlessUser.setProperty("mName", name);
                            Backendless.UserService.register(mBackendlessUser, new AsyncCallback<BackendlessUser>() {
                                @Override
                                public void handleResponse(BackendlessUser response) {
                                    Log.i(TAG, "Registration successful for " +
                                            mBackendlessUser.getEmail());

                                }

                                final ProgressDialog pDialog = ProgressDialog.show(MainActivity.this,
                                        getString(R.string.prosses_load_title),
                                        getString(R.string.register_wait_dialog),
                                        true);
                                @Override
                                public void handleFault(BackendlessFault fault) {

                                    warnUser(fault.getMessage());
                                    pDialog.dismiss();
                                }
                            });

                        }

                        else
                        {
                            warnUser(getString(R.string.invalide_email_signup));
                        }

                        }

                    else
                    {
                        warnUser(getString(R.string.weak_password_signup));
                    }


                }

                else
                    {
                        warnUser(getString(R.string.empty_field_signup_error));
                }

            }
        });
//On click listener for the login button. Attempts to login a pre-existing user.
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = mEmailEditText.getText().toString().trim();
                String password = mPasswordEditText.getText().toString().trim();
                if (!userEmail.isEmpty() && !password.isEmpty()) {
                    Backendless.UserService.login(userEmail, password, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            mBackendlessUser = response;
                            Log.i(TAG, "Sucsess");
                        }
                        final ProgressDialog pDialog = ProgressDialog.show(MainActivity.this,
                                getString(R.string.prosses_load_title),
                                getString(R.string.login_wait_dialog),
                                true);
                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Log.i(TAG, "Fail" + fault.getMessage());
                            warnUser(fault.getMessage());
                            pDialog.dismiss();
                        }
                    });

                } else {
                    warnUser(getString(R.string.empty_field_login_error));
                }


            }
        });
    }
    /*private class MySignUpOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String userEmail = mEmailEditText.getText().toString();
            String password = mPasswordEditText.getText().toString();
            String mName = mNameEditText.getText().toString();

            userEmail = userEmail.trim();
            password = password.trim();
            mName = mName.trim();

            if (!userEmail.isEmpty() &&!password.isEmpty() && !mName.isEmpty()) {

                /* register the user in Backendless *//*

            }
            else {
                /* warn the user of the problem *//*

            }
        }

    }*/

    //Used to check if the provided string contains at least one letter of the prefered case.
    public static boolean checkForCase(String password)
    {
        boolean upper = false;
        boolean lower = false;
        boolean special = false;
        boolean number = false;
        String[] specialCharacters = {"!", "2","#","$","%","^","&","*","(",")","?","`","~","-","+","=", ",", ".", "/", "<", ">"};
        String[] numbersCharacter = {"1","2","3","4","5","6","7","8","9","0"};

        if (password.length()<6)
        {return false;}
        char[] splitPass = password.toCharArray();
        for(char n:splitPass )
        {
            if (Character.isUpperCase(n))
            {
                upper = true;
                break;
            }
        }
        if (!upper)
        {return false;}
        for(char n:splitPass )
        {
            if (Character.isLowerCase(n))
            {
                lower = true;
                 break;
            }
        }
        if (!lower)
        {return false;}
        for (String n: specialCharacters)
        {
            if (password.contains(n))
            {special = true;
             break;}
        }
        if (!special)
        {return false;}
        for (String n: numbersCharacter)
        {
            if (password.contains(n))
            {
                number = true;
                break;
            }
        }
        if (upper && lower && special && number)
        {
            return true;
        }
    return false;
    }

    public void warnUser(String alertDialog)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(alertDialog);
        builder.setTitle(R.string.authentication_error_title);
        builder.setPositiveButton(android.R.string.ok, null);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

}
