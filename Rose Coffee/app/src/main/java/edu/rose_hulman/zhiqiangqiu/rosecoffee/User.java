package edu.rose_hulman.zhiqiangqiu.rosecoffee;

/**
 * Created by yoons1 on 1/15/2017.
 */

public class User {
    public static final String INCOMPLETE = "IMCOMPLETE";
    public static final String TO_CLAIM = "TO_CLAIM";
    public static final String CLAIMED = "CLAIMED";
    public static final String DELIVERED = "DELIVERED";
    public static final String RECEIVED = "Received";


    private String uid;
    private boolean is_delivery;
    private String name;
    private String email;
    private String phone;
    private String state = INCOMPLETE;

    public User() {
        //Empty constructor
    }

    public boolean isToClaim() {
        return state == TO_CLAIM;
    }

    public boolean isDelivered() {
        return state == DELIVERED;
    }

    public boolean isReceived() {
        return state == RECEIVED;
    }

    public boolean isClaimed() {
        return state == CLAIMED;
    }

    public boolean isIncomplete() {
        return state == INCOMPLETE;
    }

    public void changeState(String state) {
        this.state = state;
    }

    public void setState(String state) {
        this.state = state;
    }

    //user related method (generate ID, password related, authentication, etc)

    public boolean ismIsCustomer() {
        return is_delivery;
    }

    public void setmIsCustomer(boolean mIsDelivery) {
        this.is_delivery = mIsDelivery;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
