package main.soakim.no.birthdaymessenger;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * Created by Sondre on 20.10.2014.
 */
public class EditPersonActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_person_activity);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransacton = fragmentManager.beginTransaction();
        EditPersonFragment editFragment = new EditPersonFragment();

        Bundle b = new Bundle();
        b.putInt("position", getIntent().getIntExtra("position", 0));

        editFragment.setArguments(b);
        fragmentTransacton.add(R.id.edit_person_fragment_container, editFragment);
        fragmentTransacton.commit();
    }
}