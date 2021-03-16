package com.cloume.maps.commons.model.base;


import java.io.Serializable;


public class Paging<T> implements Serializable {

    public static <T> Paging of(T data, long count){
        return new Paging(data, count);
    }

    private Paging(){}
    private Paging(T data, long count){
        this.setData(data);
        this.setCount(count);
    }

    private long count;

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
