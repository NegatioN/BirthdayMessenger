package main.soakim.no.birthdaymessenger.smsservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import main.soakim.no.birthdaymessenger.Information.Person;
import main.soakim.no.birthdaymessenger.MySQLHelper;

/**
 * Created by NegatioN on 19.10.2014.
 */
public class SmsService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Calendar calendar = Calendar.getInstance();

        MySQLHelper db = new MySQLHelper(getApplicationContext());
       // List<Person> persons = db.getPersonWithBirthday(convertDateToString(calendar));

        //test
        List<Person> persons = new ArrayList<Person>();
        persons.add(new Person("Joakim Rishaug", 95153437, Calendar.getInstance().getTime()));

        if(persons.isEmpty())
            //do nothing?
            Log.d("SmsService.birthday", "no birthdays on this day");
        else {
            //sends an sms to each person with a birthday today
            for(Person person : persons){
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(String.valueOf(person.getPhoneNumber()), null, Person.CURRENTMESSAGE, null, null);
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
            Log.d("SmsService.birthday", "Birthdaymessage sent");
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private String convertDateToString(Calendar calendar){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(calendar.getTime());
    }

}
