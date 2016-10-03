package com.example.roy.spei_habittracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class Detail extends AppCompatActivity {

    private static final String FILENAME = "Habit.sav";
    private static final String DETAILFILE = "Detail.sav";
    private static final String HISTORYFILE = "History.sav";

    private ArrayList<Habit> habitArrayList = new ArrayList<Habit>();
    private ArrayList<Habit> clickedhabitArrayList = new ArrayList<Habit>();
    private ArrayList<Habit> historyArrayList = new ArrayList<Habit>();

    private Habit detailHabit = new Habit();

    private TextView detailName, detailNameDisplay;
    private TextView detailDate, detailDateDisplay;
    private TextView detailRepeat, detailRepeatDisplay;
    private TextView detailCompletions, detailCompletionsDisplay;

    private Button deleteButton, completeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // title
        detailName = (TextView) findViewById(R.id.detailName);
        detailNameDisplay = (TextView) findViewById(R.id.detailNameDisplay);

        // Date
        detailDate = (TextView) findViewById(R.id.detailDate);
        detailDateDisplay = (TextView) findViewById(R.id.detailDateDisplay);

        // Repeat
        detailRepeat = (TextView) findViewById(R.id.detailRepeat);
        detailRepeatDisplay = (TextView) findViewById(R.id.detailRepeatDisplay);

        // Completed Time
        detailCompletions = (TextView) findViewById(R.id.detailCompletions);
        detailCompletionsDisplay = (TextView) findViewById(R.id.detailCompletionsDisplay);

        completeButton = (Button) findViewById(R.id.completebutton);
        deleteButton = (Button) findViewById(R.id.deletebutton);

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Update the completion time in Habit.sav
                int index = -1;
                for (Habit habit : habitArrayList) {
                    habit.print();
                    if (habit.equals(detailHabit)) {
                        index = habitArrayList.indexOf(habit);
                        break;
                    }
                }
                detailHabit.addCompletedTime();
                habitArrayList.set(index, detailHabit);
                saveInFile(FILENAME, habitArrayList);
                detailCompletionsDisplay.setText(String.valueOf(detailHabit.getCompletedTime()));

                // Update the history
                historyArrayList.add(0, copy(detailHabit));
                saveInFile(HISTORYFILE, historyArrayList);

                // Visual Indication
                // Code from: https://www.mkyong.com/android/android-toast-example/
                Toast.makeText(getApplicationContext(), "Completed!!! +1", Toast.LENGTH_SHORT).show();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Delete the the object in the Habit.sav
                for (Habit habit : habitArrayList) {
                    if (habit.equals(detailHabit)) {
                        habitArrayList.remove(habit);
                        break;
                    }
                }
                saveInFile(FILENAME, habitArrayList);

                // Delete the object from the History.sav
                detailHabit.print();
                ArrayList<Habit> deleteHistoryArrayList = new ArrayList<Habit>();
                for (Habit history : historyArrayList) {
                    history.print();
                    if (history.isSameHabit(detailHabit)) {
                        deleteHistoryArrayList.add(history);
                    }
                }
                for (Habit deleteHabit : deleteHistoryArrayList) {
                    historyArrayList.remove(deleteHabit);
                }
                saveInFile(HISTORYFILE, historyArrayList);

                // Visual Indication
                // Code from: https://www.mkyong.com/android/android-toast-example/
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Load Habit.sav
        habitArrayList = loadFromFile(FILENAME);
        // Load Detail.sav
        clickedhabitArrayList = loadFromFile(DETAILFILE);
        // Load History.sav
        historyArrayList = loadFromFile(HISTORYFILE);

        // Update the View
        detailHabit = clickedhabitArrayList.get(0);
        detailNameDisplay.setText(detailHabit.getTitle());
        detailDateDisplay.setText(dateToString(detailHabit.getDate()));
        detailRepeatDisplay.setText(repeatToString(detailHabit));
        detailCompletionsDisplay.setText(String.valueOf(detailHabit.getCompletedTime()));
    }


    // Use the code from Lab3 LonelyTwitterActivity.java
    // Load Gson file to array list
    private ArrayList<Habit> loadFromFile(final String fileName) {
        ArrayList<Habit> habitArray = new ArrayList<Habit>();
        try {
            FileInputStream fis = openFileInput(fileName);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            // Code from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            Type typeHabit = new TypeToken<ArrayList<Habit>>(){}.getType();

            habitArray = gson.fromJson(in, typeHabit);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            saveInFile(fileName, new ArrayList<Habit>());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
        return habitArray;
    }


    // Use the code from Lab3 LonelyTwitterActivity.java
    // Store array list in Gson file
    private void saveInFile(final String fileName, ArrayList<Habit> habitArray) {
        try {
            FileOutputStream fos = openFileOutput(fileName, 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(habitArray, out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

    // Helper function - turn date to String
    private String dateToString (Calendar date) {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        return year + " - " + month + " - " + day;
    }

    // Helper function - turn repeat in[] to String
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

    // Helper function - get a copy of a habit
    public Habit copy (Habit habit) {
        Habit aCopy = new Habit(habit.getTitle(), habit.getRepeat(), habit.getCompletedTime(), habit.getDate());
        return aCopy;
    }
}
