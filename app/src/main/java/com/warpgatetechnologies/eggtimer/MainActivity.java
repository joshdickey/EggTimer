package com.warpgatetechnologies.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private SeekBar mSeekBar;
    private TextView mTextViewTimer;
    private String mProgressInTime;
    private Button mButtonStart;
    private CountDownTimer mCountDownTimer;
    boolean mStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSeekBar = (SeekBar)findViewById(R.id.seekBarStartTime);
        mTextViewTimer = (TextView)findViewById(R.id.textviewTimer);
        mButtonStart = (Button)findViewById(R.id.buttonStart);

        mStarted = true;

        mSeekBar.setMax(600);
        mSeekBar.setProgress(30);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                if (mCountDownTimer != null){
                    mCountDownTimer.cancel();
                    mStarted = true;
                    mButtonStart.setText("Start");
                }

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        mButtonStart.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                if (mStarted){
                    mStarted = false;
                    mButtonStart.setText("Stop");

                    mCountDownTimer = new CountDownTimer(mSeekBar.getProgress() * 1000 + 100, 1000) {

                        public void onTick(long millisecondsTillDone) {
                            //counting down.
                            int progress = (int) millisecondsTillDone / 1000;
                            mSeekBar.setProgress(progress);
                            updateTimer(progress);

                        }

                        public void onFinish() {
                            //Counter is finished.
                            mTextViewTimer.setText("0:00");
                            mButtonStart.setText("Start");

                            MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.horn);
                            mediaPlayer.start();
                        }
                    }.start();
                }else {
                    mStarted = true;
                    mButtonStart.setText("Start");
                    mCountDownTimer.cancel();
                }
            }
        });
    }

    private void updateTimer(int secondsLeft) {
        int minutes;
        int seconds;

        minutes = secondsLeft / 60;
        seconds = secondsLeft - minutes * 60;

        if(seconds <= 9){
            mProgressInTime = minutes + ":0" + seconds;
        }else{
            mProgressInTime = minutes + ":" + seconds;
        }

        mTextViewTimer.setText(mProgressInTime);
    }
}
