package com.felix.slumber.drawer.real;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.felix.slumber.R;

public class tutorial extends AppCompatActivity {

    private ImageView cross;
    private TextView txt1;
    private TextView txt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_tutorial);
        cross = findViewById(R.id.cross);
        txt1 = findViewById(R.id.tutorial);
        txt2 = findViewById(R.id.faqs);
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://youtu.be/SleTIpKXbU8"));
                intent.setPackage("com.google.android.youtube");
                startActivity(intent);
            }
        });


        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tutorial.this.startActivity(new Intent(tutorial.this, faqs.class));
            }
        });
    }
}