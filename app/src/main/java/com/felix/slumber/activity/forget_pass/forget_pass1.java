package com.felix.slumber.activity.forget_pass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.felix.slumber.R;
import com.google.android.material.textfield.TextInputEditText;

public class forget_pass1 extends AppCompatActivity {

    private Button btn;
    private ImageView cross;
    private TextInputEditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_pass_layout1);
        btn = findViewById(R.id.forget_pass1);
        cross = findViewById(R.id.cross);
        email = findViewById(R.id.userInput);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // forget_pass1.this.startActivity(new Intent(forget_pass1.this, forget_pass3.class));
                Intent mIntent = new Intent(forget_pass1.this, forget_pass3.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("email", email.getText().toString());
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
                finish();
            }
        });

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}