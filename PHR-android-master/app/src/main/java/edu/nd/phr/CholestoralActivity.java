package edu.nd.phr;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class CholestoralActivity extends ActionBarActivity {
    public GraphView graph;
    LineGraphSeries<DataPoint> series;
    public ArrayList<CholesterolInformation> cholesterolInformationList;
    //public static final String listURL = "http://m-health.cse.nd.edu:8000/phrService-0.0.1-SNAPSHOT/chol/chol/";


    public void setCholesterolInformationList(ArrayList<CholesterolInformation> cholesterolInformationList) {
        this.cholesterolInformationList = cholesterolInformationList;
    }

    //TODO: Populate ArrayList given JSON response, and call generateDataPoints()
    private class ListCholestoralAPI extends AsyncTask<String, Void, String> {
        private ArrayList<CholesterolInformation> tmpcholesterolInformationList;

        @Override
        protected String doInBackground(String... params){
            tmpcholesterolInformationList = new ArrayList<>();
            tmpcholesterolInformationList.clear();
            SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
            String email = sharedPreferences.getString("email", "");
            String listURL = params[0];
            listURL += email;
            URL url = null;
            String result = null;
            try {
                url = new URL(listURL);
            }
            catch (MalformedURLException e) {
                return e.getMessage();
            }
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                }
                in.close();
                result = response.toString();
            }
            catch (IOException e) {
                return e.getMessage();
            }
            try {
                JSONObject listObject = new JSONObject(result);
                JSONArray jsonArray = listObject.optJSONArray("cholesterol");
                if (jsonArray != null) {
                    for (int i=0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.optJSONObject(i);
                        CholesterolInformation cholesterolInformation = new CholesterolInformation();
                        if (jsonObject != null) {
                            String HDL = jsonObject.getString("hdl");
                            double hdl = Double.parseDouble(HDL);
                            String LDL = jsonObject.getString("ldl");
                            double ldl = Double.parseDouble(LDL);
                            String triGlyceride = jsonObject.getString("triGlycaride");
                            double tri = Double.parseDouble(triGlyceride);
                            String date = jsonObject.getString("date");
                            String unit = jsonObject.getString("unit");
                            cholesterolInformation.setDate(date);
                            cholesterolInformation.setHdl(hdl);
                            cholesterolInformation.setLdl(ldl);
                            cholesterolInformation.setTriGlycaride(tri);
                            cholesterolInformation.setUnit(unit);
                            tmpcholesterolInformationList.add(cholesterolInformation);
                        }
                    }

                }
            }
            catch (Exception e){
                return e.getMessage();
            }
            return "Success";
        }

        protected void onPostExecute(String result) {
            if ("Success".equals(result)) {
                System.out.println("Successfully populated list.");
                setCholesterolInformationList(tmpcholesterolInformationList);
            }
        }

    }

    //TODO: append data to List<CholestoralInformation>
    private class AddCholestoralAPI extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            String addURL = "http://m-health.cse.nd.edu:8000/phrService-0.0.1-SNAPSHOT/chol/chol/";
            SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
            String email = sharedPreferences.getString("email", "");
            double trigly = Double.parseDouble(params[0]);
            double hdl = Double.parseDouble(params[1]);
            double ldl = Double.parseDouble(params[2]);
            String date = "2014-01-01"; //hard coded for now
            String reqBody = "<cholesterol><date>"+date+"</date><hdl>"+hdl+"</hdl><ldl>"+ldl
                    +"</ldl><triGlyceride>"+trigly+"</triGlyceride><unit>mg</unit><uid>"+email
                    +"</uid></cholesterol>";
            byte[] bytebody = reqBody.getBytes();
            InputStream in;
            try {
                URL url = new URL(addURL);
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
            return strFileContents;
        }
        protected void onPostExecute(String result){
            if (result.contains("success")) {
                System.out.println("Successfully added");
            }
            else {
                System.out.println("Unsuccessful");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cholestoral);
        //empty arraylist on page load... to be filled in ASyncTask below
        new ListCholestoralAPI().execute("http://m-health.cse.nd.edu:8000/phrService-0.0.1-SNAPSHOT/chol/chol/");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cholestoral, menu);
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
    public void addCholesterolData(View view){
        EditText tri = (EditText) findViewById(R.id.editTextTriGly);
        EditText hdl = (EditText) findViewById(R.id.editTextHDL);
        EditText ldl = (EditText) findViewById(R.id.editTextLDL);
        String trygly_str = tri.getText().toString();
        String hdl_str = hdl.getText().toString();
        String ldl_str = ldl.getText().toString();
        //send EditText info to ASyncTask
        //cholesterolInformationList to be appended with new values and graph regenerated
        new AddCholestoralAPI().execute(trygly_str,hdl_str,ldl_str);
    }

    public void initializeGraph() {
        //TODO: initialize graph by setting labels, axes, etc...
    }
}
