package main.soakim.no.birthdaymessenger;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import main.soakim.no.birthdaymessenger.Information.Person;

/**
 * Created by Sondre on 23.10.2014.
 */
public class NewPersonFragment extends Fragment {

    private static String customMessage = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.new_person_fragment, null);

        DatePicker datePicker = (DatePicker) v.findViewById(R.id.datePicker);
        datePicker.setMaxDate(System.currentTimeMillis());

        Button btn = (Button) v.findViewById(R.id.saveButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View parent = (View) v.getParent().getParent();
                newEdit(parent);
            }
        });

        Button btnedit = (Button) v.findViewById(R.id.editMessageButton);
        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View parent = (View) v.getParent().getParent();
                editMessage(parent);
            }
        });
        return v;
    }

    private void editMessage(View layout) {
        final EditText input = new EditText(getActivity());

        new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.custom_message))
                .setMessage(customMessage.equals("") ? getString(R.string.default_message) : customMessage)
                .setView(input)
                .setPositiveButton(getString(R.string.set), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Editable value = input.getText();
                        String text = value.toString().trim();
                        customMessage = text;
                    }
                }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        }).show();
    }

    private void newEdit(View layout) {
        String name = "";
        int num = -1;
        int y = -1;
        int m = -1;
        int d = -1;
        try{
            name = ((EditText) layout.findViewById(R.id.nameEditText)).getText().toString().trim();
            if(name.trim().equals("")) throw new IllegalArgumentException();

            num = Integer.parseInt(((EditText) layout.findViewById(R.id.numEditText)).getText().toString().trim());
            if(num < 10000000 || num > 99999999) throw new NumberFormatException("");

            DatePicker dp = (DatePicker) layout.findViewById(R.id.datePicker);
            y = dp.getYear();
            m = dp.getMonth() + 1; //month counter starts at 0
            d = dp.getDayOfMonth();

            Log.d("datepicker", "Year:" + y + " month: " + m + " day: " + d);
        } catch(NumberFormatException e) {
            Toast.makeText(getActivity(), getString(R.string.num_error), Toast.LENGTH_SHORT).show();
            return;
        } catch (IllegalArgumentException e) {
            Toast.makeText(getActivity(), getString(R.string.name_error), Toast.LENGTH_SHORT).show();
            return;
        }

        Person person = new Person();
        person.setName(name);
        person.setPhoneNumber(num);
        person.setFormattedDate(y + "-" + m + "-" + d);

        person.setCustomMessage(customMessage.equals("") ? null : customMessage);

        MySQLHelper db = new MySQLHelper(getActivity());
        db.addPerson(person);

        BirthdayMessenger.updateList();
        BirthdayListFragment.notifyListChange();

        getActivity().finish();
    }
}