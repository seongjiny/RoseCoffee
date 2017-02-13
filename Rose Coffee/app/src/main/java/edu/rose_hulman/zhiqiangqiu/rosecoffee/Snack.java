package edu.rose_hulman.zhiqiangqiu.rosecoffee;

/**
 * Created by yoons1 on 2/12/2017.
 */

public class Snack implements MenuItem {
    private String name;

    public Snack(String name){
        this.name=name;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSize() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getComment() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setName(String name) {
        this.name=name;
    }

    @Override
    public void setSize(String size) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setComment(String comment) {
        throw new UnsupportedOperationException();
    }
}
