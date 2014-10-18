package main.soakim.no.birthdaymessenger.Information;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by NegatioN on 18.10.2014.
 */
public class Person {

    private int id;
    private String name;
    private int phoneNumber;
    private Date birthday;

    public Person(int id, String name, int phoneNumber, Date birthday){
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
    }

    public Person(String name, int phoneNumber, Date birthday){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
    }

    public String getFormattedDate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Log.d("Person.formattedDay", df.format(birthday));
        return df.format(birthday);
    }

    public void setFormattedDate(String dateFromDB){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            birthday = df.parse(dateFromDB);
        }catch(ParseException e){
            e.printStackTrace();
            Log.d("Person.setFormattedDate", "ParseException");
            birthday = null;
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
