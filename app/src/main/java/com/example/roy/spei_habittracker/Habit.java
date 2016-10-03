package com.example.roy.spei_habittracker;

import android.util.Log;

import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by Roy on 2016/09/26.
 */
public class Habit {
    private String title;
    private Calendar date;
    private int[] repeat = {0, 0, 0, 0, 0, 0, 0};
    private int completedTime;

    // Default constructor
    // Construct an object with empty title, current date, completed 0 time, and no repeat.
    // Learn code from: http://stackoverflow.com/questions/10155523/android-compare-calendar-dates
    public Habit() {
        this.title = "";
        this.date = Calendar.getInstance();
        this.completedTime = 0;
    }

    // Pass 4 parameter to constructor. Used for user to add new habit.
    public Habit(String inputTitle, int[] inputRepeat, int inputCompleteTime, Calendar inputDate) {
        this.title = inputTitle;
        this.date = inputDate;
        this.repeat = inputRepeat;
        this.completedTime = inputCompleteTime;
    }

    // Getter for Title
    public String getTitle() {
        return title;
    }

    // Getter for Date
    public Calendar getDate() {
        return date;
    }

    // Getter for repeat
    public int[] getRepeat() {
        return repeat;
    }

    // Getter for completed time
    public int getCompletedTime() {
        return completedTime;
    }

    // Setter for Title
    public void setTitle(String title) {
        this.title = title;
    }

    // Setter for Date
    public void setDate(Calendar date) {
        this.date = date;
    }

    // Setter for Repeat
    public void setRepeat(int[] repeat) {
        this.repeat = repeat;
    }

    // Setter for completed time
    public void setCompletedTime(int completedTime) {
        this.completedTime = completedTime;
    }

    // +1 to completedTime
    public void addCompletedTime() {
        this.completedTime += 1;
    }

    // -1 to completedTime
    public void deleteCompletedTime() {
        this.completedTime -= 1;
    }

    // Check if the habit is completed or not
    public boolean isCompleted() {
        if (this.completedTime>0){
            return true;
        } else {
            return false;
        }
    }

    // Change an object to String for View
    public String toString() {
        int year = this.date.get(Calendar.YEAR);
        int month = this.date.get(Calendar.MONTH)+1;
        int day = this.date.get(Calendar.DAY_OF_MONTH);
        return this.title
                + "\n" + "@ " + new StringBuilder().append(year).append(" - ").append(month).append(" - ").append(day)
                + "\n" + "Repeat: " + repeatToString(this)
                + "\n" + "Completed " + String.valueOf(this.completedTime) + " times.";
    }

    // Helper function of repeat
    // Change int[] to String
    private String repeatToString (Habit habit) {
        int[] repeat = habit.getRepeat();
        String repeatString = "";
        for (int i=0; i<7; i++) {
            if (repeat[i]==1) {
                repeatString += String.valueOf(i+1) + " ";
            }
        }
        return repeatString;
    }

    // Use Log.d to print for debugging
    public void print () {
        Log.d("Debug in Habit Class", this.toString());
    }

    // Check if two habit objects with different completionTime are the same habit
    protected Boolean isSameHabit (Habit habit) {
        if (! (this.title.equals(habit.getTitle()))) {
            //Log.d("DDDDDDDDD", "Title");
            return false;
        } else if (! (Arrays.equals(this.repeat, habit.getRepeat()))) {
            //Log.d("DDDDDDDDD", "Repeat");
            return false;
        } else if (!( (this.date.getTimeInMillis())==(habit.getDate().getTimeInMillis()) ) ){
            //Log.d("DDDDDDDDD", "Date");
            return false;
        } else {
            return true;
        }
    }

    // Check is two habit objects are equal
    protected Boolean equals (Habit habit) {
        if (! (this.title.equals(habit.getTitle()))) {
            //Log.d("DDDDDDDDD", "Title");
            return false;
        } else if (! (Arrays.equals(this.repeat, habit.getRepeat()))) {
            //Log.d("DDDDDDDDD", "Repeat");
            return false;
        } else if (!( (this.date.getTimeInMillis())==(habit.getDate().getTimeInMillis()) ) ){
            //Log.d("DDDDDDDDD", "Date");
            return false;
        } else if (! (this.completedTime==habit.getCompletedTime())) {
            //Log.d("DDDDDDDDD", "Completed");
            return false;
        } else {
            return true;
        }
    }



}

