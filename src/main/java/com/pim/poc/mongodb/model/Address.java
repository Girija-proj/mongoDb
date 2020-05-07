package com.pim.poc.mongodb.model;

public class Address {

    private String houseNo;
    private  String block;
    private String streetNo;
    private String city;
    private  String state;
    private  String country;

    public String getHouseNo () {
        return houseNo;
    }

    public void setHouseNo (String houseNo) {
        this.houseNo = houseNo;
    }

    public String getBlock () {
        return block;
    }

    public void setBlock (String block) {
        this.block = block;
    }

    public String getStreetNo () {
        return streetNo;
    }

    public void setStreetNo (String streetNo) {
        this.streetNo = streetNo;
    }

    public String getCity () {
        return city;
    }

    public void setCity (String city) {
        this.city = city;
    }

    public String getState () {
        return state;
    }

    public void setState (String state) {
        this.state = state;
    }

    public String getCountry () {
        return country;
    }

    public void setCountry (String country) {
        this.country = country;
    }
}
