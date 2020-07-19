package com.example.clinique;

import android.app.Application;
import com.example.clinique.R;
import com.parse.Parse;
import com.parse.ParseInstallation;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.app_id))
                .clientKey(getString(R.string.client_key))
                .server(getString(R.string.server_url))
                .build()
        );

        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
