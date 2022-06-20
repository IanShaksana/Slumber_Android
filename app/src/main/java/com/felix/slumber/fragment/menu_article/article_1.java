package com.felix.slumber.fragment.menu_article;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.AnyRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.felix.slumber.R;
import com.felix.slumber.background.http_post;
import com.felix.slumber.dialog.dialog_article;
import com.felix.slumber.model.model_article_source;
import com.felix.slumber.model_body.model_activity;
import com.felix.slumber.model_body.model_profile;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Adrian on 5/18/2018.
 */

public class article_1 extends Fragment implements dialog_article.dialogListener_worker {

    private Button read;

    private Button left;
    private Button right;

    private Integer index = 0;

    private TextView tx;
    private TextView title;
    private TextView kalimat_pertama;

    private List<model_article_source> models = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.menu_fragment_article_1, container, false);
        populate();

        title = view.findViewById(R.id.title);
        read = view.findViewById(R.id.read);
        left = view.findViewById(R.id.left);
        right = view.findViewById(R.id.right);
        kalimat_pertama = view.findViewById(R.id.kalimatpertama);

        // initate
        title.setText(getString(models.get(index).getResource_title()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            kalimat_pertama.setText(Html.fromHtml(getString(models.get(index).getResource_content()), Html.FROM_HTML_MODE_LEGACY));
        } else {
            kalimat_pertama.setText(Html.fromHtml(getString(models.get(index).getResource_content())));
        }

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // onButtonShowPopupWindowClick(v);
                model_article_source model = models.get(index);
                opendialog_invite(model);
            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index == 0) {
                    index = 12;
                } else {
                    index--;
                }
                title.setText(getString(models.get(index).getResource_title()));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    kalimat_pertama.setText(Html.fromHtml(getString(models.get(index).getResource_content()), Html.FROM_HTML_MODE_LEGACY));
                } else {
                    kalimat_pertama.setText(Html.fromHtml(getString(models.get(index).getResource_content())));
                }
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index == 12) {
                    index = 0;
                } else {
                    index++;
                }
                title.setText(getString(models.get(index).getResource_title()));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    kalimat_pertama.setText(Html.fromHtml(getString(models.get(index).getResource_content()), Html.FROM_HTML_MODE_LEGACY));
                } else {
                    kalimat_pertama.setText(Html.fromHtml(getString(models.get(index).getResource_content())));
                }
            }
        });
        return view;
    }

    private void populate() {
        models.add(new model_article_source(R.string.title_1, R.string.article_1, getUri(R.drawable.p12)));
        models.add(new model_article_source(R.string.title_2, R.string.article_2, getUri(R.drawable.p15)));
        models.add(new model_article_source(R.string.title_3, R.string.article_3, getUri(R.drawable.p14)));
        models.add(new model_article_source(R.string.title_4, R.string.article_4, getUri(R.drawable.p6)));
        models.add(new model_article_source(R.string.title_5, R.string.article_5, getUri(R.drawable.p10)));
        models.add(new model_article_source(R.string.title_6, R.string.article_6, getUri(R.drawable.p13)));
        models.add(new model_article_source(R.string.title_7, R.string.article_7, getUri(R.drawable.p11)));
        models.add(new model_article_source(R.string.title_8, R.string.article_8, getUri(R.drawable.p9)));
        models.add(new model_article_source(R.string.title_9, R.string.article_9, getUri(R.drawable.p8)));
        models.add(new model_article_source(R.string.title_10, R.string.article_10, getUri(R.drawable.p17)));
        models.add(new model_article_source(R.string.title_11, R.string.article_11, getUri(R.drawable.p18)));
        models.add(new model_article_source(R.string.title_12, R.string.article_12, getUri(R.drawable.p7)));
        models.add(new model_article_source(R.string.title_13, R.string.article_13, getUri(R.drawable.p16)));

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

                        if (!JObject.getString("statuscode").equals("OK")) {
                            Toast.makeText(context, "Gagal : " + JObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } else if (JObject.getString("statuscode").equals("no connection")) {
                            Toast.makeText(context, "Tidak ada koneksi ke server", Toast.LENGTH_SHORT).show();
                        } else {
                            // Toast.makeText(context, "Sukses", Toast.LENGTH_SHORT).show();
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

    private void opendialog_invite(model_article_source model) {
        dialog_article dialogfragment = new dialog_article(model);
        dialogfragment.setTargetFragment(this, 0);
        dialogfragment.show(getParentFragmentManager(), "exa");
    }


    @Override
    public void apply_listener(model_article_source model) {
        model_activity modelss = new model_activity(new model_profile(getContext()), "ARTICLE", getString(models.get(index).getResource_title()), "", "");
        finish_task(getContext(), modelss);
    }

    public Uri getUri(@AnyRes int drawableId) {
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + getContext().getResources().getResourcePackageName(drawableId)
                + '/' + getContext().getResources().getResourceTypeName(drawableId)
                + '/' + getContext().getResources().getResourceEntryName(drawableId));

        return imageUri;
    }
}