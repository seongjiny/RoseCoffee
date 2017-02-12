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
    private int orderID;
    private int customerID;
    private String buildingName;
    private String roomNumber; //including its identifier like "G", ex. G310
    private Time deliveryTime;
    private int deliveryID;
    private Rating rating;

    public Order(int customerID,int deliveryPersonID,String buildingName, String roomNumber){
        this.customerID = customerID;
        deliveryID = deliveryPersonID;
        this.buildingName = buildingName;
        this.roomNumber = roomNumber;

    }

    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Time getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Time deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public int getDeliveryID() {
        return deliveryID;
    }

    public void setDeliveryID(int deliveryID) {
        this.deliveryID = deliveryID;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }


    // we will write more methods like generate OrderID, etc.




}
