package com.pim.poc.mongodb.model;

import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.List;

public class CustomerAddress {

    //@BsonProperty(value = " Address")
    private List<Address> address;

    public List<Address> getAddress () {
        return address;
    }

    public void setAddress (List<Address> address) {
        this.address = address;
    }
}
