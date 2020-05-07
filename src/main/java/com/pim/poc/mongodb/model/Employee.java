package com.pim.poc.mongodb.model;


import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.List;

public class Employee {

    @BsonProperty(value = "EmpId")
    private int eid;

    @BsonProperty(value = "Name")
    private String name;

    @BsonProperty(value = "Skills")
    private List<String> skills;

    @BsonProperty(value = "Department")
    private String dept;

    @BsonProperty(value = "Customer Address")
    private CustomerAddress customerAddress;

    public int getEid () {
        return eid;
    }

    public void setEid (int eid) {
        this.eid = eid;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public List<String> getSkills () {
        return skills;
    }

    public void setSkills (List<String> skills) {
        this.skills = skills;
    }

    public String getDept () {
        return dept;
    }

    public void setDept (String dept) {
        this.dept = dept;
    }

    public CustomerAddress getCustomerAddress () {
        return customerAddress;
    }

    public void setCustomerAddress (CustomerAddress customerAddress) {
        this.customerAddress = customerAddress;
    }
}
