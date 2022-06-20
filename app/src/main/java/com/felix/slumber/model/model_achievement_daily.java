package com.felix.slumber.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class model_achievement_daily {
    private String id;
    private String title;
    private Integer rep;
    private Integer point;
    public model_achievement_daily(String id, String title, Integer rep, Integer point) {
        this.id = id;
        this.title = title;
        this.rep = rep;
        this.point = point;
    }
}
