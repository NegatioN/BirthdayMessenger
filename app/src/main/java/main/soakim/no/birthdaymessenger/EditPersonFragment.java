package main.soakim.no.birthdaymessenger;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import main.soakim.no.birthdaymessenger.Information.Person;

/**
 * Created by Sondre on 20.10.2014.
 */
public class EditPersonFragment extends Fragment {

    private Person person;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_person_fragment, null);

        Bundle b = getArguments();
        Person p = BirthdayMessenger.persons.get(b.getInt("position"));

        person = p;

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
            if(name.trim().equals("")) throw new IllegalArgumentException();

            num = Integer.parseInt(((EditText) layout.findViewById(R.id.numEditText)).getText().toString().trim());
            if(num < 10000000 || num > 99999999) throw new NumberFormatException("");

            DatePicker dp = (DatePicker) layout.findViewById(R.id.datePicker);
            y = dp.getYear();
            m = dp.getMonth();
            d = dp.getDayOfMonth();
        } catch(NumberFormatException e) {
            Toast.makeText(getActivity(), getString(R.string.num_error), Toast.LENGTH_SHORT).show();
            return;
        } catch (IllegalArgumentException e) {
            Toast.makeText(getActivity(), getString(R.string.name_error), Toast.LENGTH_SHORT).show();
            return;
        }

        person.setName(name);
        person.setPhoneNumber(num);
        person.setFormattedDate(y + "-" + m + "-" + d);

        MySQLHelper db = new MySQLHelper(getActivity());
        db.updatePerson(person);

        BirthdayMessenger.updateList();
        BirthdayListFragment.notifyListChange();
    }

    public void deletePerson(Person person) {
        MySQLHelper db = new MySQLHelper(getActivity());
        db.deletePerson(person);

        BirthdayMessenger.updateList();
    }

    public Person getPerson() {
        return person;
    }
}