package com.sharifian.shaheen.mygradetracker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;


public class CreateClass extends Activity {
    private int maxClasses;
    private ArrayList<MyClass> classes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);
        SharedPreferences mPref = getSharedPreferences("myClass", MODE_PRIVATE);
        maxClasses = mPref.getInt("maxClasses", 0);
        Gson gson = new Gson();
        classes = new ArrayList<>();
        for (int i = 0; i < maxClasses; i++) {
            String current = "classes_" + i;
            String value = mPref.getString(current, null);
            classes.add(gson.fromJson(value, MyClass.class));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_class, menu);
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

    public void onClickToMyClasses(View view) {
        MyClass newClass = new MyClass();
        EditText input = (EditText)findViewById(R.id.ETclassName);
        if(input.length()== 0) {
            //toast Empty parameter
            Toast.makeText(getApplicationContext(),"You must give your class a name!", Toast.LENGTH_SHORT).show();
            return;
        }
        newClass.className = input.getText().toString();
        newClass.weights = new int[5];
        input = (EditText)findViewById(R.id.ETHomework);
        if(input.length() == 0) {
           //toast empty parameter
            Toast.makeText(getApplicationContext(),"There is an empty field!", Toast.LENGTH_SHORT).show();
            return;
        }
        newClass.weights[0] = Integer.parseInt(input.getText().toString());
        input = (EditText) findViewById(R.id.ETMidterm);
        if(input.length() == 0) {
            //toast empty parameter
            Toast.makeText(getApplicationContext(),"There is an empty field!", Toast.LENGTH_SHORT).show();
            return;
        }
        newClass.weights[1] = Integer.parseInt(input.getText().toString());
        input = (EditText) findViewById(R.id.ETLabs);
        if(input.length() == 0) {
            //toast empty parameter
            Toast.makeText(getApplicationContext(),"There is an empty field!", Toast.LENGTH_SHORT).show();
            return;
        }
        newClass.weights[2] = Integer.parseInt(input.getText().toString());
        input = (EditText) findViewById(R.id.ETParticipation);
        if(input.length()== 0) {
            //toast empty paramter
            Toast.makeText(getApplicationContext(),"There is an empty field!", Toast.LENGTH_SHORT).show();

            return;
        }
        newClass.weights[3] = Integer.parseInt(input.getText().toString());
        input = (EditText) findViewById(R.id.ETFinal);
        if(input.length() == 0) {
            // toast empty paramter
            return;
        }
        newClass.weights[4] = Integer.parseInt(input.getText().toString());
        int sum = newClass.weights[0] + newClass.weights[1] + newClass.weights[2] +
                newClass.weights[3] + newClass.weights[4];
        if (sum < 100) {
            // toast too small
            Toast.makeText(getApplicationContext(),"Your percentages are less than 100!", Toast.LENGTH_SHORT).show();
            return;
        } else if (sum > 100) {
            // toast too big
            Toast.makeText(getApplicationContext(),"Your percentages are greater than 100!", Toast.LENGTH_SHORT).show();
            return;
        }

        classes.add(newClass);

        SharedPreferences prefs = getSharedPreferences("myClass", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        maxClasses = classes.size();
        editor.putInt("maxClasses", classes.size());
        Gson gson = new Gson();
        for (int i = 0; i < maxClasses; i++) {
            String user_json = gson.toJson(classes.get(i));
            String id = "classes_" + i;
            editor.putString(id, user_json);
        }
        editor.commit();
        Intent getMyClassesIntent = new Intent(this, MyClassesAct.class);
        final int result = 1;
        getMyClassesIntent.putExtra("callingActivity", "MainActivity");
        startActivity(getMyClassesIntent);
    }
}
