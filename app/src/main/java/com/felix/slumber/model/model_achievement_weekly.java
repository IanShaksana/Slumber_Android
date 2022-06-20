package com.felix.slumber.model;

import android.net.Uri;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class model_achievement_weekly {
    private Uri uri;
    private String title;

    public model_achievement_weekly(Uri uri, String title) {
        this.uri = uri;
        this.title = title;
    }
}
