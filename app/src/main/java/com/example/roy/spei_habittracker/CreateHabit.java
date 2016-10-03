package com.example.roy.spei_habittracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
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

public class CreateHabit extends AppCompatActivity {

    private static final String FILENAME = "Habit.sav";

    private ArrayList<Habit> habitArrayList = new ArrayList<Habit>();

    private EditText editTitle;
    private Button addButton, cancelButton, dateButton;
    private CheckBox mon, tue, wed, thu, fri, sat, sun;
    private Calendar inputDate;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_habit);

        editTitle = (EditText) findViewById(R.id.TitleEditText);
        dateButton = (Button) findViewById(R.id.datebutton);
        addButton = (Button) findViewById(R.id.createbutton);

        mon = (CheckBox) findViewById(R.id.MoncheckBox);
        tue = (CheckBox) findViewById(R.id.TuecheckBox);
        wed = (CheckBox) findViewById(R.id.WedcheckBox);
        thu = (CheckBox) findViewById(R.id.ThucheckBox);
        fri = (CheckBox) findViewById(R.id.FricheckBox);
        sat = (CheckBox) findViewById(R.id.SatcheckBox);
        sun = (CheckBox) findViewById(R.id.SuncheckBox);
        final CheckBox[] checkBoxArray = {mon, tue, wed, thu, fri, sat, sun};

        // Get the year, month, day from date
        // Learn code from: http://stackoverflow.com/questions/2654025/how-to-get-year-month-day-hours-minutes-seconds-and-milliseconds-of-the-cur
        inputDate = Calendar.getInstance();
        year = inputDate.get(Calendar.YEAR);
        month = inputDate.get(Calendar.MONTH);
        day = inputDate.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);


        // Use code from: http://www.tutorialspoint.com/android/android_datepicker_control.htm
        dateButton = (Button) findViewById(R.id.datebutton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(999);
            }
        });

        // Add button - finish edit and start to store in file
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editTitle.getText().toString();

                // Get the repeat
                int[] repeat = new int[7];   // Set up the int[] repeat
                for (int i=0; i<7; i++) {
                    if ((checkBoxArray[i]).isChecked()){
                        repeat[i] = 1;
                    }
                }

                Habit newHabit = new Habit(text, repeat, 0, inputDate);
                habitArrayList.add(newHabit);
                saveInFile(FILENAME, habitArrayList);

                // Code from: https://www.mkyong.com/android/android-toast-example/
                Toast.makeText(getApplicationContext(), "New Habit Added", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        // Cancel button will go back to previous activity
        cancelButton = (Button) findViewById(R.id.cancelbutton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Get the habitArrayList from Habit.sav
        habitArrayList = loadFromFile(FILENAME);
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



    // Deal with a Dialog
    // tutorialspoint
    // Use code from: http://www.tutorialspoint.com/android/android_datepicker_control.htm
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    // Get the date
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            inputDate.set(arg1, arg2, arg3);
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        dateButton.setText(new StringBuilder().append(year).append(" - ").append(month).append(" - ").append(day));
        //Log.d("DDD", "AAA");
    }


}