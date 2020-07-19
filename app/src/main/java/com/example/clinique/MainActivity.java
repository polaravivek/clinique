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

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnKeyListener {

    TextView changeLogInMode;
    Button LogInButton;
    EditText Userpassword;
    ImageView LogoimageView;
    ConstraintLayout backgrounRelativeLayout;
    TextView simpleText;


    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {

        if (i == keyEvent.KEYCODE_ENTER && keyEvent.getAction() == keyEvent.ACTION_DOWN) {
            Login(view);
        }
        return false;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.changeLogInMode) {

            Intent intent = new Intent(this,SignUpActivity.class);
            startActivity(intent);

        } else if (view.getId() == R.id.ConstaintLayout || view.getId() == R.id.logoImageView) {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        }
    }

    public void Login(View view) {

        EditText mobilenumber = (EditText) findViewById(R.id.mobilenumber);
        EditText password = (EditText) findViewById(R.id.UserPassword);
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        mgr.hideSoftInputFromWindow(password.getWindowToken(), 0);
        mgr.hideSoftInputFromWindow(mobilenumber.getWindowToken(), 0);

        if (mobilenumber.getText().toString().matches("") || password.getText().toString().matches("")) {

            Toast.makeText(this, "A Username and Password are required", Toast.LENGTH_SHORT).show();

        } else {

            ParseUser.logInInBackground(mobilenumber.getText().toString(), password.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {

                    if (user != null) {

                        Toast.makeText(MainActivity.this, "Loged in Successful!", Toast.LENGTH_SHORT).show();
                        Log.i("Log in", "Loged in Successful!");

                    } else {

                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        if (ParseUser.getCurrentUser() != null) {

        }
    }
}