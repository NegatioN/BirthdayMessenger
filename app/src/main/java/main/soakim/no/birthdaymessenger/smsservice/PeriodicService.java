package main.soakim.no.birthdaymessenger.smsservice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.text.format.Time;

/**
 * Periodically calls the SMS-service
 * Created by NegatioN on 19.10.2014.
 */
public class PeriodicService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Time time = new Time();
        time.setToNow();

        Intent i = new Intent(this, SmsService.class);
        PendingIntent pi = PendingIntent.getService(this,0,i,0);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String whenToSend = preferences.getString("time_preference", "00-01");

        int hour = Integer.parseInt(whenToSend.substring(0,2));
        int minute = Integer.parseInt(whenToSend.substring(3,5));
        time.set(0,minute,hour,time.monthDay,time.month,time.year);

        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //alarm runs every day
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, time.toMillis(false), 24*60*60*1000, pi);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
