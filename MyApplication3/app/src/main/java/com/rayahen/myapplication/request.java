package com.example.omar.helpinghand;

import java.util.List;

public class request{
    private String phone;
    private String name;
    private String address;
    private String total;
    private List<Order> order;
    private String state;

    public request() {
    }

    public request(String phone, String name, String address, String total, List<Order> order) {
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.total = total;
        this.order = order;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
