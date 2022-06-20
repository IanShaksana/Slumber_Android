package com.felix.slumber.model;

import android.net.Uri;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class model_uri_title_source {
    private Uri uri;
    private String title;

    public model_uri_title_source(Uri uri, String title) {
        this.uri = uri;
        this.title = title;
    }
}
