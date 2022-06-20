package com.felix.slumber.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.felix.slumber.R;
import com.felix.slumber.background.http_post;
import com.felix.slumber.dialog.dialog_address;
import com.felix.slumber.dialog.dialog_date;
import com.felix.slumber.dialog.dialog_gender;
import com.felix.slumber.model_body.model_register;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SignUp extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, dialog_gender.dialogListener_worker, dialog_address.dialogListener_worker {

    private Button btn;
    private ImageView cross;
    private EditText email;
    private EditText password;
    private EditText name;
    private EditText dob;
    private EditText address;
    private EditText pass_confirm;
    private EditText reg_gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_layout);
        btn = findViewById(R.id.register);
        email = findViewById(R.id.reg_email);
        password = findViewById(R.id.reg_pass);
        name = findViewById(R.id.reg_name);
        dob = findViewById(R.id.reg_dob);
        address = findViewById(R.id.reg_add);
        pass_confirm = findViewById(R.id.reg_pass_confirm);
        reg_gender = findViewById(R.id.reg_gender);
        cross = findViewById(R.id.cross);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn.setEnabled(false);
                model_register model = new model_register(email.getText().toString(), password.getText().toString(), name.getText().toString(), dob.getText().toString(), address.getText().toString(), reg_gender.getText().toString());
                if (password.getText().toString().equals(pass_confirm.getText().toString())) {
                    sign_up(model);
                }

            }
        });

        dob.setOnClickListener(new View.OnClickListener() {
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

        address.setOnClickListener(new View.OnClickListener() {
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
    }

    private void sign_up(model_register model) {
        try {

            http_post post = new http_post(SignUp.this);
            Gson gson = new Gson();
            JSONObject jo = new JSONObject(gson.toJson(model));
            jo.put("api", getString(R.string.register_api));
            post.execute(jo);
            post.getListener(new http_post.OnUpdateListener() {
                @Override
                public void onUpdate(JSONObject JObject) {

                    try {

                        if (!JObject.getString("statuscode").equals("OK")) {
                            Toast.makeText(SignUp.this, "Gagal : " + JObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } else if (JObject.getString("statuscode").equals("no connection")) {
                            Toast.makeText(SignUp.this, "Tidak ada koneksi ke server", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignUp.this, "Success", Toast.LENGTH_SHORT).show();
                            finish();
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
        dob.setText(current_date);
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
        address.setText(add);
    }
}