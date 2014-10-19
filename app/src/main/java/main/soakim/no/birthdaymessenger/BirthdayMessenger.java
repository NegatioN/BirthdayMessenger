package main.soakim.no.birthdaymessenger;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import main.soakim.no.birthdaymessenger.Information.Person;


public class BirthdayMessenger extends Activity implements BirthdayListFragment.PersonChanged {

    private ArrayList<Person> persons = new ArrayList<Person>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday_messenger);


        //send an sms
    //    Intent i = new Intent();
    //    i.setAction("main.soakim.no.birthdaymessenger.SmsBroadcastReciever");
    //    sendBroadcast(i);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.birthday_messenger, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void nameChanged(String name) {
        //if(findViewById(R.id.editPage) != null) {

    }
}

