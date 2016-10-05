package com.example.jmscha14.harcourtschappel2;

import android.media.MediaPlayer;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Handler h = null;
    TextView click = null;
    MediaPlayer mPlayer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        h = new Handler();
        this.click = (TextView) findViewById(R.id.number_of_clicks);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPlayer = MediaPlayer.create(this,R.raw.background);
        mPlayer.setVolume(.5f,.5f);
        mPlayer.setLooping(true);
        mPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPlayer.stop();
        mPlayer.reset();
        mPlayer.release();
    }

    public void countUpdate(final int number){
        h.post(new Runnable() {
            @Override
            public void run() {
                click.setText(Integer.toString(number));
            }
        });
    }


    public void startThinking() {
        View thinkView = findViewById(R.id.thinking);
        thinkView.setVisibility(View.VISIBLE);
    }
    public void stopThinking() {
        View thinkView = findViewById(R.id.thinking);
        thinkView.setVisibility(View.GONE);
    }




}
