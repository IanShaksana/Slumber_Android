package com.felix.slumber.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class model_store_item {
    private String id;
    private Integer ic_src;
    private String title;
    private String subtitle;
    private Integer point;

    public model_store_item(String id, Integer ic_src, String title, String subtitle, Integer point) {
        this.id = id;
        this.ic_src = ic_src;
        this.title = title;
        this.subtitle = subtitle;
        this.point = point;
    }
}
