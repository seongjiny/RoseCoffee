package edu.rose_hulman.zhiqiangqiu.rosecoffee;

/**
 * Created by yoons1 on 2/12/2017.
 */
public class Drink implements MenuItem{
    private String size;
    private String name;
    private String comment;

    public Drink() {
        //Empty
    }

    public Drink(String name,String size, String comment){
        this.name=name;
        this.size=size;
        this.comment=comment;
    }
    @Override
    public String getSize() {
        return size;
    }
    @Override
    public void setSize(String size) {
        this.size = size;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String getComment() {
        return comment;
    }
    @Override
    public void setComment(String comment) {
        this.comment = comment;
    }
}
