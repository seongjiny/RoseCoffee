package edu.rose_hulman.zhiqiangqiu.rosecoffee;

/**
 * Created by yoons1 on 2/14/2017.
 */

public class MenuDrink {
    private String name;
    private double priceSmall;
    private double priceMedium;
    private double priceLarge;

    public MenuDrink(){
        name = "";
        priceSmall = 0.0;
        priceMedium = 0.0;
        priceLarge = 0.0;
    }
    public MenuDrink(String name, double priceSmall, double priceMedium, double priceLarge){
        this.name = name;
        this.priceSmall = priceSmall;
        this.priceMedium = priceMedium;
        this.priceLarge = priceLarge;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPriceSmall() {
        return priceSmall;
    }

    public void setPriceSmall(double priceSmall) {
        this.priceSmall = priceSmall;
    }

    public double getPriceMedium() {
        return priceMedium;
    }

    public void setPriceMedium(double priceMedium) {
        this.priceMedium = priceMedium;
    }

    public double getPriceLarge() {
        return priceLarge;
    }

    public void setPriceLarge(double priceLarge) {
        this.priceLarge = priceLarge;
    }
}
