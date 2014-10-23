package main.soakim.no.birthdaymessenger;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.soakim.no.birthdaymessenger.Information.Person;
import main.soakim.no.birthdaymessenger.smsservice.PeriodicService;

public class BirthdayMessenger extends Activity implements BirthdayListFragment.ListFragmentItemClickListener {
    public static ArrayList<String> personsName = new ArrayList<String>();
    public static List<Person> persons = new ArrayList<Person>();
    private static Context context;

    public Date setFormattedDate(String dateFromDB){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = df.parse(dateFromDB);
        }catch(ParseException e){
            e.printStackTrace();
            d = null;
        }
        return d;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySQLHelper db = new MySQLHelper(this);

       // db.addPerson(new Person( "Joakim Rishaug", 95153437, setFormattedDate("1990-10-21")));
       // db.addPerson(new Person( "Martin", 22222222, setFormattedDate("1993-05-13")));

        persons = db.getAllPersons();
        context = this;

        //SharedPreferences.Editor.clear().commit(); // clears sharedpreferences from device?

        // sets default message if sharedpreferances doesn't yet exist
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("message_preference", getString(R.string.default_message));
        editor.commit();

        Log.d("People in db", persons.toString());

        updateList();
        Log.d("People in arraylist", personsName.size()+"");

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        if(preferences.getBoolean("checkbox_preference", false)){
            //starts the periodic-service for sending messages to everyone that has a birthday today.
             Intent i = new Intent();
             i.setAction("main.soakim.no.birthdaymessenger.SmsBroadcastReciever");
             sendBroadcast(i);
            Log.d("Alarm", "Periodic alarm started");
        }else{
            //cancels the periodic alarm so it no longer runs.
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            PendingIntent pSmsIntent = PendingIntent.getService(this, 0, new Intent(this, PeriodicService.class), 0);
            alarmManager.cancel(pSmsIntent);
            Log.d("Alarm", "Periodic alarm Cancelled");
        }

        setContentView(R.layout.activity_birthday_messenger);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.birthday_messenger, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_settings: //settings();
                Intent in = new Intent(this, SetPreferencesActivity.class);
                startActivity(in);
                return true;
            case R.id.new_person:
                Intent intent = new Intent("main.soakim.no.birthdaymessenger.NewPersonActivity");
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentItemClick(int position) {
        int orientation = getResources().getConfiguration().orientation;

        if(orientation == Configuration.ORIENTATION_LANDSCAPE ){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment prevFrag = fragmentManager.findFragmentByTag("main.soakim.no.birthdaymessenger.person.edit");

            if(prevFrag!=null) fragmentTransaction.remove(prevFrag);

            EditPersonFragment fragment = new EditPersonFragment();

            Bundle b = new Bundle();
            b.putInt("position", position);
            fragment.setArguments(b);
            fragmentTransaction.add(R.id.edit_person_fragment_container, fragment,"main.soakim.no.birthdaymessenger.person.edit");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else{
            Intent intent = new Intent("main.soakim.no.birthdaymessenger.EditPersonActivity");
            intent.putExtra("position", position);
            startActivity(intent);
        }
    }

    public static void updateList() {
        //personsName = new String[persons.size()];
        MySQLHelper db = new MySQLHelper(context);
        persons = db.getAllPersons();

        personsName.clear();
        for(int i = 0; i < persons.size(); i++)
            personsName.add(persons.get(i).getName());

        BirthdayListFragment.notifyListChange();
    }
}