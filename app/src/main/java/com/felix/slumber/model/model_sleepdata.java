package com.felix.slumber.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class model_sleepdata {
    private Integer avg_insomina = 0;
    private Integer avg_sleep_time = 0;
    private Date sleep_time;
    private Date wake_time;

    public model_sleepdata() {
    }
}
