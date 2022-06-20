package com.felix.slumber.drawer.real;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.felix.slumber.R;
import com.felix.slumber.activity.MainActivity;
import com.felix.slumber.background.http_post;
import com.felix.slumber.dialog.dialog_address;
import com.felix.slumber.dialog.dialog_date;
import com.felix.slumber.dialog.dialog_gender;
import com.felix.slumber.general.utility;
import com.felix.slumber.model_body.model_login;
import com.felix.slumber.model_body.model_profile;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class profile extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, dialog_gender.dialogListener_worker, dialog_address.dialogListener_worker {

    private Button btn;

    private ImageView cross;
    private ImageView edit;
    private TextView change_pass;

    private boolean edit_mode = false;
    private TextInputEditText reg_email;
    private TextInputEditText reg_name;
    private TextInputEditText reg_dob;
    private TextInputEditText reg_add;
    private TextInputEditText reg_gender;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_profile);
        btn = findViewById(R.id.done);
        reg_email = findViewById(R.id.reg_email);
        reg_name = findViewById(R.id.reg_name);
        reg_dob = findViewById(R.id.reg_dob);
        reg_add = findViewById(R.id.reg_add);
        reg_gender = findViewById(R.id.reg_gender);
        cross = findViewById(R.id.cross);
        change_pass = findViewById(R.id.change_pass);
        edit = findViewById(R.id.edit);

        preferences = getSharedPreferences("State", MODE_PRIVATE);
        model_login model = new model_login(getApplicationContext());
        new utility().update_profile(model, getApplicationContext());

        reg_email.setText(preferences.getString("email", ""));
        reg_name.setText(preferences.getString("name", ""));
        reg_dob.setText(preferences.getString("dob", ""));
        reg_add.setText(preferences.getString("address", ""));
        reg_gender.setText(preferences.getString("gender", ""));


        reg_email.setEnabled(false);
        reg_name.setEnabled(false);
        reg_dob.setEnabled(false);
        reg_add.setEnabled(false);
        reg_gender.setEnabled(false);
        btn.setVisibility(View.INVISIBLE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = reg_email.getText().toString();
                String password = "";
                String name = reg_name.getText().toString();
                String dob = reg_dob.getText().toString();
                String address = reg_add.getText().toString();
                String gender = reg_gender.getText().toString();
                // model_profile model = new model_profile(preferences.getString("id", ""), email, password, name, dob, address, gender);
                model_profile model = new model_profile(preferences.getString("id", ""), email, password, name, dob, address, gender);

                edit(model);

                finish();
            }
        });

        reg_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendialog_date();
            }
        });

        reg_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendialog_gender();
            }
        });

        reg_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendialog_address();
            }
        });

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_mode == false){
                    edit_mode = true;
                    reg_email.setEnabled(true);
                    reg_name.setEnabled(true);
                    reg_dob.setEnabled(true);
                    reg_add.setEnabled(true);
                    reg_gender.setEnabled(true);
                    btn.setVisibility(View.VISIBLE);
                }else{
                    edit_mode = false;
                    reg_email.setEnabled(false);
                    reg_name.setEnabled(false);
                    reg_dob.setEnabled(false);
                    reg_add.setEnabled(false);
                    reg_gender.setEnabled(false);
                    btn.setVisibility(View.INVISIBLE);
                }
            }
        });

        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                profile.this.startActivity(new Intent(profile.this, profile2.class));
            }
        });


    }


    private void edit(model_profile model) {
        try {

            http_post post = new http_post(profile.this);
            Gson gson = new Gson();
            JSONObject jo = new JSONObject(gson.toJson(model));
            jo.put("api", getString(R.string.edit_profile_api));
            post.execute(jo);
            post.getListener(new http_post.OnUpdateListener() {
                @Override
                public void onUpdate(JSONObject JObject) {

                    try {

                        if (!JObject.getString("statuscode").equals("OK")) {
                            Toast.makeText(profile.this, "Gagal : " + JObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } else if (JObject.getString("statuscode").equals("no connection")) {
                            Toast.makeText(profile.this, "Tidak ada koneksi ke server", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(profile.this, "Success", Toast.LENGTH_SHORT).show();
                            edit_success(JObject);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void edit_success(JSONObject JObject) {
        try {

            model_login model = new model_login(getApplicationContext());
            new utility().update_profile(model, getApplicationContext());
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void opendialog_date() {
        dialog_date dialogfragment = new dialog_date();
        dialogfragment.show(getSupportFragmentManager(), "date");
    }

    private void opendialog_gender() {
        dialog_gender dialogfragment = new dialog_gender();
        dialogfragment.show(getSupportFragmentManager(), "gender");
    }

    private void opendialog_address() {
        dialog_address dialogfragment = new dialog_address();
        dialogfragment.show(getSupportFragmentManager(), "address");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat format_1 = new SimpleDateFormat("yyyy-MM-dd");
        String current_date = format_1.format(c.getTime());
        reg_dob.setText(current_date);
    }


    @Override
    public void apply_listener(String gender) {
        if (gender.equalsIgnoreCase("female")) {
            reg_gender.setText("Female");
        }

        if (gender.equalsIgnoreCase("male")) {
            reg_gender.setText("Male");
        }
    }

    @Override
    public void apply_address(String add) {
        reg_add.setText(add);
    }
}