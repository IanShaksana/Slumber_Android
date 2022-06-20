package com.felix.slumber.model_body;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class model_register {
    private String email;
    private String password;
    private String name;
    // private Date dob;

    private String dob;
    private String address;
    private String gender;

    public model_register(String email, String password, String name, String dob, String address, String gender) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.gender = gender;
    }
}
