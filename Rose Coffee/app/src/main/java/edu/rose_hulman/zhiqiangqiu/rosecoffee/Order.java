package edu.rose_hulman.zhiqiangqiu.rosecoffee;

/**
 * Created by yoons1 on 1/15/2017.
 */
public class Order {
    private int orderState;
    // orderState = -1: Order is expired;Was never claimed by any DeliveryPerson.
    // orderState = 0 : Order is just made, pending to be claimed by DeliveryPerson.
    // orderState = 1 : Order is claimed by DeliveryPerson, Customer is waiting for Delivery guy.
    // orderState = 2 : Customer has received a drink and accepted that order is complete.
    // orderState = 3 : Order is complete, it is stored in Order history.
    // orderState 2 and 3 might be merged later
    private int mOrderID;
    private int mCustomerID;
    private String mBuildingName;
    private String mRoomNumber; //including its identifier like "G", ex. G310
    private Time DeliveryTime;
    private int mDeliveryPersonID;
    private Rating mRating;

    public Order(int customerID,int deliveryPersonID,String buildingName, String roomNumber){
        mCustomerID = customerID;
        mDeliveryPersonID = deliveryPersonID;
        mBuildingName = buildingName;
        mRoomNumber = roomNumber;

    }

    // we will write more methods like generate OrderID, etc.



    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    public int getmOrderID() {
        return mOrderID;
    }

    public void setmOrderID(int mOrderID) {
        this.mOrderID = mOrderID;
    }

    public int getmCustomerID() {
        return mCustomerID;
    }

    public void setmCustomerID(int mCustomerID) {
        this.mCustomerID = mCustomerID;
    }

    public String getmBuildingName() {
        return mBuildingName;
    }

    public void setmBuildingName(String mBuildingName) {
        this.mBuildingName = mBuildingName;
    }

    public String getmRoomNumber() {
        return mRoomNumber;
    }

    public void setmRoomNumber(String mRoomNumber) {
        this.mRoomNumber = mRoomNumber;
    }

    public Time getDeliveryTime() {
        return DeliveryTime;
    }

    public void setDeliveryTime(Time deliveryTime) {
        DeliveryTime = deliveryTime;
    }

    public int getmDeliveryPersonID() {
        return mDeliveryPersonID;
    }

    public void setmDeliveryPersonID(int mDeliveryPersonID) {
        this.mDeliveryPersonID = mDeliveryPersonID;
    }

    public Rating getmRating() {
        return mRating;
    }

    public void setmRating(Rating mRating) {
        this.mRating = mRating;
    }
}
