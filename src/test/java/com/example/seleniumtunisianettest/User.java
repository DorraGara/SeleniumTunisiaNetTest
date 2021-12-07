package com.example.seleniumtunisianettest;

import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
    public User(String firstName, String lastName, String email, String password, Date birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.dateOfBirth = birthday;
    }

    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public Date dateOfBirth;

    public String dateBirthFormat(){
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat dateFormatter = new SimpleDateFormat(pattern);
        return dateFormatter.format(this.dateOfBirth);
    }
}