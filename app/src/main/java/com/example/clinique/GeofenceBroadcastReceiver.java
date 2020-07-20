package com.example.clinique;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.util.List;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "GeofenceBroadcastReceiv";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //   Toast.makeText(context, "Geofence Triggered...", Toast.LENGTH_SHORT).show();

        NotificationHelper notificationHelper = new NotificationHelper(context);

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        if (geofencingEvent.hasError()){

            Log.d(TAG, "onReceive: geofencingEvent has error");
            return;
        }

        List<com.google.android.gms.location.Geofence> geofences = geofencingEvent.getTriggeringGeofences();

        for (com.google.android.gms.location.Geofence geofence:geofences){
            Log.d(TAG, "onReceive: " + geofence.getRequestId());
        }
        //       Location location = geofencingEvent.getTriggeringLocation();

        int trasitiontype = geofencingEvent.getGeofenceTransition();

        switch (trasitiontype){

            case com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_ENTER:
                Toast.makeText(context, "You Entered in Geofence", Toast.LENGTH_SHORT).show();
                notificationHelper.sendHighPriorityNotification("GEOFENCE TRANSITION ENTER","You Entered in Geofence", com.example.clinique.Geofence.class);
                break;
            case com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_DWELL:
                Toast.makeText(context, "You are in the Geofence", Toast.LENGTH_SHORT).show();
                notificationHelper.sendHighPriorityNotification("GEOFENCE TRANSITION DWELL","You are in the Geofence", com.example.clinique.Geofence.class);
                break;
            case com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_EXIT:
                Toast.makeText(context, "You Exit from Geofence", Toast.LENGTH_SHORT).show();
                notificationHelper.sendHighPriorityNotification("GEOFENCE_TRANSITION_EXIT","You Exit from Geofence", com.example.clinique.Geofence.class);
                break;
        }
/*
        if (trasitiontype == Geofence.GEOFENCE_TRANSITION_ENTER){
            ParseObject object = new ParseObject("Notification");
            object.put("Notify",trasitiontype);
            object.put("patient","Ashokbhai");
            object.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null){

                        Log.d(TAG, "notifiction send to server");

                    }else{
                        Log.d(TAG, e.getMessage());
                    }
                }
            });
        }

 */
    }
}
