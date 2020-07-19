package com.example.clinique;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    private static final String TAG = "SignUpActivity";
    Boolean LoginActive = true;
    TextView changeLogInMode;
    Button LogInButton;
    EditText Userpassword;
    ImageView LogoimageView;
    ConstraintLayout backgrounRelativeLayout;
    TextView simpleText;

    public void signup(View view){

        EditText mobilenumber = (EditText) findViewById(R.id.mobilenumber);
        EditText firstname = (EditText)findViewById(R.id.firstname) ;
        EditText lastname = (EditText)findViewById(R.id.Lastname) ;
        EditText password = (EditText) findViewById(R.id.UserPassword);

        String name = lastname.getText().toString() + firstname.getText().toString();

        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        mgr.hideSoftInputFromWindow(password.getWindowToken(), 0);
        mgr.hideSoftInputFromWindow(mobilenumber.getWindowToken(), 0);

        ParseObject object = new ParseObject("Username");
        object.put("Username",name);
        object.put("mobilenumber",mobilenumber.getText().toString());

        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){

                    Log.i(TAG, "info saved successful");

                }else{
                    Log.i(TAG, e.getMessage());
                }
            }
        });

        ParseUser user = new ParseUser();

        user.setPassword(password.getText().toString());
        user.setUsername(mobilenumber.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(SignUpActivity.this, "Sign up Successful!", Toast.LENGTH_SHORT).show();
                    goToLoginPage();
                } else if ((e.getMessage().equals("Account already exists for this username."))){
                    Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    goToLoginPage();
                }
                else{
                    Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Intent intent = getIntent();

        changeLogInMode = (TextView) findViewById(R.id.changeLogInMode);
        LogInButton = (Button) findViewById(R.id.signupButton);
        backgrounRelativeLayout = (ConstraintLayout) findViewById(R.id.ConstaintLayout);
        Userpassword = (EditText) findViewById(R.id.UserPassword);
        simpleText = (TextView)findViewById(R.id.simpleTextview);


        changeLogInMode.setOnClickListener(this);
        Userpassword.setOnKeyListener(this);
        LogoimageView = (ImageView)findViewById(R.id.logoImageView);
        backgrounRelativeLayout.setOnClickListener(this);
        LogoimageView.setOnClickListener(this);
    }

    public void onClick(View view) {

        if (view.getId() == R.id.changeLogInMode) {

            Log.i(TAG, "onClick: change log in mode");
            goToLoginPage();

        } else if (view.getId() == R.id.ConstaintLayout || view.getId() == R.id.logoImageView) {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        }
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {

        if (i == keyEvent.KEYCODE_ENTER && keyEvent.getAction() == keyEvent.ACTION_DOWN) {
            signup(view);
        }
        return false;
    }

    public void goToLoginPage(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}