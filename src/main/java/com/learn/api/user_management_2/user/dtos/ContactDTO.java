package com.learn.api.user_management_2.user.dtos;

import java.io.Serializable;

import com.learn.api.user_management_2.user.entities.Contact;

import lombok.Data;

public class ContactDTO implements Serializable {

    private String email;
    private String phone;
    private String note;

    public ContactDTO() { }

    public ContactDTO(Contact contact) {
        if (contact != null) {
            this.email = contact.getEmail();
            this.phone =  contact.getPhone();
            this.note = contact.getNote();
        }
    }

    // Getter & Setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
