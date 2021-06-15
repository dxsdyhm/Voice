package com.hcy.voice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.VolumeUtils;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView txVolume;
    private MediaPlayer mPlayer;
    public static final String AUDIO_FILE = "test_music.mp3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txVolume=findViewById(R.id.tx_volume);
        findViewById(R.id.btn_del).setOnClickListener(this);
        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_bgm).setOnClickListener(this);
        initVolue();
    }

    @Override
    protected void onResume() {
        super.onResume();
        playTestMusic();
    }

    private void initVolue(){
        int volue= VolumeUtils.getVolume(AudioManager.STREAM_MUSIC);
        txVolume.setText(getString(R.string.current_volume)+volue);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_del:
                int volue= VolumeUtils.getVolume(AudioManager.STREAM_MUSIC);
                VolumeUtils.setVolume(AudioManager.STREAM_MUSIC,volue-1,AudioManager.FLAG_SHOW_UI);
                initVolue();
                break;
            case R.id.btn_add:
                int voluecul= VolumeUtils.getVolume(AudioManager.STREAM_MUSIC);
                VolumeUtils.setVolume(AudioManager.STREAM_MUSIC,voluecul+1,AudioManager.FLAG_SHOW_UI);
                initVolue();
                break;
            case R.id.btn_bgm:
                playTestMusic();
                break;
        }
    }

    private void playTestMusic(){
        if(mPlayer!=null&&mPlayer.isPlaying()){
            mPlayer.pause();
        }else {
            initMusic();
        }
    }

    private void initMusic(){
        mPlayer = new MediaPlayer();
        try {
            AssetFileDescriptor fd = getAssets().openFd(AUDIO_FILE);
            mPlayer.setDataSource(fd.getFileDescriptor());
            mPlayer.prepare();
            mPlayer.setLooping(true);
            mPlayer.start();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mPlayer!=null&&mPlayer.isPlaying()){
            mPlayer.pause();
            mPlayer.release();
            mPlayer=null;
        }
    }
}