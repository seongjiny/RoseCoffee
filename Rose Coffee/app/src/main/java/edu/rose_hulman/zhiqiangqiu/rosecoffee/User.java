package edu.rose_hulman.zhiqiangqiu.rosecoffee;

import com.google.firebase.database.Exclude;

/**
 * Created by yoons1 on 1/15/2017.
 */

public class User {

    private String uid;
    private boolean is_customer;
    private String name;
    private String email;

    public User() {
        //Empty constructor
    }
    //user related method (generate ID, password related, authentication, etc)

    public boolean isCustomer() {
        return is_customer;
    }

    public void setIsCustomer(boolean isCustomer) {
        this.is_customer = isCustomer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
