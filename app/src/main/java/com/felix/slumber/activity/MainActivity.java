package com.felix.slumber.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.felix.slumber.R;
import com.felix.slumber.background.http_post;
import com.felix.slumber.dialog.dialog_trivia;
import com.felix.slumber.dialog.dialog_trivia_correct;
import com.felix.slumber.dialog.dialog_trivia_wrong;
import com.felix.slumber.drawer.real.profile;
import com.felix.slumber.drawer.real.setting;
import com.felix.slumber.drawer.real.store.store_draw;
import com.felix.slumber.drawer.real.support;
import com.felix.slumber.drawer.real.tutorial;
import com.felix.slumber.fragment.menu_achievement.achievement_1;
import com.felix.slumber.fragment.menu_article.article_1;
import com.felix.slumber.fragment.menu_clock.clock_1;
import com.felix.slumber.fragment.menu_music.music_1;
import com.felix.slumber.fragment.menu_report.report_2;
import com.felix.slumber.general.utility;
import com.felix.slumber.model_body.model_activity;
import com.felix.slumber.model_body.model_login;
import com.felix.slumber.model_body.model_profile;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements dialog_trivia.dialogListener_worker {

    private MaterialButton btn1;
    private MaterialButton btn2;
    private MaterialButton btn3;
    private MaterialButton btn4;
    private MaterialButton btn5;
    private NavigationView navigation;

    private ImageView imageView2;
    private ImageView drawer;
    private TextView text1;
    private TextView text2;
    private DrawerLayout drawerlayout;

    private AppBarConfiguration mAppBarConfiguration;
    private Fragment selectedFragment = null;

    private TextView mNameTextView1;
    private TextView mNameTextView2;
    private ImageView imageView;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        model_login model = new model_login(getApplicationContext());
        new utility().update_profile(model, getApplicationContext());
        setContentView(R.layout.activity_nav_drawer);

        trivia_popup(new model_profile(MainActivity.this));
        imageView2 = findViewById(R.id.imageView2);
        text1 = findViewById(R.id.yourUsername2);
        text2 = findViewById(R.id.ageHometown2);
        drawer = findViewById(R.id.drawer);
        navigation = findViewById(R.id.nav_view);
        drawerlayout = findViewById(R.id.drawer_layout);

        mAppBarConfiguration = new AppBarConfiguration.Builder()
                .setDrawerLayout(drawerlayout)
                .build();

        drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerlayout.openDrawer(GravityCompat.START); //Edit Gravity.START need API 14
            }
        });
        drawer_process();


        sp = getSharedPreferences("State", MODE_PRIVATE);
        String gender = sp.getString("gender", "");
        if (gender.equalsIgnoreCase("MALE")) {
            imageView2.setImageResource(R.mipmap.ic_male_foreground);

        }
        if (gender.equalsIgnoreCase("FEMALE")) {
            imageView2.setImageResource(R.mipmap.ic_female_foreground);
        }

        // set dari preferences
        text1.setText(sp.getString("name", ""));
        text2.setText(calculateAge(sp.getString("dob", "")) + " Years, " + sp.getString("address", ""));
        // set dari preferences

        ImageSlider imageSlider = findViewById(R.id.slider);

        List<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.a1, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.a2, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.a3, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.a4, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.a5, ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModels, ScaleTypes.CENTER_INSIDE);

        // initial
        btn1 = (MaterialButton) findViewById(R.id.clock);
        btn2 = (MaterialButton) findViewById(R.id.music);
        btn3 = (MaterialButton) findViewById(R.id.report);
        btn4 = (MaterialButton) findViewById(R.id.article);
        btn5 = (MaterialButton) findViewById(R.id.achievement);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new clock_1()).commit();
        btn1.setIconTint(ColorStateList.valueOf(Color.parseColor("#0c072c")));
        btn1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#fafbbd")));
        // initial

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = new clock_1();
                frag_tran(selectedFragment);
                btn1.setIconTint(ColorStateList.valueOf(Color.parseColor("#0c072c")));
                btn1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#fafbbd")));

                // cancel
                btn2.setIconTint(ColorStateList.valueOf(Color.parseColor("#fafbbd")));
                btn2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0c072c")));
                btn3.setIconTint(ColorStateList.valueOf(Color.parseColor("#fafbbd")));
                btn3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0c072c")));
                btn4.setIconTint(ColorStateList.valueOf(Color.parseColor("#fafbbd")));
                btn4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0c072c")));
                btn5.setIconTint(ColorStateList.valueOf(Color.parseColor("#fafbbd")));
                btn5.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0c072c")));
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = new music_1();
                frag_tran(selectedFragment);
                btn2.setIconTint(ColorStateList.valueOf(Color.parseColor("#0c072c")));
                btn2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#fafbbd")));


                // cancel
                btn1.setIconTint(ColorStateList.valueOf(Color.parseColor("#fafbbd")));
                btn1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0c072c")));
                btn3.setIconTint(ColorStateList.valueOf(Color.parseColor("#fafbbd")));
                btn3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0c072c")));
                btn4.setIconTint(ColorStateList.valueOf(Color.parseColor("#fafbbd")));
                btn4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0c072c")));
                btn5.setIconTint(ColorStateList.valueOf(Color.parseColor("#fafbbd")));
                btn5.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0c072c")));

            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = new report_2();
                // selectedFragment = new report_1();
                frag_tran(selectedFragment);
                btn3.setIconTint(ColorStateList.valueOf(Color.parseColor("#0c072c")));
                btn3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#fafbbd")));

                // cancel
                btn1.setIconTint(ColorStateList.valueOf(Color.parseColor("#fafbbd")));
                btn1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0c072c")));
                btn2.setIconTint(ColorStateList.valueOf(Color.parseColor("#fafbbd")));
                btn2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0c072c")));
                btn4.setIconTint(ColorStateList.valueOf(Color.parseColor("#fafbbd")));
                btn4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0c072c")));
                btn5.setIconTint(ColorStateList.valueOf(Color.parseColor("#fafbbd")));
                btn5.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0c072c")));

            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = new article_1();
                frag_tran(selectedFragment);
                btn4.setIconTint(ColorStateList.valueOf(Color.parseColor("#0c072c")));
                btn4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#fafbbd")));


                // cancel
                btn1.setIconTint(ColorStateList.valueOf(Color.parseColor("#fafbbd")));
                btn1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0c072c")));
                btn2.setIconTint(ColorStateList.valueOf(Color.parseColor("#fafbbd")));
                btn2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0c072c")));
                btn3.setIconTint(ColorStateList.valueOf(Color.parseColor("#fafbbd")));
                btn3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0c072c")));
                btn5.setIconTint(ColorStateList.valueOf(Color.parseColor("#fafbbd")));
                btn5.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0c072c")));
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = new achievement_1();
                frag_tran(selectedFragment);
                btn5.setIconTint(ColorStateList.valueOf(Color.parseColor("#0c072c")));
                btn5.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#fafbbd")));


                // cancel
                btn1.setIconTint(ColorStateList.valueOf(Color.parseColor("#fafbbd")));
                btn1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0c072c")));
                btn2.setIconTint(ColorStateList.valueOf(Color.parseColor("#fafbbd")));
                btn2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0c072c")));
                btn4.setIconTint(ColorStateList.valueOf(Color.parseColor("#fafbbd")));
                btn4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0c072c")));
                btn3.setIconTint(ColorStateList.valueOf(Color.parseColor("#fafbbd")));
                btn3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0c072c")));
            }
        });


    }

    private void frag_tran(Fragment selectedFragment) {
        if (selectedFragment != null) {
            // getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out).replace(R.id.main_fragment, selectedFragment).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, selectedFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

    /*
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    */

    public Integer calculateAge(String dt) {
        LocalDate birthdate = new DateTime(dt).toLocalDate();
        LocalDate now = new LocalDate();
        Years age = Years.yearsBetween(birthdate, now);
        return age.getYears();
    }

    private void drawer_process() {
        View header = navigation.getHeaderView(0);
        mNameTextView1 = header.findViewById(R.id.yourUsername);
        mNameTextView2 = header.findViewById(R.id.ageHometown);
        imageView = header.findViewById(R.id.imageView);

        sp = getSharedPreferences("State", MODE_PRIVATE);
        String gender = sp.getString("gender", "");
        if (gender.equalsIgnoreCase("MALE")) {
            imageView.setImageResource(R.mipmap.ic_male_foreground);

        }
        if (gender.equalsIgnoreCase("FEMALE")) {
            imageView.setImageResource(R.mipmap.ic_female_foreground);
        }

        // set dari preferences
        mNameTextView1.setText(sp.getString("name", ""));
        mNameTextView2.setText(calculateAge(sp.getString("dob", "")) + " Years, " + sp.getString("address", ""));
        // set dari preferences

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.nav_profile:
                        //Do some thing here

                        MainActivity.this.startActivity(new Intent(MainActivity.this, profile.class));
                        // add navigation drawer item onclick method here
                        break;
                    case R.id.nav_store:
                        MainActivity.this.startActivity(new Intent(MainActivity.this, store_draw.class));
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        break;

                    case R.id.nav_setting:

                        MainActivity.this.startActivity(new Intent(MainActivity.this, setting.class));
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        break;


                    case R.id.nav_tutorial:

                        MainActivity.this.startActivity(new Intent(MainActivity.this, tutorial.class));
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        break;

                    case R.id.nav_support:

                        MainActivity.this.startActivity(new Intent(MainActivity.this, support.class));
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        break;

                    case R.id.logout:

                        model_login model = new model_login(MainActivity.this);
                        logout(model);
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        break;
                }
                return false;
            }
        });

    }

    private void logout(model_login model) {
        try {

            http_post post = new http_post(MainActivity.this);
            Gson gson = new Gson();
            JSONObject jo = new JSONObject(gson.toJson(model));
            jo.put("api", getString(R.string.logout_api));
            post.execute(jo);
            post.getListener(new http_post.OnUpdateListener() {
                @Override
                public void onUpdate(JSONObject JObject) {

                    try {

                        if (!JObject.getString("statuscode").equals("OK")) {
                            Toast.makeText(MainActivity.this, "Gagal : " + JObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } else if (JObject.getString("statuscode").equals("no connection")) {
                            Toast.makeText(MainActivity.this, "Tidak ada koneksi ke server", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                            logout_success();
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

    private void logout_success() {
        try {
            SharedPreferences sh = getSharedPreferences("State", MODE_PRIVATE);
            SharedPreferences.Editor editor = sh.edit();
            editor.putBoolean("Login_State", false);
            editor.commit();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void trivia_popup(model_profile model) {
        try {

            http_post post = new http_post(MainActivity.this);
            Gson gson = new Gson();
            JSONObject jo = new JSONObject(gson.toJson(model));
            jo.put("api", getString(R.string.get_daily_api));
            post.execute(jo);
            post.getListener(new http_post.OnUpdateListener() {
                @Override
                public void onUpdate(JSONObject JObject) {

                    try {

                        if (!JObject.getString("statuscode").equals("OK")) {
                            Toast.makeText(MainActivity.this, "Gagal : " + JObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } else if (JObject.getString("statuscode").equals("no connection")) {
                            Toast.makeText(MainActivity.this, "Tidak ada koneksi ke server", Toast.LENGTH_SHORT).show();
                        } else {
                            // Toast.makeText(MainActivity.this, "Sukses", Toast.LENGTH_SHORT).show();

                            JSONObject data = JObject.getJSONArray("data").getJSONObject(0);
                            if (!data.isNull("trivia")) {

                                String idtrivia = data.getJSONObject("trivia").getString("id");
                                String idtrivia_detail = data.getJSONObject("trivia_detail").getString("id");
                                String question = data.getJSONObject("trivia_detail").getString("deskripsi");
                                Integer answer = data.getJSONObject("trivia_detail").getInt("answer");
                                opendialog_(idtrivia, idtrivia_detail, question, answer);
                                // MainActivity.this.startActivity(mIntent)
                            }
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

    private void opendialog_(String idtrivia, String idtrivia_detail, String question, Integer answer) {
        dialog_trivia dialogfragment = new dialog_trivia(idtrivia, idtrivia_detail, question, answer);
        dialogfragment.show(getSupportFragmentManager(), "exa");
    }


    @Override
    public void apply_listener(Boolean result, String idtrivia, Integer answer) {
        model_activity model = new model_activity(new model_profile(MainActivity.this), "TRIVIA", "", "", idtrivia, answer);
        finish_task(MainActivity.this, model);
        if (result) {
            dialog_trivia_correct dialogfragment = new dialog_trivia_correct();
            dialogfragment.show(getSupportFragmentManager(), "exa1");
        } else {
            dialog_trivia_wrong dialogfragment = new dialog_trivia_wrong();
            dialogfragment.show(getSupportFragmentManager(), "exa2");
        }
    }

    private void finish_task(final Context context, model_activity model) {
        try {


            http_post post = new http_post(context);
            Gson gson = new Gson();
            JSONObject jo = new JSONObject(gson.toJson(model));
            jo.put("api", getString(R.string.finish_task));
            post.execute(jo);
            post.getListener(new http_post.OnUpdateListener() {
                @Override
                public void onUpdate(JSONObject JObject) {

                    try {

                        model_login model = new model_login(getApplicationContext());
                        new utility().update_profile(model, getApplicationContext());

                        if (!JObject.getString("statuscode").equals("OK")) {
                            Toast.makeText(context, "Gagal : " + JObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } else if (JObject.getString("statuscode").equals("no connection")) {
                            Toast.makeText(context, "Tidak ada koneksi ke server", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
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


}