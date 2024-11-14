package com.learn.api.user_management_2.user.dtos;

import java.io.Serializable;

import com.learn.api.user_management_2.user.entities.Address;

import lombok.Data;

@Data
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
}
