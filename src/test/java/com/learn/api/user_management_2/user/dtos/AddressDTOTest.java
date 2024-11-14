package com.learn.api.user_management_2.user.dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.learn.api.user_management_2.user.entities.Address;

public class AddressDTOTest {
    @Test
    public void testAddressDTOConstructor1() {
        AddressDTO addressDTO = new AddressDTO();
        assertEquals(null, addressDTO.getAddress());
        assertEquals(null, addressDTO.getAddress2());
        assertEquals(null, addressDTO.getCity());
        assertEquals(null, addressDTO.getCountry());
        assertEquals(null, addressDTO.getZipCode());
    }

    @Test
    public void testAddressDTOConstructor2() {
        Address address = new Address();
        address.setCity("Brest");
        address.setCountry("France");
        address.setZipCode("29200");

        AddressDTO addressDTO = new AddressDTO(address);
        assertEquals("Brest", addressDTO.getCity());
        assertEquals("France", addressDTO.getCountry());
        assertEquals("29200", addressDTO.getZipCode());
    }
}
