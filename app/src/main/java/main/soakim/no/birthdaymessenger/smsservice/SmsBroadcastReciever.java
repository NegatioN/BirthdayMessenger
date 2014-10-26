package main.soakim.no.birthdaymessenger.smsservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by NegatioN on 19.10.2014.
 */
public class SmsBroadcastReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if(preferences.getBoolean("checkbox_preference", false)) {
            Intent i = new Intent(context, PeriodicService.class);
            context.startService(i);
        }
        else{
            Log.d("Broadcastrec", "Service not booting");
        }
    }
}
