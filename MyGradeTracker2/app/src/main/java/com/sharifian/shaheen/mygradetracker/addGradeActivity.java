package com.sharifian.shaheen.mygradetracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class addGradeActivity extends ActionBarActivity {
    private MyClass thisClass;
    private int currentClass;
    SharedPreferences mPref;
    SharedPreferences.Editor editor;
    private RadioGroup radioGroup;
    Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grade);

        mPref = getSharedPreferences("myClass", MODE_PRIVATE);
        editor = mPref.edit();

        currentClass = mPref.getInt("currentClass", 0);
        gson = new Gson();
        String current = "classes_" + currentClass;
        String value = mPref.getString(current, null);
        thisClass = gson.fromJson(value, MyClass.class);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_grade, menu);
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
//
//    public void addClassOnClick(MenuItem item) {
//        Intent getMyClassesIntent = new Intent(this, MyClassesAct.class);
//        final int result = 1;
//        getMyClassesIntent.putExtra("callingActivity", "MainActivity");
//        startActivity(getMyClassesIntent);
//    }

    public void onClickToMyClasses(View view) {
        Assignment x = new Assignment();
        EditText tv = (EditText) findViewById(R.id.yourScoreET);
        x.score = Integer.parseInt(tv.getText().toString());
        tv = (EditText) findViewById(R.id.maxScoreET);
        x.maxScore = Integer.parseInt(tv.getText().toString());
        tv = (EditText) findViewById(R.id.assignmentNameET);
        x.name = tv.getText().toString();
        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
        int id = rg.getCheckedRadioButtonId();
        if (id == R.id.rbHomework) {
            x.Category = MyClass.Category.Homework;
        } else if(id == R.id.rbMidterms) {
            x.Category = MyClass.Category.Midterm;
        } else if(id == R.id.rbLabs) {
            x.Category = MyClass.Category.Labs;
        } else if(id == R.id.rbParticipation) {
            x.Category = MyClass.Category.Participation;
        } else {
            x.Category = MyClass.Category.Finals;
        }
        if (x.maxScore < 0 || x.score <= 0 ) {
            return;
        }

        thisClass.assignments.add(x);
        String user_json = gson.toJson(thisClass, MyClass.class);
        String name = "classes_" + currentClass;
        editor.putString(name, user_json);
        editor.commit();

        Intent getMyClassesIntent = new Intent(this, MyClassesAct.class);
        final int result = 1;
        getMyClassesIntent.putExtra("callingActivity", "MainActivity");
        startActivity(getMyClassesIntent);
    }
}
