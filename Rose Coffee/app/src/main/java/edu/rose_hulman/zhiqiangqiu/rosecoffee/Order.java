package edu.rose_hulman.zhiqiangqiu.rosecoffee;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;

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
    private String customer;
    private String location;
    private List<Drink> drinks;
    private List<String> snacks;
    private double totalPrice;
    private String time;
    private String delivery;

    public Order() {
        //Empty
    }

    public Order(String customerID, String location, String time, ArrayList<Drink> drinks, ArrayList<String> snacks, double totalPrice) {
        this.customer = customerID;
        this.location = location;
        this.time = time;
        this.drinks = drinks;
        this.snacks = snacks;
        this.totalPrice = totalPrice;
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

    @Exclude
    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getCustomerID() {
        return customer;
    }

    public void setCustomerID(String customerID) {
        this.customer = customerID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDeliveryID() {
        return delivery;
    }

    public void setDeliveryID(String deliveryID) {
        this.delivery = deliveryID;
    }

    public int getSnackCount() {return snacks.size();}

    public int getDrinkCount() {return drinks.size();}
}
