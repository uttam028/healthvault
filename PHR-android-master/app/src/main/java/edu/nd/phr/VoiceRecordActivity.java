package edu.nd.phr;

import android.annotation.TargetApi;
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
import android.widget.ProgressBar;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import android.util.Log;
import android.media.MediaRecorder;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import org.w3c.dom.Text;
import android.animation.ObjectAnimator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.animation.DecelerateInterpolator;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.lang.Math;

import edu.nd.phr.MultiPartUtility;

import java.io.File;
import java.io.OutputStream;
import java.util.List;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;



public class VoiceRecordActivity extends ActionBarActivity{

    public class Word{
        String word;
        int duration;

        public Word(String s, int d){
            word = s;
            duration = d;
        }
    }

    public class ProgressBarAnimation extends Animation{
        private ProgressBar progressBar;
        private float from;
        private float  to;

        public ProgressBarAnimation(ProgressBar progressBar, float from, float to) {
            super();
            this.progressBar = progressBar;
            this.from = from;
            this.to = to;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            float value = from + (to - from) * interpolatedTime;
            progressBar.setProgress((int) value);
        }

    }

    private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName = null;;

    private MediaRecorder mRecorder = null;

    private MediaPlayer   mPlayer = null;

    private Button startButton;
    private Button recordAgainButton;
    private Button uploadButton;
    private TextView wordBox;
    private TextView caseNumberText;
    private ProgressBar progressBar;
    private RecordingCountDownTimer recordTimer;
    private PlayCountDownTimer playTimer;
    private WordCountDownTimer wordTimer;
    private boolean stillRunning;

    private Word[] testCase0 = {new Word("Cheese", 1000), new Word("Sausage", 500), new Word("Ham", 2000), new Word("Sauce", 1000), new Word("Pepperoni", 500), new Word("Mushrooms", 1000)};
    private Word[] testCase1 = {new Word("Pants", 100), new Word("Shoes", 50), new Word("Hat", 200)};
    private Word[] testCase2 = {new Word("Lamp", 100), new Word("Desk", 50), new Word("Pencil Sharpener", 200), new Word("Chair", 100)};
    private Word[][] testCases = {testCase0, testCase1, testCase2};

    private int[] testCaseTimes;
    private int caseNumber = 0;
    int wordCount;

    public final static String apiURL = "http://m-lab.cse.nd.edu:8080/fileupload/rest/files/upload";

    int timerRunning;




    // RecordCountDownTimer class
    public class RecordingCountDownTimer extends AccurateTimer
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
            timerRunning = 0;
            wordBox.setText("");
            stopRecording();
            startButton.setText("Play");
            startButton.setEnabled(true);
            recordAgainButton.setEnabled(true);
            uploadButton.setEnabled(true);
            recordAgainButton.setVisibility(View.VISIBLE);
            uploadButton.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            startButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    onPlay();
                }
            });
        }

        @Override
        public void onTick(long millisUntilFinished)
        {
            timerCount = timerCount +1;
        }
    }

    // RecordCountDownTimer class
    public class WordCountDownTimer extends AccurateTimer
    {
        private int tickCount;
        public WordCountDownTimer(long startTime, long interval)
        {
            super(startTime, interval);
            tickCount = 0;
        }

        @Override
        public void onFinish()
        {
            wordCount++;
            if(wordCount < testCases[caseNumber].length) {
                createWordTimer();
            }
        }

        @Override
        public void onTick(long millisUntilFinished)
        {
        }
    }

    // PlayCountDownTimer class
    public class PlayCountDownTimer extends AccurateTimer
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

    @TargetApi(11)
    private void createWordTimer(){
        wordBox.setText(testCases[caseNumber][wordCount].word);
        wordTimer = new WordCountDownTimer(testCases[caseNumber][wordCount].duration, testCases[caseNumber][wordCount].duration);
        ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, 0, 100);
        anim.setDuration(testCases[caseNumber][wordCount].duration);
        progressBar.startAnimation(anim);
        wordTimer.start();
    }

    private void onRecord() {
        try {
            startRecording();
            startButton.setEnabled(false);
            recordTimer = new RecordingCountDownTimer(testCaseTimes[caseNumber], testCaseTimes[caseNumber]);
            recordTimer.start();
            wordCount = 0;
            progressBar.setVisibility(View.VISIBLE);
            createWordTimer();

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
        playTimer = new PlayCountDownTimer(testCaseTimes[caseNumber], 1);
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
        caseNumberText.setText("Test Case: 1");
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

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

        testCaseTimes = new int[testCases.length];
        int i, k;
        int length;
        for(i = 0; i < testCases.length; i++){
            length = 0;
            for(k = 0; k < testCases[i].length; k++){
                length = length + testCases[i][k].duration;
            }
            testCaseTimes[i] = length;
        }
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
        ProgressDialog progress = new ProgressDialog(VoiceRecordActivity.this);
        @Override
        protected String doInBackground(String... params) {
            String urlString = apiURL;
            String charset = "UTF-8";
            File uploadFile1 = new File(params[0]);
            String response = "";

            try {
                URL url = new URL("http://m-lab.cse.nd.edu:8080/fileupload/rest/files/upload/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");

                FileBody fileBody = new FileBody(uploadFile1);
                MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.STRICT);
                multipartEntity.addPart("file", fileBody);

                connection.setRequestProperty("Content-Type", multipartEntity.getContentType().getValue());
                OutputStream out = connection.getOutputStream();
                try {
                    multipartEntity.writeTo(out);
                } finally {
                    out.close();
                }
                int status = connection.getResponseCode();
                System.out.println("status:" + status);
                return Integer.toString(status);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "TRUE";
        }

        @Override
        protected void onPreExecute() {

            progress.setTitle("Uploading");
            progress.setMessage("Wait while uploading...");

            progress.show();
            progress.setCancelable(false);
        }

        protected void onPostExecute(String result){
            // Dismiss progress Bar
            progress.dismiss();

            //Create Alert
            if(result.equals("200")){
                new AlertDialog.Builder(VoiceRecordActivity.this)
                        .setMessage("Upload Successful")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            else{
                new AlertDialog.Builder(VoiceRecordActivity.this)
                        .setMessage("Upload Failed")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }

        }
    } //END CALL API
}
