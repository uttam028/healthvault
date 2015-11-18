package edu.nd.phr;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class SignupActivity extends ActionBarActivity {
    public final static String emailAvailabilityURL = "http://m-health.cse.nd.edu:8000/phrService-0.0.1-SNAPSHOT/signup/signup/<email>";
    public final static String signupURL = "http://m-health.cse.nd.edu:8000/phrService-0.0.1-SNAPSHOT/signup/signup";
    //subclass to verify and send signup information in background
    private class SignupAPICall extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params)
        {
            EditText first = (EditText)findViewById(R.id.signup_firstname);
            EditText last = (EditText)findViewById(R.id.signup_lastname);
            String firstStr = first.getText().toString();
            String lastStr = last.getText().toString();
            if (firstStr.isEmpty() || lastStr.isEmpty()){
                return "missing_fields";
            }
            EditText emailEdit = (EditText)findViewById(R.id.signup_email);
            String email = emailEdit.getText().toString();
            String URLcall = signupURL + "/" + email;
            InputStream in;
            Log.i("SignupActivity", "The value of params[0] is:" + params[0]);
            try {
                URL url = new URL(URLcall);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type","application/json");
                try {
                    in = new BufferedInputStream(urlConnection.getInputStream());
                }
                catch (Exception e) {
                    e.printStackTrace();
                    return "error";
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return "error";
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
                 return "error";
            }
            Log.i("SignupActivity","response to API call is: "+strFileContents);
            //strFileContents contains TRUE here if email is already taken
            if ("TRUE".equals(strFileContents)){
                return "email_taken"; //returns "taken" if email is taken
            }
            //TODO:REGISTER
            String body = params[0];
            byte[] bytebody = body.getBytes();
            String regURL = signupURL;
            InputStream input;
            String S_strFileContents = null;
            byte[] S_contents = new byte[1024];
            int S_bytesRead;
            if ("FALSE".equals(strFileContents)) {
                    //TODO: call register method
                    Log.i("SignupActivity", "Response for email validation is FALSE");
                    try {
                        URL url = new URL(regURL);
                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                        urlConnection.setRequestMethod("PUT");
                        urlConnection.setRequestProperty("Content-Type", "application/xml");
                        try {
                            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                            wr.write(bytebody);
                            input = new BufferedInputStream(urlConnection.getInputStream());
                        } catch (Exception e) {
                            e.printStackTrace();
                            return "error";
                        }
                    } catch (Exception e) {
                        e.getStackTrace();
                        return "error";
                    }
                    try {
                        while ((S_bytesRead = input.read(S_contents)) != -1) {
                            S_strFileContents = new String(S_contents, 0, S_bytesRead);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        return "error";
                    }
                return S_strFileContents;
                }
            else {
                return "error";
            }
}
        protected void onPostExecute(String result){
            String str = result.substring(0,4);
            str = str.toLowerCase();
            //server side error or some other error
            if (str.equals("error")){
                AlertDialog.Builder loginAlert = new AlertDialog.Builder(SignupActivity.this);
                loginAlert.setMessage("Error connecting to server");
                loginAlert.setTitle("Error");
                loginAlert.setPositiveButton("OK", null);
                loginAlert.setCancelable(true);
                loginAlert.create().show();
            }
            //firstname or lastname fields are empty
            else if (result.equals("missing_fields")){
                AlertDialog.Builder loginAlert = new AlertDialog.Builder(SignupActivity.this);
                loginAlert.setMessage("Every section must be completed!");
                loginAlert.setTitle("Registration Error");
                loginAlert.setPositiveButton("OK", null);
                loginAlert.setCancelable(true);
                loginAlert.create().show();
            }
            //email is already taken
            else if (result.equals("email_taken")){
                AlertDialog.Builder loginAlert = new AlertDialog.Builder(SignupActivity.this);
                loginAlert.setMessage("Email is already registered!");
                loginAlert.setTitle("Registration Error");
                loginAlert.setPositiveButton("OK", null);
                loginAlert.setCancelable(true);
                loginAlert.create().show();
            }
            //success!
            else {
                AlertDialog.Builder loginAlert = new AlertDialog.Builder(SignupActivity.this);
                loginAlert.setMessage("You have successfully registered.");
                loginAlert.setTitle("Success!");
                loginAlert.setPositiveButton("OK", null);
                loginAlert.setCancelable(true);
                loginAlert.create().show();
                //TODO: take user back to login page
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup, menu);
        return true;
    }
    public void verifySignup(View view) {
        EditText firstnameEditText = (EditText)findViewById(R.id.signup_firstname);
        EditText lastnameEditText = (EditText)findViewById(R.id.signup_lastname);
        EditText emailEditText = (EditText)findViewById(R.id.signup_email);
        EditText passwordEditText = (EditText)findViewById(R.id.signup_password);
        String first = firstnameEditText.getText().toString();
        String last = lastnameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        //have String values for First Name, Last Name, Email, Password
        if (email != null && password != null) {
            String XMLBody = "<user><firstName>"+first+"</firstName><lastName>"+last+"</lastName><email>"+email+"</email><password>"+password+"</password></user>";
            new SignupAPICall().execute(XMLBody);
        }
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
