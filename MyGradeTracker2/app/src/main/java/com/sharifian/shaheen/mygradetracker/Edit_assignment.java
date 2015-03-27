package com.sharifian.shaheen.mygradetracker;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.w3c.dom.Text;


public class Edit_assignment extends FragmentActivity {
    private Gson gson;
    private MyClass thisClass;
    private int currentClass;
    private SharedPreferences mPref;
    private SharedPreferences.Editor editor;
    private LinearLayout layout;
    final Context context = this;
    private Button button;
    private EditText result;
    private int temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assignment);

        gson = new Gson();
        mPref = getSharedPreferences("myClass", MODE_PRIVATE);
        editor = mPref.edit();
        currentClass = mPref.getInt("currentClass", -1);

        if (currentClass < 0) {
            Intent goToMyClasses = new Intent(this, MyClassesAct.class);
            final int result = 1;
            goToMyClasses.putExtra("callingActivity", "MainActivity");
            startActivity(goToMyClasses);
        }

        String current = "classes_" + currentClass;
        String value = mPref.getString(current, null);
        thisClass = gson.fromJson(value, MyClass.class);

        printAssignment();
    }

    private void printAssignment() {

        layout = (LinearLayout) findViewById(R.id.assignmentListView);
        for (int i = 0; i < thisClass.assignments.size(); i++) {
            Assignment x = thisClass.assignments.get(i);
            TextView tb = new TextView(this);
            tb.setText(x.name + "(" + x.Category.toString() + ") " + x.score + "/" + x.maxScore);
            tb.setTextSize(20);
            tb.setId(i);
            tb.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
//                    MyDialogFragment myFragment = new MyDialogFragment();
//                    myFragment.show(getFragmentManager(), "theDialog");
//                     // showDialog(v);
//                   int index = v.getId();
//                   thisClass.assignments.remove(index);
//                   layout.removeAllViews();
//                    printAssignment();
                    temp = v.getId();
                    LayoutInflater li = LayoutInflater.from(context);
                    View promptsView = li.inflate(R.layout.dialoglayout, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            context);

                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView);
                    final TextView userInput = (TextView) promptsView
                            .findViewById(R.id.txt_dia);

                    // set dialog message
                    alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            // get user input and set it to result
                                            // edit text
                                            //                   int index = v.getId();
                                            thisClass.assignments.remove(temp);
                                            layout.removeAllViews();
                                            printAssignment();
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            dialog.cancel();
                                        }
                                    });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();



                    return true;
                }
            });
            layout.addView(tb);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_assignment, menu);
        return true;
    }
    public void showDialog(View view) {
        FragmentManager manager = getFragmentManager();
        MyDialogFragment myDialog = new MyDialogFragment();
        myDialog.show(manager,"Dialogue");
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

    public void onClickToEditAssn(View view) {
        String user_json = gson.toJson(thisClass);
        editor.putString("classes_" + currentClass, user_json);
        editor.commit();
        Intent goToMyClasses = new Intent(this, MyClassesAct.class);
        final int result = 1;
        goToMyClasses.putExtra("callingActivity", "MainActivity");
        startActivity(goToMyClasses);
    }

    // dialogue box option that deletes assignment
    public void deleteAssn(View view) {
        // delete
        Intent goToEditAssn = new Intent(this, Edit_assignment.class);
        final int result = 1;
        startActivity(goToEditAssn);
    }

    public void goBackToEditAssn(View view) {
        Intent goToEditAssn = new Intent(this, Edit_assignment.class);
        final int result = 1;
        startActivity(goToEditAssn);
    }
}
