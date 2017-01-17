package edu.rose_hulman.zhiqiangqiu.rosecoffee;

import java.util.ArrayList;

/**
 * Created by yoons1 on 1/15/2017.
 */

public class User implements Customer,DeliveryPerson{
    private int mUserID;
    private boolean mIsCustomer;
    private String mName;
    private String mPhoneNumber;
    private Order mDefaultOrder;
    private ArrayList<Order> mOrderHistory;
    public User(String userName){
        mName = userName; // this is temporary user's name before he/she changes his/her name from the SettingFragment
    }

    //user related method (generate ID, password related, authentication, etc)



    public boolean ismIsCustomer() {
        return mIsCustomer;
    }

    public void setmIsCustomer(boolean mIsCustomer) {
        this.mIsCustomer = mIsCustomer;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public Order getmDefaultOrder() {
        return mDefaultOrder;
    }

    public void setmDefaultOrder(Order mDefaultOrder) {
        this.mDefaultOrder = mDefaultOrder;
    }

    public ArrayList<Order> getmOrderHistory() {
        return mOrderHistory;
    }

    public void setmOrderHistory(ArrayList<Order> mOrderHistory) {
        this.mOrderHistory = mOrderHistory;
    }
}
