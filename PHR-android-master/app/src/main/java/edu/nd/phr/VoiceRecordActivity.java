package edu.nd.phr;

import android.app.AlertDialog;
import android.content.Entity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.os.Environment;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import android.util.Log;
import android.media.MediaRecorder;
import android.media.MediaPlayer;
import android.os.CountDownTimer;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.nd.phr.MultiPartUtility;

import java.io.File;
import java.util.List;



public class VoiceRecordActivity extends ActionBarActivity{

    private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName = null;;

    private MediaRecorder mRecorder = null;

    private MediaPlayer   mPlayer = null;

    private Button startButton;
    private Button recordAgainButton;
    private Button uploadButton;
    private TextView wordBox;
    private TextView caseNumberText;
    private RecordingCountDownTimer recordTimer;
    private PlayCountDownTimer playTimer;

    private String[] testCase0 = {"Cheese", "Pepperoni", "Black Olives", "Sausage", "Sauce", "Ham"};
    private String[] testCase1 = {"Apple", "Orange", "Banana", "Grape"};
    private String[] testCase2 = {"Shirt", "Pants", "Shoes", "Hat", "Scarf", "Socks"};

    private String[][] testCases = {testCase0, testCase1, testCase2};
    private int caseNumber = 0;

    private int speed = 1000;

    public final static String apiURL = "http://m-lab.cse.nd.edu:8080/fileupload/rest/files/upload";


    // RecordCountDownTimer class
    public class RecordingCountDownTimer extends CountDownTimer
    {
        private int timerCount;
        public RecordingCountDownTimer(long startTime, long interval)
        {
            super(startTime, interval);
            timerCount = 0;
        }

        @Override
        public void onFinish()
        {
            wordBox.setText("");
            stopRecording();
            startButton.setText("Play");
            startButton.setEnabled(true);
            recordAgainButton.setEnabled(true);
            uploadButton.setEnabled(true);
            recordAgainButton.setVisibility(View.VISIBLE);
            uploadButton.setVisibility(View.VISIBLE);
            startButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    onPlay();
                }
            });
        }

        @Override
        public void onTick(long millisUntilFinished)
        {
            wordBox.setText(testCases[caseNumber][timerCount]);
            timerCount = timerCount +1;
        }
    }

    // PlayCountDownTimer class
    public class PlayCountDownTimer extends CountDownTimer
    {
        public PlayCountDownTimer(long startTime, long interval)
        {
            super(startTime, interval);
        }

        @Override
        public void onFinish()
        {
            stopPlaying();
            startButton.setEnabled(true);
            recordAgainButton.setEnabled(true);
            uploadButton.setEnabled(true);
        }

        @Override
        public void onTick(long millisUntilFinished)
        {

        }
    }

    private void onRecord() {
        try {

            recordTimer = new RecordingCountDownTimer((testCases[caseNumber].length + 1) * speed, speed);
            recordTimer.start();
            startButton.setEnabled(false);
            startRecording();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onRecordAgain() {
        recordAgainButton.setVisibility(View.GONE);
        uploadButton.setVisibility(View.GONE);
        startButton.setText("Start");
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onRecord();
            }
        });
    }

    private void onPlay() {
        startButton.setEnabled(false);
        recordAgainButton.setEnabled(false);
        uploadButton.setEnabled(false);
        playTimer = new PlayCountDownTimer((testCases[caseNumber].length + 1) * speed, speed);
        playTimer.start();
        startPlaying();
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    private void onUpload() {
        caseNumber++;
        caseNumberText.setText("Test Case: " + String.valueOf(caseNumber + 1));
        new CallAPI().execute(mFileName);
        onRecordAgain();
    }

    private void startRecording() throws IOException {
        mRecorder = new MediaRecorder();
        AudioRecordTest();
        //mFileName = Environment.getExternalStorageDirectory().getAbsolutePath().substring(8) + "/audio/" +  System.currentTimeMillis()+ ".3gp";
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    public void AudioRecordTest() {
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/audiorecordtest.wav";
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.activity_voice_recorder);
        startButton = (Button) findViewById(R.id.startButton);
        recordAgainButton = (Button) findViewById(R.id.recordAgainButton);
        uploadButton = (Button) findViewById(R.id.uploadButton);
        recordAgainButton.setVisibility(View.GONE);
        uploadButton.setVisibility(View.GONE);
        wordBox = (TextView) findViewById(R.id.wordBox);
        caseNumberText = (TextView) findViewById(R.id.caseNumberText);

        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onRecord();
            }
        });

        recordAgainButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onRecordAgain();
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onUpload();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    private class CallAPI extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            String urlString = apiURL;
            String charset = "UTF-8";
            File uploadFile1 = new File(params[0]);
            String response = "";

            //HTTP POST
            try {
                MultiPartUtility multipart = new MultiPartUtility(urlString, charset);

                multipart.addFilePart("fileUpload", uploadFile1);

                response = multipart.finish();

            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                return e.getMessage();
            }
            return response;

        }
        protected void onPostExecute(String result){
            System.out.println(result);
        }
    } //END CALL API
}
