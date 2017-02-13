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
    private int orderID;
    private int customerID;
    private String location;
    private ArrayList<Drink> drinks;
    private ArrayList<String> snacks;
    private double totalPrice;
    private Time time;
    private int deliveryID;

    public Order(int customerID,int deliveryPersonID,String location){
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
//    public int getOrderState() {
//        return orderState;
//    }
//
//    public void setOrderState(int orderState) {
//        this.orderState = orderState;
//    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
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

    public int getDeliveryID() {
        return deliveryID;
    }

    public void setDeliveryID(int deliveryID) {
        this.deliveryID = deliveryID;
    }



    // we will write more methods like generate OrderID, etc.




}
