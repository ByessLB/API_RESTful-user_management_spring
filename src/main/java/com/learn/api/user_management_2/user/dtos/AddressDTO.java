package com.learn.api.user_management_2.user.dtos;

import java.io.Serializable;

import com.learn.api.user_management_2.user.entities.Address;

import lombok.Data;

public class AddressDTO implements Serializable {

    private String address;
    private String address2;
    private String city;
    private String country;
    private String zipCode;

    public AddressDTO() {}

    public AddressDTO(Address address) {
        if (address != null) {
            this.address = address.getAddress();
            this.address2 = address.getAddress2();
            this.city = address.getCity();
            this.country = address.getCountry();
            this.zipCode = address.getZipCode();
        }
    }

    // Getter & Setter
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
