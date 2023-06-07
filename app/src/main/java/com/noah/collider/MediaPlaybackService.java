package com.noah.collider;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.io.IOException;

/**
 * Media playback service in charge of playing music
 */
public class MediaPlaybackService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {
    private MediaPlayer mPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Uri uri = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(getApplicationContext().getPackageName())
                .path(String.valueOf(R.raw.lightyear_legends))
                .build();

        mPlayer = new MediaPlayer();
        mPlayer.setLooping(true);
        mPlayer.setOnErrorListener(this);
        mPlayer.setScreenOnWhilePlaying(true);
        mPlayer.setOnPreparedListener(this);

        try {
            mPlayer.setDataSource(getApplicationContext(), uri);
        } catch (IOException ignored) {
            // ignored for now
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mPlayer.setAudioAttributes(new AudioAttributes.Builder()
                                               .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                               .setUsage(AudioAttributes.USAGE_MEDIA)
                                               .build());
        } else {
            // backward compatibility to play audio on music stream
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }
        mPlayer.prepareAsync();

        return START_STICKY;

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mp.reset();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
}
