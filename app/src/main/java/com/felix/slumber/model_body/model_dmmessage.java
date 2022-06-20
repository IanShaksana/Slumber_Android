package com.felix.slumber.model_body;

public class model_dmmessage {

    public model_dmmessage(model_profile model_profile, String message) {
        this.id = model_profile.getId();
        this.email = model_profile.getEmail();
        this.message = message;
    }

    private String id;
    private String email;
    private String message;
}
