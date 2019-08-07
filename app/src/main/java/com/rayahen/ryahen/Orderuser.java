package com.rayahen.ryahen;

public class Orderuser{
    int id;
    String name,total,adress,build,flat,nots;
    double lat,lang;

    public Orderuser(int id, String name,String build,String flat,String nots, String total, String adress,double lat,double lang) {
        this.id = id;
        this.name = name;
        this.total = total;
        this.adress = adress;
        this.build = build;
        this.flat = flat;
        this.nots = nots;
        this.lat = lat;
        this.lang = lang;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getNots() {
        return nots;
    }

    public void setNots(String nots) {
        this.nots = nots;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLang() {
        return lang;
    }

    public void setLang(double lang) {
        this.lang = lang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
