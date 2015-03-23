package com.sharifian.shaheen.mygradetracker;

import java.util.ArrayList;

/**
 * Created by shaheensharifian on 1/9/15.
 */
public class MyClass {
    public enum Category {
        Homework, Midterm, Labs, Participation, Finals
    }
    public String className;
    public int[] weights;
    ArrayList<Assignment> assignments;

    public MyClass() {
        className = "Class Name";
        weights = new int[5];
        assignments = new ArrayList<Assignment>();
    }

    public MyClass(int index) {
        className = "" + index;
        weights = new int[5];
        assignments = new ArrayList<Assignment>();
    }
}
