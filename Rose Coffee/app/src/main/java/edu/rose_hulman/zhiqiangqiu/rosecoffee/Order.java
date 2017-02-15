package edu.rose_hulman.zhiqiangqiu.rosecoffee;

import com.google.firebase.database.Exclude;

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
    private String customer;
    private String location;
    private ArrayList<Drink> drinks = new ArrayList<>();
    private ArrayList<String> snacks = new ArrayList<>();
    private double totalPrice=0;
    private String time;
    private String delivery;

    public Order() {
        //Empty
    }

    public Order(String customer, String location, String time, ArrayList<Drink> drinks, ArrayList<String> snacks, double totalPrice) {
        this.customer = customer;
        this.location = location;
        this.time = time;
        this.drinks = drinks;
        this.snacks = snacks;
        this.totalPrice = totalPrice;
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

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
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

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    @Exclude
    public int getSnackCount() {return snacks.size();}

    @Exclude
    public int getDrinkCount() {return drinks.size();}

    public void setDrinks(ArrayList<Drink> drinks){
        this.drinks=drinks;
    }
    public void setSnacks(ArrayList<String> snacks){
        this.snacks=snacks;
    }

    public ArrayList<Drink> getDrinks() {
        return drinks;
    }

    public ArrayList<String> getSnacks() {
        return snacks;
    }
    public void addToTotalPrice(double price) {
        totalPrice+=price;
    }
}
