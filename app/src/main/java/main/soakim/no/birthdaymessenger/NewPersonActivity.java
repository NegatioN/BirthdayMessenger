package main.soakim.no.birthdaymessenger;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Sondre on 23.10.2014.
 */
public class NewPersonActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.new_person_activity);

        if(savedInstanceState == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransacton = fragmentManager.beginTransaction();
            NewPersonFragment newFragment = new NewPersonFragment();

            fragmentTransacton.add(R.id.new_person_fragment_container, newFragment);
            fragmentTransacton.commit();
        }
        else {
            NewPersonFragment newFragment = new NewPersonFragment();
            newFragment = (NewPersonFragment) getFragmentManager().findFragmentById(R.id.new_person_fragment_container);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.exit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.exit: //settings();
                Intent intent = new Intent(getApplicationContext(), BirthdayMessenger.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}