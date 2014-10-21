package main.soakim.no.birthdaymessenger;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.soakim.no.birthdaymessenger.Information.Person;

public class BirthdayMessenger extends Activity implements BirthdayListFragment.ListFragmentItemClickListener {
    public static ArrayList<String> personsName = new ArrayList<String>();
    public static List<Person> persons = new ArrayList<Person>();

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

     //   db.addPerson(new Person( "Joakim", 12345678, setFormattedDate("1990-02-13")));
     //   db.addPerson(new Person( "Martin", 22222222, setFormattedDate("1993-05-17")));

        persons = db.getAllPersons();

        Log.d("People in db", persons.toString());

        updateDb();
        Log.d("People in arraylist", personsName.size()+"");

        setContentView(R.layout.activity_birthday_messenger);
        //how to send an sms
       // Intent i = new Intent();
       // i.setAction("main.soakim.no.birthdaymessenger.SmsBroadcastReciever");
       // sendBroadcast(i);
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

    public static void updateDb() {
        //personsName = new String[persons.size()];
        personsName.clear();
        for(int i = 0; i < persons.size(); i++)
            personsName.add(persons.get(i).getName());

    }
}