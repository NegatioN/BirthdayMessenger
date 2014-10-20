package main.soakim.no.birthdaymessenger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import main.soakim.no.birthdaymessenger.Information.Person;

/**
 * Created by NegatioN on 18.10.2014.
 */
public class MySQLHelper extends SQLiteOpenHelper{

    private final static String TABLE_PERSONS = "Perssons";
    private final static String DB_NAME = "BirthdayDatabase";
    //Database Key-values
    public final static String KEY_ID = "_ID", KEY_NAME = "_NAME", KEY_PHONE = "_PHONE", KEY_BIRTHDAY = "_BIRTHDAY", KEY_MESSAGE = "_MESSAGE";

    private final static int DB_VERSION = 1;

    public MySQLHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + TABLE_PERSONS + "(" +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_NAME + " TEXT, " +
                KEY_PHONE + " INTEGER, " +
                KEY_BIRTHDAY + " TEXT, " +
                KEY_MESSAGE + " TEXT)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONS);
        onCreate(sqLiteDatabase);
    }

    // CRUD Functionality for DB.

    //adds a person to the database
    public void addPerson(Person person){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, person.getName());
        values.put(KEY_PHONE, person.getPhoneNumber());
        values.put(KEY_BIRTHDAY, person.getFormattedDate());
        if(person.getCustomMessage() != null)
            values.put(KEY_MESSAGE, person.getCustomMessage());
        //TODO MAYBE HAVE TO ADD NORMAL MESSAGE TO ALL PERSON-OBJECTS IF BUGS

        db.insert(TABLE_PERSONS, null, values);
        db.close();

    }

    //returns a single person with a given ID
    public Person getPerson(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PERSONS, new String[]{KEY_ID, KEY_NAME, KEY_PHONE, KEY_BIRTHDAY, KEY_MESSAGE}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        Person foundPerson = new Person();
        if(cursor != null) {
            cursor.moveToFirst();

            //sets all cursor-information to Person-object based on KEY-value placement in query
            foundPerson.setId(id);
            foundPerson.setName(cursor.getString(1));
            foundPerson.setPhoneNumber(Integer.parseInt(cursor.getString(2)));
            foundPerson.setFormattedDate(cursor.getString(3));
            if(!cursor.isNull(4))
                foundPerson.setCustomMessage(cursor.getString(4));

        }

        return foundPerson;
    }

    //returns all people currently in the database
    public List<Person> getAllPersons(){
        List<Person> persons = new ArrayList<Person>();
        String query = "SELECT * FROM " + TABLE_PERSONS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst())
        do{
            Person person = new Person();

            person.setId(Integer.parseInt(cursor.getString(0)));
            person.setName(cursor.getString(1));
            person.setPhoneNumber(Integer.parseInt(cursor.getString(2)));
            person.setFormattedDate(cursor.getString(3));
            if(!cursor.isNull(4))
                person.setCustomMessage(cursor.getString(4));

            persons.add(person);
        }while(cursor.moveToNext());

        return persons;
    }

    //gets us all the people that has a birthday on the given yyyy-MM-dd formatted datestring
    public List<Person> getPersonWithBirthday(String formattedDate){
        List<Person> persons = new ArrayList<Person>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_PERSONS + " WHERE " + KEY_BIRTHDAY + " = " + formattedDate;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst())
            do{
                Person person = new Person();

                person.setId(Integer.parseInt(cursor.getString(0)));
                person.setName(cursor.getString(1));
                person.setPhoneNumber(Integer.parseInt(cursor.getString(2)));
                person.setFormattedDate(cursor.getString(3));
                if(!cursor.isNull(4))
                    person.setCustomMessage(cursor.getString(4));

                persons.add(person);
            }while(cursor.moveToNext());


        return persons;
    }

    public void deletePerson(Person person){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_PERSONS, KEY_ID + " =?", new String[]{String.valueOf(person.getId())});
        db.close();
    }

    public int updatePerson(Person person){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, person.getName());
        values.put(KEY_PHONE, person.getPhoneNumber());
        values.put(KEY_BIRTHDAY, person.getFormattedDate());
        if(person.getCustomMessage() != null)
            values.put(KEY_MESSAGE, person.getCustomMessage());

        return db.update(TABLE_PERSONS, values, KEY_ID + "=?", new String[]{String.valueOf(person.getId())});
    }



}
