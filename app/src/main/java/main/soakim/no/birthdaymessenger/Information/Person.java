package main.soakim.no.birthdaymessenger.Information;

import android.text.format.Time;

/**
 * Created by NegatioN on 18.10.2014.
 */
public class Person {

    private int id;
    private String name;
    private int phoneNumber;
    private Time birthday;

    public Person(int id, String name, int phoneNumber, Time birthday){
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
    }

    public Person(String name, int phoneNumber, Time birthday){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
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

    public Time getBirthday() {
        return birthday;
    }

    public void setBirthday(Time birthday) {
        this.birthday = birthday;
    }
}
