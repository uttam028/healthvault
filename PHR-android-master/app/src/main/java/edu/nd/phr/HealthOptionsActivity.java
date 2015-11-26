package edu.nd.phr;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class HealthOptionsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_options);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_health_options, menu);
        return true;
    }
    public void toAccount(View view) {
        Intent i = new Intent(getApplicationContext(),AccountActivity.class);
        startActivity(i);
    }
    //@Override
    public void toCholestoral(View view) {
    //TODO:if button is clicked, take to cholesterol page
        Intent i = new Intent(getApplicationContext(),CholestoralActivity.class);
        startActivity(i);
    }

    //@Override
    public void toVoiceRecord(View view) {
        //TODO:if button is clicked, take to cholesterol page
        Intent i = new Intent(getApplicationContext(),VoiceRecordActivity.class);
        startActivity(i);
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
