package com.sharifian.shaheen.mygradetracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by shaheensharifian on 1/15/15.
 */


// This will be used to create a dialog window


// If you get an error that the minimum must be 11, change the minimum in the manifest
// and also change it in build.gradle

// To generate the onCreateDialog() right click on DialogFragment and Generate and
// select onCreateDialog()

public class MyDialogFragment extends DialogFragment{
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialoglayout, null);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // We build the dialog
        // getActivity() returns the Activity this Fragment is associated with
        AlertDialog.Builder theDialog = new AlertDialog.Builder(getActivity());

        // Set the title for the Dialog
        theDialog.setTitle("Sample Dialog");

        // Set the message
        theDialog.setMessage("Hello I'm a Dialog");

        // Add text for a positive button
        theDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Toast.makeText(getActivity(), "Clicked OK", Toast.LENGTH_SHORT).show();

            }
        });

        // Add text for a negative button
        theDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Toast.makeText(getActivity(), "Clicked Cancel", Toast.LENGTH_SHORT).show();

            }
        });

        // Returns the created dialog
        return theDialog.create();

    }
}