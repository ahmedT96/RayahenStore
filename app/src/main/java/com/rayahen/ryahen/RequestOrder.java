package com.rayahen.ryahen;

import java.util.List;

public class RequestOrder{
    private int id;
    private String phone;
    private String name;
    private String address;
    private String buliding;
    private String flat;
    private String commant;


    private String total;
    private List<Order> order;

    public RequestOrder( int id,String phone, String name, String address, String total, String buliding, String flat, String commant, List<Order> order) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.total = total;
        this.order = order;
        this.buliding = buliding;
        this.flat = flat;
        this.commant = commant;

    }

    public String getBuliding() {
        return buliding;
    }

    public void setBuliding(String buliding) {
        this.buliding = buliding;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getCommant() {
        return commant;
    }

    public void setCommant(String commant) {
        this.commant = commant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }
}
