package com.sharifian.shaheen.mygradetracker;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.gesture.GestureOverlayView;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class MyClassesAct extends ActionBarActivity {
    private static final int TEXT_SIZE = 16;
    private ViewFlipper flipper;
    private int maxClasses;
    private int currentPage;
    private MyClass thisClass;
    Gson gson;
    private SharedPreferences mPref;
    private SharedPreferences.Editor editor;
    LinearLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_classes2);

        params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 2f);

        gson = new Gson();
        mPref = getSharedPreferences("myClass", MODE_PRIVATE);
        editor = mPref.edit();

        flipper = (ViewFlipper) findViewById(R.id.flipperId);
        maxClasses = mPref.getInt("maxClasses", 0);
        currentPage = mPref.getInt("currentClass", maxClasses);

        for (int i = 0; i < maxClasses; i++) {
            String current = "classes_" + i;
            String value = mPref.getString(current, null);
            thisClass = gson.fromJson(value, MyClass.class);
            addClass(i);
        }
        if (currentPage < maxClasses) {
            flipper.setDisplayedChild(currentPage);
        } else {
            flipper.setDisplayedChild(maxClasses - 1);
        }
    }

    private void addTextView(LinearLayout innerLayout, String cat, String weight) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        innerLayout.addView(layout);

        params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT, 1f);
        TextView tv = new TextView(this);
        tv.setText(cat + " " + weight);
        tv.setTextSize(TEXT_SIZE);
        tv.setGravity(Gravity.LEFT);
        tv.setLayoutParams(params);
        layout.addView(tv);

        params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 1.5f);
        tv = new TextView(this);
        tv.setTextSize(TEXT_SIZE);
        tv.setLayoutParams(params);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        layout.addView(tv);
        tv = new TextView(this);
        tv.setTextSize(TEXT_SIZE);
        tv.setLayoutParams(params);
        tv.setGravity(Gravity.RIGHT);
        layout.addView(tv);
    }

    private void addClass(int index) {

        LinearLayout layout = (LinearLayout) findViewById(R.id.outMostLayout);
        ViewFlipper flipper = (ViewFlipper) layout.getChildAt(0);
        LinearLayout verticalLayout = new LinearLayout(this);
        verticalLayout.setOrientation(LinearLayout.VERTICAL);
        verticalLayout.setId(index);
        flipper.addView(verticalLayout);


        TextView tv = new TextView(this);
        tv.setTextSize(30);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        verticalLayout.addView(tv);

        LinearLayout innerLayout = new LinearLayout(this);
        innerLayout.setOrientation(LinearLayout.VERTICAL);
        verticalLayout.addView(innerLayout);

        addTextView(innerLayout, "Homework", "" + thisClass.weights[0] + "%");
        addTextView(innerLayout, "Midterm", "" + thisClass.weights[1] + "%");
        addTextView(innerLayout, "Lab", "" + thisClass.weights[2] + "%");
        addTextView(innerLayout, "Participation", "" + thisClass.weights[3] + "%");
        addTextView(innerLayout, "Final", "" + thisClass.weights[4] + "%");

        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        verticalLayout.addView(layout);

        tv = new TextView(this);
        tv.setTextSize(22);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        verticalLayout.addView(tv);

        tv = new TextView(this);
        tv.setText("Previous Assignments:");
        tv.setTextSize(16);
        verticalLayout.addView(tv);

        ScrollView sv = new ScrollView(this);
        LinearLayout.LayoutParams param = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        sv.setLayoutParams(param);
        verticalLayout.addView(sv);

        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        sv.addView(layout);
        sv.isClickable();
        sv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // dialog box
                return false;
            }
        });

        updateScore(index);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_classes, menu);

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
        }// else {}

        return super.onOptionsItemSelected(item);
    }

    private void updateScore(int index) {
        double[] total = new double[5];
        double[] max = new double[5];
        MyClass c = thisClass;
        for (int i = 0; i < c.assignments.size(); i++) {
            Assignment x = c.assignments.get(i);
            int category = x.Category.ordinal();
            total[category] += x.score;
            max[category] += x.maxScore;
        }

        LinearLayout verticalLayout = (LinearLayout) findViewById(index);
        TextView tv = (TextView) verticalLayout.getChildAt(0);
        tv.setText(c.className);

        LinearLayout innerLayout = (LinearLayout) verticalLayout.getChildAt(1);
        for (int i = 0; i < 5; i++) {
            LinearLayout layout = (LinearLayout) innerLayout.getChildAt(i);
            TextView tb = (TextView) layout.getChildAt(2);
            tb.setText(total[i] + "/" + max[i]);
            tb = (TextView) layout.getChildAt(1);
            if (max[i] > 0) {
                double percent = total[i] / max[i];
                tb.setText("" + String.format("%.2f", percent * 100) + "%");
            } else {
                tb.setText("--");
            }
        }

        // we are stupid

        double totalScore = 0;
        double totalWeight = 0;
        for (int i = 0; i < 5; i++) {
            if (max[i] > 0) {
                totalWeight += c.weights[i];
                totalScore += total[i] / max[i] * c.weights[i];
            }
        }

        double overallPercent = totalScore / totalWeight;

        TextView tb = (TextView) verticalLayout.getChildAt(3);
        if (totalScore > 0 && totalWeight > 0) {
            tb.setText("Total Score: " + String.format("%.2f", overallPercent * 100) + "%");
        } else {
            tb.setText("Total Score: --");
        }

        ScrollView sv = (ScrollView) verticalLayout.getChildAt(5);
        LinearLayout layout = (LinearLayout) sv.getChildAt(0);
        layout.removeAllViews();
        for (int i = 0; i < c.assignments.size(); i++) {
            Assignment x = c.assignments.get(i);
            tb = new TextView(this);
            tb.setText(x.Category.toString() + " " + x.score + "/" + x.maxScore );
            layout.addView(tb);
        }
    }

    // Try to implement animations here (crossfade)
    public boolean onTouchEvent(MotionEvent touchevent) {
        float initialX = 0;
        ViewFlipper TruitonFlipper = (ViewFlipper) findViewById(R.id.flipperId);

        switch (touchevent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = touchevent.getX();
                break;
            case MotionEvent.ACTION_UP:
                float finalX = touchevent.getX();
                if (initialX > finalX) {
//                    currentPage -= 1;
//                    if (currentPage < 0) {
//                        currentPage += maxClasses;
//                    }
                    TruitonFlipper.showNext();
                } else {
                    TruitonFlipper.showPrevious();
                    currentPage = flipper.getDisplayedChild();
                }
                currentPage = flipper.getDisplayedChild();
                break;
        }
        return false;
    }

    public void goToAddClassOnClick(MenuItem item) {
        SharedPreferences saveFile = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = saveFile.edit();
        editor.putInt("maxClasses", maxClasses + 1);
        editor.putInt("currentClass", maxClasses + 1);
        // intent
        Intent goToCreateClass = new Intent(this, CreateClass.class);
        final int result = 1;
        goToCreateClass.putExtra("callingActivity", "MainActivity");
        startActivity(goToCreateClass);
    }

    public void onClickToAddAssn(MenuItem item) {
        editor.putInt("maxClasses", maxClasses);
        editor.putInt("currentClass", currentPage);
        editor.commit();
        // intent
        Intent goToCreateAssn = new Intent(this,addGradeActivity.class);
        final int result = 1;
        startActivity(goToCreateAssn);
    }

    public void onClickRemoveClass(MenuItem item) {
        flipper.removeViewAt(currentPage);
        editor.putInt("maxClasses", maxClasses - 1);
        for (int i = currentPage - 1; i < maxClasses - 1; i++) {
            String current = "classes_" + (i + 1);
            String value = mPref.getString(current, null);
            String user_json = gson.toJson(gson.fromJson(value, MyClass.class));
            String id = "classes_" + i;
            editor.putString(id, user_json);
        }
        editor.commit();
        currentPage++;
        if (maxClasses == 0) {
            // intent to createClass
            Intent goToCreateClass = new Intent(this, CreateClass.class);
            final int result = 1;
            startActivity(goToCreateClass);

        }
    }

    public void onClickRemoveAllClasses(MenuItem item) {
        // remove all classes
        editor.putInt("maxClasses", 0);
        editor.commit();
        Intent goToCreateClass = new Intent(this, CreateClass.class);
        final int result = 1;
        startActivity(goToCreateClass);
    }

    public void onClickToEditAssn(MenuItem item) {
        editor.putInt("maxClasses", maxClasses);
        editor.putInt("currentClass", currentPage);
        editor.commit();
        Intent goToEditAssn = new Intent(this, Edit_assignment.class);
        final int result = 1;
        startActivity(goToEditAssn);
    }
}
