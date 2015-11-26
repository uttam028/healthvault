package edu.nd.phr;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.os.*;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends ActionBarActivity {
    public final static String apiURL = "http://m-health.cse.nd.edu:8000/phrService-0.0.1-SNAPSHOT/login/login";
    public final static String MY_PREFERENCES = "myPrefs";
    SharedPreferences sharedpreferences;
    //private subclass to call the API
    private class CallAPI extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            boolean serverDown = false; //for debugging purposes
            if (serverDown) return "TRUE";
            String urlString = apiURL;
            String body = params[0];
            byte[] bytebody = body.getBytes();
            InputStream in;

            //HTTP PUT
            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("PUT");
                urlConnection.setRequestProperty("Content-Type", "application/xml");
                try {
                    DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                    wr.write(bytebody);
                    in = new BufferedInputStream(urlConnection.getInputStream());
                }
                catch (Exception e) {
                        e.printStackTrace();
                        return e.getMessage();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                return e.getMessage();
            }

            byte[] contents = new byte[1024];
            int bytesRead;
            String strFileContents = null;
            try {
                while ((bytesRead = in.read(contents)) != -1) {
                    strFileContents = new String(contents, 0, bytesRead);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
            Log.i("LoginActivity","doInBackGround - response to API call is " + strFileContents);
            return strFileContents;
        }
        protected void onPostExecute(String result){
            if (result.equals("TRUE")){
                //on success TODO: begin storing data about user for quick displays
                EditText emailEditText = (EditText) findViewById(R.id.editText_email_address);
                String email = emailEditText.getText().toString();
                sharedpreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("email", email);
                editor.commit();
                Intent i = new Intent(getApplicationContext(), HealthOptionsActivity.class);
                startActivity(i);
            }
            else if(result.equals("FALSE")){
                AlertDialog.Builder loginAlert = new AlertDialog.Builder(LoginActivity.this);
                loginAlert.setMessage("Invalid login credentials.");
                loginAlert.setTitle("Login Error");
                loginAlert.setPositiveButton("OK",null);
                loginAlert.setCancelable(true);
                loginAlert.create().show();
                String message = "Invalid Login. Try again.";
                Log.i("LoginActivity", "onPostExecute - error to display " + message);
            }
            else {
                AlertDialog.Builder loginAlert = new AlertDialog.Builder(LoginActivity.this);
                loginAlert.setMessage("Error connecting to server.");
                loginAlert.setTitle("Server Error");
                loginAlert.setPositiveButton("OK",null);
                loginAlert.setCancelable(true);
                loginAlert.create().show();
                String error = "Error connecting to server. Try again later.";
                Log.i("LoginActivity", "onPostExecute - error to display " + error);
            }
        }
    } //END CALL API

    public void verifyLogin(View view) {
        EditText emailEditText = (EditText) findViewById(R.id.editText_email_address);
        EditText passwordEditText = (EditText) findViewById(R.id.editText_password);
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (email != null && password != null){
            String xmlbody = "<user><email>" + email + "</email><password>" + password + "</password></user>";
            new CallAPI().execute(xmlbody);
        }
    }
    public void toSignup(View view) {
        Intent j = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(j);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        if (email != null) {
            EditText emailEdit = (EditText) findViewById(R.id.editText_email_address);
            emailEdit.setText(email);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
