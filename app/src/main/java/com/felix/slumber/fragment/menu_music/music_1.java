package com.felix.slumber.fragment.menu_music;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.AnyRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.felix.slumber.R;
import com.felix.slumber.background.http_post;
import com.felix.slumber.model.model_uri_title_source;
import com.felix.slumber.model_body.model_activity;
import com.felix.slumber.model_body.model_profile;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by Adrian on 5/18/2018.
 */

public class music_1 extends Fragment {
    private static MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private Runnable runnable;

    private ImageButton btn_play;
    private ImageButton btn_next;
    private ImageButton btn_previous;
    private ImageButton btn_shuffle;
    private ImageButton btn_repeat;
    private TextView title;

    private Boolean isPause = false;
    private Boolean isPlaying = false;
    private Boolean isShuffle = false;
    private Integer isRepeat = 0;
    private List<model_uri_title_source> data_uri_title = new ArrayList<>();
    private Integer currentPosition = 0;
    private Uri currentSong;

    private SharedPreferences sh;
    private SharedPreferences.Editor editor;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.menu_fragment_music_1, container, false);
        mediaPlayer = new MediaPlayer();

        sh = getContext().getSharedPreferences("State", MODE_PRIVATE);
        editor = sh.edit();

        /*
        isPause = sh.getBoolean("isPause", false);
        isPlaying = sh.getBoolean("isPlaying", false);
        isShuffle = sh.getBoolean("isShuffle", false);
        isRepeat = sh.getInt("isRepeat", 0);
         */

        data_uri_title.add(new model_uri_title_source(getUri(R.raw.campfire), "Campfire"));
        data_uri_title.add(new model_uri_title_source(getUri(R.raw.crickets), "Crickets"));
        data_uri_title.add(new model_uri_title_source(getUri(R.raw.forest), "Forest"));
        data_uri_title.add(new model_uri_title_source(getUri(R.raw.monoman), "Monoman"));
        data_uri_title.add(new model_uri_title_source(getUri(R.raw.nature), "Nature"));
        data_uri_title.add(new model_uri_title_source(getUri(R.raw.rain), "Rain"));
        data_uri_title.add(new model_uri_title_source(getUri(R.raw.relief), "Relief"));
        data_uri_title.add(new model_uri_title_source(getUri(R.raw.water), "Water"));

        btn_play = view.findViewById(R.id.play);
        btn_next = view.findViewById(R.id.next);
        btn_previous = view.findViewById(R.id.previous);
        btn_shuffle = view.findViewById(R.id.shuffle);
        btn_repeat = view.findViewById(R.id.repeat);
        title = view.findViewById(R.id.title);
        title.setText(data_uri_title.get(1).getTitle());

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPlaying) {
                    if (isPause) {
                        mediaPlayer.start();
                        isPause = !isPause;
                        editor.putBoolean("isPause", isPause);
                        isPlaying = true;
                        editor.putBoolean("isPLaying", isPlaying);
                        editor.commit();
                        btn_play.setImageResource(R.drawable.ic_pause);
                    } else {
                        nextSong();
                        btn_play.setImageResource(R.drawable.ic_pause);
                    }
                    /*
                    WindowManager.LayoutParams WMLP = getActivity().getWindow().getAttributes();
                    WMLP.dimAmount=1.0f;
                    getActivity().getWindow().setAttributes(WMLP);
                     */
                } else {
                    isPlaying = !isPlaying;
                    editor.putBoolean("isPLaying", isPlaying);
                    isPause = true;
                    editor.putBoolean("isPause", isPause);
                    mediaPlayer.pause();
                    editor.commit();
                    btn_play.setImageResource(R.drawable.ic_music_play);
                }
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = new MediaPlayer();
                nextSong();
                isPlaying = true;
                editor.putBoolean("isPLaying", isPlaying);
                editor.commit();
            }
        });

        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = new MediaPlayer();
                previousSong();
                isPlaying = true;
                editor.putBoolean("isPLaying", isPlaying);
                editor.commit();
            }
        });

        btn_shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShuffle = !isShuffle;
                editor.putBoolean("isShuffle", isShuffle);
                editor.commit();
            }
        });

        btn_repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRepeat == 0) {
                    isRepeat = 1;

                } else if (isRepeat == 1) {
                    isRepeat = 2;
                } else if (isRepeat == 2) {
                    isRepeat = 0;
                }
                editor.putInt("isRepeat", isRepeat);
                editor.commit();
            }
        });


        return view;
    }


    public void nextSong() {
        int numOfSong = data_uri_title.size();

        if (!isShuffle) { // Shuffle mode is off
            if (currentPosition < numOfSong - 1) {
                currentPosition++;
                currentSong = data_uri_title.get(currentPosition).getUri();
                Log.d("my_log", "position = " + currentPosition);
                title.setText(data_uri_title.get(currentPosition).getTitle());
                playBackMusic();
            } else {
                currentPosition = 0;
                currentSong = data_uri_title.get(currentPosition).getUri();
                Log.d("my_log", "position = " + currentPosition);
                title.setText(data_uri_title.get(currentPosition).getTitle());
                playBackMusic();
            }
        } else { // Shuffle mode is on
            Random rand = new Random();
            currentPosition = rand.nextInt(numOfSong);
            currentSong = data_uri_title.get(currentPosition).getUri();
            Log.d("my_log", "position = " + currentPosition);
            title.setText(data_uri_title.get(currentPosition).getTitle());
            playBackMusic();
        }
    }

    public void previousSong() {
        int numOfSong = data_uri_title.size();

        if (currentPosition == 0) {
            currentPosition = data_uri_title.size() - 1;
        } else {
            currentPosition--;
        }
        currentSong = data_uri_title.get(currentPosition).getUri();
        Log.d("my_log", "position = " + currentPosition);
        title.setText(data_uri_title.get(currentPosition).getTitle());
        playBackMusic();
    }

    public void playBackMusic() {
        try {
            mediaPlayer.release();
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(getContext(), currentSong);
            mediaPlayer.prepare();

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    endOfTheSong();
                }
            });

            isPlaying = true;
            editor.putBoolean("isPlaying", isPlaying);
            editor.commit();
            mediaPlayer.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void endOfTheSong() {
        model_activity model = new model_activity(new model_profile(getContext()), "MUSIC", "", "", "");
        finish_task(getContext(), model);
        if (isRepeat == 1) { // currently repeat one song
            mediaPlayer.seekTo(0);
            mediaPlayer.start();
        } else if (isRepeat == 2) { // currently repeat all songs
            nextSong();
        } else { // currently no repeat
            if (currentPosition != data_uri_title.size() - 1) nextSong();
        }
    }

    public Uri getUri(@AnyRes int drawableId) {
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + getContext().getResources().getResourcePackageName(drawableId)
                + '/' + getContext().getResources().getResourceTypeName(drawableId)
                + '/' + getContext().getResources().getResourceEntryName(drawableId));

        return imageUri;
    }

    @Override
    public void onPause() {
        isPlaying = !isPlaying;
        editor.putBoolean("isPLaying", isPlaying);
        isPause = true;
        editor.putBoolean("isPause", isPause);
        mediaPlayer.pause();
        editor.commit();
        btn_play.setImageResource(R.drawable.ic_music_play);
        super.onPause();
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



}