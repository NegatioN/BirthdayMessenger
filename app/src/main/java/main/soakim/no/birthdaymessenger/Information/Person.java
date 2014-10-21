package main.soakim.no.birthdaymessenger.Information;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by NegatioN on 18.10.2014.
 */
public class Person {
    public final static String DATEFORMAT = "yyyy-MM-dd";


    private int id;
    private String name, customMessage;
    private int phoneNumber;
    private Date birthday;

    public Person(int id, String name, int phoneNumber, Date birthday){
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
    }

    public Person(int id, String name, int phoneNumber, String bday){
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        setFormattedDate(bday);
    }

    public Person(int id, String name, int phoneNumber, Date birthday, String customMessage){
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.customMessage = customMessage;
    }

    public Person(String name, int phoneNumber, Date birthday){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
    }

    public Person(){

    }

    public int getDay(){
        int i = Integer.parseInt(getFormattedDate("dd"));
        return i;
    }

    public int getMonth(){
        int i = Integer.parseInt(getFormattedDate("MM"));
        return i;
    }

    public int getYear(){
        int i = Integer.parseInt(getFormattedDate("yyyy"));
        return i;
    }

    public String getFormattedDate(String format){
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(birthday);
    }

    public String getFormattedDate(){
        SimpleDateFormat df = new SimpleDateFormat(DATEFORMAT);
        Log.d("Person.formattedDay", df.format(birthday));
        return df.format(birthday);
    }

    public void setFormattedDate(String dateFromDB){
        SimpleDateFormat df = new SimpleDateFormat(DATEFORMAT);
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

    public String getCustomMessage() {
        return customMessage;
    }

    public void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
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

    public Date getBirthday() {
        return birthday;
    }
}
