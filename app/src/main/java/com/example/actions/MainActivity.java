package com.example.actions;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupButton(R.id.coffinShort, R.raw.coffin_short);
        setupButton(R.id.coffinLong, R.raw.coffin_long);

        setupButton(R.id.sadTrombone, R.raw.sad_trombone);
        setupButton(R.id.badumts, R.raw.badumts);

        setupButton(R.id.triumph, R.raw.triumph);
        setupButton(R.id.shit, R.raw.shit);

        setupButton(R.id.nelson, R.raw.nelson);
        setupButton(R.id.crickets, R.raw.crikets);

        setupButton(R.id.usa, R.raw.usa);
        setupButton(R.id.airhorn, R.raw.airhorn);

        setupButton(R.id.dramatic, R.raw.dramatic);
        setupButton(R.id.applause, R.raw.applause);
    }

    private void setupButton(int buttonId, int soundId)
    {
        findViewById(buttonId).setOnClickListener(v -> playSound(soundId));
    }

    private void playSound(int soundId)
    {
        AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);

        if (audioManager != null)
        {
            audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
        }

        if (mediaPlayer != null)
        {
            mediaPlayer.stop();
        }

        mediaPlayer = MediaPlayer.create(this, soundId);
        mediaPlayer.start();
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        if (mediaPlayer != null)
        {
            mediaPlayer.stop();
        }
    }
}