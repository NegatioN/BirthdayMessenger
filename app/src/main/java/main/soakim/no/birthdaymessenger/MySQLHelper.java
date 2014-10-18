package main.soakim.no.birthdaymessenger;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import main.soakim.no.birthdaymessenger.Information.Person;

/**
 * Created by NegatioN on 18.10.2014.
 */
public class MySQLHelper extends SQLiteOpenHelper{

    private final static String TABLE_PERSONS = "Perssons";
    private final static String DB_NAME = "BirthdayDatabase";
    //Database Key-values
    public final static String KEY_ID = "_ID", KEY_NAME = "_NAME", KEY_PHONE = "_PHONE", KEY_BIRTHDAY = "_BIRTHDAY";

    private final static int DB_VERSION = 1;

    public MySQLHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATETABLE " + TABLE_PERSONS + "(" +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_NAME + " TEXT, " +
                KEY_PHONE + " INTEGER, " +
                KEY_BIRTHDAY + " TEXT)";
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

        db.insert(TABLE_PERSONS, null, values);
        db.close();

    }



}