package main.soakim.no.birthdaymessenger.smsservice;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import main.soakim.no.birthdaymessenger.Information.Person;
import main.soakim.no.birthdaymessenger.MySQLHelper;
import main.soakim.no.birthdaymessenger.R;

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
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(!prefs.getBoolean("checkbox_preference", true)) return -1;
        Calendar calendar = Calendar.getInstance();

        MySQLHelper db = new MySQLHelper(getApplicationContext());
        List<Person> persons = db.getPersonWithBirthday(convertDateToString(calendar));

        //test
       // List<Person> persons = new ArrayList<Person>();
       // persons.add(new Person("Joakim Rishaug", 95153437, Calendar.getInstance().getTime()));

        if(persons.isEmpty())
            //do nothing?
            Log.d("SmsService.birthday", "no birthdays on this day");
        else {
            //sends an sms to each person with a birthday today
            for(Person person : persons){
                try {
                    SmsManager smsManager = SmsManager.getDefault();

                    String birthdayMessage = null;

                    if(person.getCustomMessage() == null) {
                        //get userdefined message.
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                        birthdayMessage = preferences.getString("message_preference", getApplicationContext().getString(R.string.default_message));
                        Log.d("HEYO3!!", birthdayMessage);
                        birthdayMessage = addPersonNameToMessage(birthdayMessage, person);
                    }else
                        birthdayMessage = person.getCustomMessage();

                    smsManager.sendTextMessage(String.valueOf(person.getPhoneNumber()), null, birthdayMessage, null, null);
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

    //exchanges the (Name) part of a message with the name of the person
    private String addPersonNameToMessage(String message, Person person){
        String regex = "(Name)";

        return message.replace(regex, person.getName());
    }

}
