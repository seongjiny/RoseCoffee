package edu.rose_hulman.zhiqiangqiu.rosecoffee;

import java.util.ArrayList;

/**
 * Created by yoons1 on 1/15/2017.
 */
public class Order {
//    private int orderState;
//    // orderState = -1: Order is expired;Was never claimed by any DeliveryPerson.
//    // orderState = 0 : Order is just made, pending to be claimed by DeliveryPerson.
//    // orderState = 1 : Order is claimed by DeliveryPerson, Customer is waiting for Delivery guy.
//    // orderState = 2 : Customer has received a drink and accepted that order is complete.
//    // orderState = 3 : Order is complete, it is stored in Order history.
//    // orderState 2 and 3 might be merged later
    private String orderID;
    private String customerID;
    private String location;
    private ArrayList<Drink> drinks;
    private ArrayList<String> snacks;
    private double totalPrice;
    private Time time;
    private String deliveryID;

    public Order(String customerID,String deliveryPersonID,String location){
        this.customerID = customerID;
        deliveryID = deliveryPersonID;
        this.location = location;
    }

    public void addDrink(Drink drink){
        drinks.add(drink);
    }

    public void deleteDrink(Drink drink){
        drinks.remove(drink);
    }

    public void addSnack(String snack){
        snacks.add(snack);
    }

    public void removeSnack(String snack){
        snacks.remove(snack);
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getDeliveryID() {
        return deliveryID;
    }

    public void setDeliveryID(String deliveryID) {
        this.deliveryID = deliveryID;
    }
}
