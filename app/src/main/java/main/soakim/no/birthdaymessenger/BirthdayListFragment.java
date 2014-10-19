package main.soakim.no.birthdaymessenger;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import main.soakim.no.birthdaymessenger.Information.Person;

/**
 * Created by Sondre on 19.10.2014.
 */
public class BirthdayListFragment extends Fragment {

    private ArrayList<Person> persons = new ArrayList<Person>();
    private static PersonChanged listener;

    public interface PersonChanged {
        public void nameChanged(String name);
    }

    public void onAttatch(Activity activity){
        super.onAttach(activity);
        try {
            listener = (PersonChanged) activity;
            System.out.println("Added Listener");

        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement OnTitleSelectedListener");
        }
    }

    public BirthdayListFragment() {
        persons.add(new Person(1, "Joakim", 12345678, new Date(1990, 12, 15)));
        persons.add(new Person(2, "Sondre", 98765432, new Date(1992, 9, 17)));
        persons.add(new Person(3, "Marlar", 11111111, new Date(1993, 2, 3)));

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.birthday_list, container, false);
        ListView lv = (ListView) v.findViewById(R.id.persons);

        String[] values = new String[persons.size()];
        for(int i = 0; i < values.length; i++)
            values[i] = persons.get(i).getName();

        final ArrayList<String> list = new ArrayList<String>();
        for(int i = 0; i < values.length; i++)
            list.add(values[i]);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String data = adapter.getItem(position);
            }
        });
        return v;
    }
}
