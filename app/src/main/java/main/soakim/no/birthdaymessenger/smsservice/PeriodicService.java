package main.soakim.no.birthdaymessenger.smsservice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import java.util.Calendar;

/**
 * Periodically calls the SMS-service
 * Created by NegatioN on 19.10.2014.
 */
public class PeriodicService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Calendar cal = Calendar.getInstance();
        Intent i = new Intent(this, SmsService.class);
        PendingIntent pi = PendingIntent.getService(this,0,i,0);

        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 60*1000, pi);



        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
