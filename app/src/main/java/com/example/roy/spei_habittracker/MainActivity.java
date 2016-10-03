package com.example.roy.spei_habittracker;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "Habit.sav";
    private static final String HISTORYFILE = "History.sav";

    private ArrayList<Habit> historyArrayList = new ArrayList<Habit>();
    private ArrayList<Habit> habitArrayList = new ArrayList<Habit>();

    private Habit habitClicked;

    private ListView completedView;
    private ArrayAdapter<Habit> completedViewAdapter;
    private Button CreateB, HistoryB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        completedView = (ListView) findViewById(R.id.listView);

        // Add Create Button
        CreateB = (Button) findViewById(R.id.CreateButton);
        CreateB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Learn code from android developer website
                // https://developer.android.com/training/basics/firstapp/starting-activity.html
                // Go to CreatehabitActivity
                Intent goCreateHabitIntent = new Intent(MainActivity.this, CreateHabit.class);
                startActivity(goCreateHabitIntent);
            }
        });

        // History Button.  Fatal Exception(java.lang.IllegalStateException: ArrayAdapter requires the resource ID to be a TextView)
        HistoryB = (Button) findViewById(R.id.HistoryButton);
        HistoryB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go to History
                Intent goHistoryIntent = new Intent(MainActivity.this, History.class);
                startActivity(goHistoryIntent);
            }
        });

        // You need long click to delete one completion
        // Learn code from: http://stackoverflow.com/questions/8846707/how-to-implement-a-long-click-listener-on-a-listview
        completedView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Delete the object from the History.sav
                habitClicked = (Habit) adapterView.getItemAtPosition(position);

                // Delete completion history in History.sav
                for (Habit history : historyArrayList) {
                    if (history.equals(habitClicked)) {
                        historyArrayList.remove(habitClicked);
                        break;
                    }
                }
                // -1 for completion time in habit.sav
                for (Habit history : habitArrayList) {
                    if (history.isSameHabit(habitClicked)) {
                        history.deleteCompletedTime();
                        break;
                    }
                }
                // Update those files
                saveInFile(FILENAME, habitArrayList);
                saveInFile(HISTORYFILE, historyArrayList);

                // Update the ListView
                completedViewAdapter.notifyDataSetChanged();
                // Code from: https://www.mkyong.com/android/android-toast-example/
                Toast.makeText(getApplicationContext(), "Delete Completion", Toast.LENGTH_SHORT).show();
                return false;
            }
        });





    }



    @Override
    protected void onResume() {
        super.onResume();


        // Learn the code from Lab3 LonelyTwitterActivity.java

        // Load file for each time users start the activity
        // Load habit.sav to habitArrayList
        habitArrayList = loadFromFile(FILENAME);
        // Load History.sav to historyArrayList
        historyArrayList = loadFromFile(HISTORYFILE);

        // Display the ListView on resume
        completedViewAdapter =
                new ArrayAdapter<Habit>(this,R.layout.list_item,historyArrayList);
        completedView.setAdapter(completedViewAdapter);

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







}
