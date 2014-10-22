package main.soakim.no.birthdaymessenger;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * Created by Sondre on 23.10.2014.
 */
public class NewPersonActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.new_person_activity);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransacton = fragmentManager.beginTransaction();
        NewPersonFragment newFragment = new NewPersonFragment();

        fragmentTransacton.add(R.id.new_person_fragment_container, newFragment);
        fragmentTransacton.commit();
    }
}