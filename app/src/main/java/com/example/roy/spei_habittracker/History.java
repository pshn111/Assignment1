package com.example.roy.spei_habittracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

public class History extends AppCompatActivity {

    private static final String FILENAME = "Habit.sav";
    private static final String DETAILFILE = "Detail.sav";

    private ArrayList<Habit> habitArrayList = new ArrayList<Habit>();
    private ArrayList<Habit> clickedhabitArrayList = new ArrayList<Habit>();
    public Habit habitClicked;

    ListView viewAllListView;
    private ArrayAdapter<Habit> allViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        viewAllListView = (ListView) findViewById(R.id.ViewAllListView);

        // If click on an item on the ListView --> Go to DetailActivity
        viewAllListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Learn getItemAtPosition() from:
                // http://stackoverflow.com/questions/21295328/android-listview-with-onclick-items
                // https://developer.android.com/reference/android/widget/AdapterView.html#getItemAtPosition(int)
                habitClicked = (Habit) adapterView.getItemAtPosition(position);

                // Find which item users click
                clickedhabitArrayList.add(habitClicked);
                saveInFile(DETAILFILE, clickedhabitArrayList);

                // Learn code from android developer website
                // https://developer.android.com/training/basics/firstapp/starting-activity.html
                // Go to DetailActivity
                Intent goDetailIntent = new Intent(History.this, Detail.class);
                startActivity(goDetailIntent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Load file for each time users start the activity
        // Load habit.sav to habitArrayList
        habitArrayList = loadFromFile(FILENAME);
        clickedhabitArrayList.clear();
        // Display the ListView on resume
        allViewAdapter =
                new ArrayAdapter<Habit>(this,R.layout.list_item,habitArrayList);
        viewAllListView.setAdapter(allViewAdapter);
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
            Type typehabit = new TypeToken<ArrayList<Habit>>(){}.getType();

            habitArray = gson.fromJson(in, typehabit);

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
