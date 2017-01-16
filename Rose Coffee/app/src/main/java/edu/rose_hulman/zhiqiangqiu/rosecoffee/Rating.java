package edu.rose_hulman.zhiqiangqiu.rosecoffee;

/**
 * Created by yoons1 on 1/15/2017.
 */
public class Rating {
    private int mCustomerScore;
    private String mCustomerComment;
    private int mDeliveryScore;
    private String mDeliveryComment;
    public Rating(int customerScore,String customerComment, int deliveryScore, String deliveryComment){
        mCustomerScore = customerScore;
        mCustomerComment = customerComment;
        mDeliveryScore = deliveryScore;
        mDeliveryComment = deliveryComment;
    }


    //more rating methods here



    public int getmCustomerScore() {
        return mCustomerScore;
    }

    public void setmCustomerScore(int mCustomerScore) {
        this.mCustomerScore = mCustomerScore;
    }

    public String getmCustomerComment() {
        return mCustomerComment;
    }

    public void setmCustomerComment(String mCustomerComment) {
        this.mCustomerComment = mCustomerComment;
    }

    public int getmDeliveryScore() {
        return mDeliveryScore;
    }

    public void setmDeliveryScore(int mDeliveryScore) {
        this.mDeliveryScore = mDeliveryScore;
    }

    public String getmDeliveryComment() {
        return mDeliveryComment;
    }

    public void setmDeliveryComment(String mDeliveryComment) {
        this.mDeliveryComment = mDeliveryComment;
    }

    public int getmOrderID() {
        return mOrderID;
    }

    public void setmOrderID(int mOrderID) {
        this.mOrderID = mOrderID;
    }

    private int mOrderID;
}
