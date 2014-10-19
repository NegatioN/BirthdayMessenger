package main.soakim.no.birthdaymessenger;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import main.soakim.no.birthdaymessenger.Information.Person;

/**
 * Created by Sondre on 20.10.2014.
 */
public class EditPersonFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_person_fragment, null);

        Bundle b = getArguments();
        Person p = BirthdayMessenger.persons.get(b.getInt("position"));

        EditText nameEdit = (EditText) v.findViewById(R.id.nameEditText);
        nameEdit.setText(p.getName());

        EditText numEdit = (EditText) v.findViewById(R.id.numEditText);
        numEdit.setText(p.getPhoneNumber()+"");

        DatePicker datePicker = (DatePicker) v.findViewById(R.id.datePicker);
        datePicker.setMaxDate(System.currentTimeMillis());
        datePicker.init(p.getYear(), p.getMonth(), p.getDay(), null);

        return v;
    }
}