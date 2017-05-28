package com.example.danielnikulin.flappy;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setTitle("Flappy");
        DisplayMetrics dispMetr = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dispMetr);
        Constants.SCREEN_WIDHT = dispMetr.widthPixels;
        Constants.SCREEN_HEIGHT = dispMetr.heightPixels;
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        setContentView(new Panel (this));
    }


}