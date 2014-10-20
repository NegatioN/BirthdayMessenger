package main.soakim.no.birthdaymessenger;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import main.soakim.no.birthdaymessenger.Information.Person;

/**
 * Created by Sondre on 20.10.2014.
 */
public class EditPersonFragment extends Fragment {

    //private ListView listview;

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

        Button btn = (Button) v.findViewById(R.id.saveButton);
        btn.setTag(p.getId());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View parent = (View) v.getParent();
                Person p = getPerson(Integer.parseInt(v.getTag()+""));
                saveEdit(parent, p);
            }
        });

        return v;
    }

    private Person getPerson(int id) {
        List<Person> persons = BirthdayMessenger.persons;
        for(Person p : persons)
            if(p.getId() == id) return p;
        return null;
    }

    private void saveEdit(View layout, Person person) {
        String name = "";
        int num = -1;
        int y = -1;
        int m = -1;
        int d = -1;
        try{
            name = ((EditText) layout.findViewById(R.id.nameEditText)).getText().toString().trim();
            num = Integer.parseInt(((EditText) layout.findViewById(R.id.numEditText)).getText().toString().trim());
            if(num < 10000000 || num > 99999999) throw new NumberFormatException("");
            DatePicker dp = (DatePicker) layout.findViewById(R.id.datePicker);
            y = dp.getYear();
            m = dp.getMonth();
            d = dp.getDayOfMonth();
        } catch(NumberFormatException e) {
            Toast.makeText(getActivity(), "Wrong number format!", Toast.LENGTH_SHORT).show();
        }

        person.setName(name);
        person.setPhoneNumber(num);
        person.setFormattedDate(y + "-" + m + "-" + d);

        BirthdayMessenger.updateDb();

        //((BaseAdapter)listview.getAdapter()).notifyDataSetChanged();
    }
}