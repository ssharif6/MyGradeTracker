package com.sharifian.shaheen.mygradetracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.Gson;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref = getSharedPreferences("myClass", MODE_PRIVATE);


        int run = sharedPref.getInt("runAgain?", 0);
        if (run == 1) {
            int maxClasses = sharedPref.getInt("maxClasses", 0);
            if (maxClasses > 0) {
                onClickToMyClass(new View(this));
            } else {
                onCreateClassClick(new View(this));
            }
        } else { // first time running program
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("runAgain?", 1);
            editor.putInt("maxClasses", 0);
            editor.commit();
            setContentView(R.layout.activity_main);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        } else if(id == R.id.menu_add_class){
            Intent goToAddClass = new Intent(this, CreateClass.class);
            final int result = 1;
            goToAddClass.putExtra("callingActivity", "MainActivity");
            startActivity(goToAddClass);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void sendMessage(View view) {
        // Do something in response to button
    }

    public void onCreateClassClick(View view) {
        Intent getCreateClassScreenIntent = new Intent(this,CreateClass.class );
        getCreateClassScreenIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        final int result = 1;
        getCreateClassScreenIntent.putExtra("callingActivity", "MainActivity");
        startActivity(getCreateClassScreenIntent);
    }



    public void onClickToMyClass(View view) {
        Intent goToMyClassesIntent = new Intent(this, MyClassesAct.class);
        goToMyClassesIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        final int result = 1;
        goToMyClassesIntent.putExtra("callingActivity", "MainActivity");
        startActivity(goToMyClassesIntent);
    }
}
