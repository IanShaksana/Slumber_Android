package com.felix.slumber.model_body;

import lombok.*;

@Getter
@Setter

public class model_activity {

    public model_activity() {

    }

    public model_activity(model_profile profile, String tipe, String idarticle, String idalarm, String idtriviauser) {

        setIdprofile(profile.getId());
        setTipe(tipe);
        setIdalarm(idalarm);
        setIdarticle(idarticle);
        setIdtriviauser(idtriviauser);
    }

    public model_activity(model_profile profile, String tipe, String idarticle, String idalarm, String idtriviauser, Integer answer) {

        setIdprofile(profile.getId());
        setTipe(tipe);
        setIdalarm(idalarm);
        setIdarticle(idarticle);
        setIdtriviauser(idtriviauser);
        setAnswer(answer);
    }

    public model_activity(model_profile profile, String idtask, String tipe, String idarticle, String idalarm, String idtriviauser) {

        setIdprofile(profile.getId());
        setIdtask(idtask);
        setTipe(tipe);
        setIdalarm(idalarm);
        setIdarticle(idarticle);
        setIdtriviauser(idtriviauser);
    }

    // credential
    private String idprofile;
    private String idtask;

    // article, music , alarm, trivia
    private String tipe;

    // utk detail article
    private String idarticle;

    // utk detail alarm
    private String idalarm;

    // utk detail trivia
    private String idtriviauser;
    private Integer answer;

}