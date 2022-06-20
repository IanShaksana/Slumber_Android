package com.felix.slumber.model;

import android.net.Uri;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class model_article_source {
    private Integer resource_title;
    private Integer resource_content;
    private Uri image_res;

    public model_article_source(Integer resource_title, Integer resource_content, Uri image_res) {
        this.resource_title = resource_title;
        this.resource_content = resource_content;
        this.image_res = image_res;
    }
}
