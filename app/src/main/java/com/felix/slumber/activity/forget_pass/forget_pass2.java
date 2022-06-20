package com.felix.slumber.activity.forget_pass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.felix.slumber.R;

public class forget_pass2 extends AppCompatActivity {

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_pass_layout2);
        btn = (Button) findViewById(R.id.forget_pass2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forget_pass2.this.startActivity(new Intent(forget_pass2.this, forget_pass3.class));
                finish();
            }
        });
    }
}