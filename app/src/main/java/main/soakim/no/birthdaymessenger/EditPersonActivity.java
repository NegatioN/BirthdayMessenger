package main.soakim.no.birthdaymessenger;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Sondre on 20.10.2014.
 */
public class EditPersonActivity extends Activity{
    private EditPersonFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_person_activity);

        if(savedInstanceState == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransacton = fragmentManager.beginTransaction();
            EditPersonFragment editFragment = new EditPersonFragment();

            Bundle b = new Bundle();
            b.putInt("position", getIntent().getIntExtra("position", 0));

            editFragment.setArguments(b);
            fragmentTransacton.add(R.id.edit_person_fragment_container, editFragment);
            fragmentTransacton.commit();
            fragment = editFragment;
        }
        else {
            EditPersonFragment editFragment = new EditPersonFragment();
            editFragment = (EditPersonFragment) getFragmentManager().findFragmentById(R.id.edit_person_fragment_container);
            fragment = editFragment;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.exit: //settings();
                finish();
                break;
            case R.id.delete_person:
                deletePerson();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deletePerson() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        fragment.deletePerson(fragment.getPerson());
                        finish();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.delete_question)).setPositiveButton(getString(R.string.yes), dialogClickListener)
                .setNegativeButton(getString(R.string.no), dialogClickListener).show();
    }
}