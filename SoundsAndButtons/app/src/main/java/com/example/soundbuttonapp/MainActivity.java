package com.example.soundbuttonapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private String[] sound_effects = {"Dramatic", "Dreaming Harp", "Reveal"};
    private ArrayAdapter<String> arrayAdapter;
    private Button play_btn, pause_btn, stop_btn;
    private Spinner spinner;
    private String sound;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.purple_500));

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sound_effects);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        play_btn = findViewById(R.id.play_sound_btn);
        stop_btn = findViewById(R.id.stop_sound_btn);
        pause_btn = findViewById(R.id.pause_sound_btn);

        spinner = findViewById(R.id.spinner);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stopPlayer();
                sound = parent.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this, "Selected Sound: " + sound, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play(v);
            }
        });

        stop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop(v);
            }
        });

        pause_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause(v);
            }
        });
    }

    public void play(View view){

        if (mediaPlayer == null) {

            switch (sound) {
                case "Dramatic":
                    mediaPlayer = MediaPlayer.create(this, R.raw.dramatic_sound_effect);
                    break;
                case "Reveal":
                    mediaPlayer = MediaPlayer.create(this, R.raw.reveal_sound_effect);
                    break;
                case "Dreaming Harp":
                    mediaPlayer = MediaPlayer.create(this, R.raw.dreaming_harp_sound_effect);
                    break;
            }
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopPlayer();
            }
        });

        mediaPlayer.start();
    }

    public void pause(View view){
        if(mediaPlayer != null){
            mediaPlayer.pause();
        }
    }

    private void stopPlayer(){
        if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
            Toast.makeText(getApplicationContext(), "MediaPlayer Released", Toast.LENGTH_SHORT).show();
        }
    }

    public void stop(View view) {
        stopPlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
    }
}