package main.soakim.no.birthdaymessenger;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Sondre on 20.10.2014.
 */
public class BirthdayListFragment extends ListFragment{

    private ListFragmentItemClickListener listener;
    private static ArrayAdapter<String> adapter;

    // static String[] strings = new String[] { "empty" };

    public interface ListFragmentItemClickListener {
        void onListFragmentItemClick(int position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            listener = (ListFragmentItemClickListener) activity;
        }catch(Exception e){
            Toast.makeText(activity.getBaseContext(), "Exception",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //strings = BirthdayMessenger.personsName;
        adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, BirthdayMessenger.personsName);

        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        listener.onListFragmentItemClick(position);
    }

    public static void notifyListChange() {
        if(adapter == null) return;
        //strings = BirthdayMessenger.personsName;
        adapter.notifyDataSetChanged();
    }
}