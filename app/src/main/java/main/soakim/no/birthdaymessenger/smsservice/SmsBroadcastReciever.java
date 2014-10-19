package main.soakim.no.birthdaymessenger.smsservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by NegatioN on 19.10.2014.
 */
public class SmsBroadcastReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, SmsService.class);
        // real Intent i = new Intent(context, PeriodicService.class);
        context.startService(i);
    }
}
