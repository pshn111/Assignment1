package com.example.roy.spei_habittracker;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        isGetTitleCorrect();
        isGetDateCorrect();
        isGetRepeatCorrect();
        isGetCompletedTimeCorrect();
        isSetTitleCorrect();
        isSetDateCorrect();
        isSetRepeatCorrect();
        isSetCompletedTimeCorrect();
        isAddCompletedTimeCorrect();
        isDeleteCompletedTimeCorrect();
        isIsCompletedCorrect();
        isRepeatToStringCorrect();
        isToStringCorrect();
        isIsSameHabitCorrect ();
        isEqualsCorrect ();

    }

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

    public void isGetTitleCorrect() {
        int[] repeat = {1, 1, 1, 1, 1, 0, 0};
        Calendar date = Calendar.getInstance();
        Habit habit = new Habit("Test", repeat, 5, date);
        assertEquals(habit.getTitle(), "Test");
    }

    public void isGetDateCorrect() {
        int[] repeat = {1, 1, 1, 1, 1, 0, 0};
        Calendar date = Calendar.getInstance();
        Habit habit = new Habit("Test", repeat, 5, date);
        assertEquals(habit.getDate(), date);
    }

    public void isGetRepeatCorrect() {
        int[] repeat = {1, 1, 1, 1, 1, 0, 0};
        Calendar date = Calendar.getInstance();
        Habit habit = new Habit("Test", repeat, 5, date);
        assertEquals(habit.getRepeat(), repeat);
    }

    public void isGetCompletedTimeCorrect() {
        int[] repeat = {1, 1, 1, 1, 1, 0, 0};
        Calendar date = Calendar.getInstance();
        Habit habit = new Habit("Test", repeat, 5, date);
        assertEquals(habit.getCompletedTime(), 5);
    }

    public void isSetTitleCorrect() {
        int[] repeat = {1, 1, 1, 1, 1, 0, 0};
        Calendar date = Calendar.getInstance();
        Habit habit = new Habit("Test", repeat, 5, date);
        habit.setTitle("testTitle");
        assertEquals(habit.getTitle(), "testTitle");
    }

    public void isSetDateCorrect() {
        int[] repeat = {1, 1, 1, 1, 1, 0, 0};
        Calendar date = Calendar.getInstance();
        Habit habit = new Habit("Test", repeat, 5, date);
        date = Calendar.getInstance();
        habit.setDate(date);
        assertEquals(habit.getDate(), date);
    }

    public void isSetRepeatCorrect() {
        int[] repeat = {1, 1, 1, 1, 1, 0, 0};
        Calendar date = Calendar.getInstance();
        Habit habit = new Habit("Test", repeat, 5, date);
        int[] newRepeat = {0, 0, 0, 0, 0, 1, 1};
        habit.setRepeat(newRepeat);
        assertEquals(habit.getRepeat(), newRepeat);
    }

    public void isSetCompletedTimeCorrect() {
        int[] repeat = {1, 1, 1, 1, 1, 0, 0};
        Calendar date = Calendar.getInstance();
        Habit habit = new Habit("Test", repeat, 5, date);
        habit.setCompletedTime(0);
        assertEquals(habit.getCompletedTime(), 0);
    }

    public void isAddCompletedTimeCorrect() {
        int[] repeat = {1, 1, 1, 1, 1, 0, 0};
        Calendar date = Calendar.getInstance();
        Habit habit = new Habit("Test", repeat, 5, date);
        habit.addCompletedTime();
        assertEquals(habit.getCompletedTime(), 6);
    }

    public void isDeleteCompletedTimeCorrect() {
        int[] repeat = {1, 1, 1, 1, 1, 0, 0};
        Calendar date = Calendar.getInstance();
        Habit habit = new Habit("Test", repeat, 5, date);
        habit.deleteCompletedTime();
        assertEquals(habit.getCompletedTime(), 4);
    }

    public void isIsCompletedCorrect() {
        int[] repeat = {1, 1, 1, 1, 1, 0, 0};
        Calendar date = Calendar.getInstance();
        Habit habit = new Habit("Test", repeat, 1, date);
        assertEquals(habit.isCompleted(), true);

        habit.deleteCompletedTime();
        assertEquals(habit.isCompleted(), false);
    }

    private void isRepeatToStringCorrect () {
        int[] repeat = {1, 1, 1, 1, 1, 0, 0};
        Calendar date = Calendar.getInstance();
        Habit habit = new Habit("Test", repeat, 1, date);
        String target = "1 2 3 4 5 ";
        assertEquals(repeatToString(habit), target);
    }

    public void isToStringCorrect() {
        int[] repeat = {1, 1, 1, 1, 1, 0, 0};
        Calendar date = Calendar.getInstance();
        Habit habit = new Habit("Test", repeat, 1, date);

        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);

        String target = "Test" +
                "\n" + "@ " + year + " - " + month + " - " + day +
                "\n" + "Repeat: " + repeatToString(habit) +
                "\n" + "Completed " + String.valueOf(habit.getCompletedTime()) + " times.";
        assertEquals(habit.toString(), target);
    }



    protected void isIsSameHabitCorrect () {
        int[] repeat = {1, 1, 1, 1, 1, 0, 0};
        Calendar date = Calendar.getInstance();
        Habit habit = new Habit("Test", repeat, 5, date);

        //int[] repeat2 = {1, 1, 1, 1, 1, 0, 0};
        //Calendar date2 = Calendar.getInstance();
        Habit habit2 = new Habit("Test", repeat, 6, date);

        assertTrue(habit.isSameHabit(habit2));

        Habit habit3 = new Habit("Test3", repeat, 5, date);
        assertFalse(habit.isSameHabit(habit3));

    }

    //@Override
    protected void isEqualsCorrect () {
        int[] repeat = {1, 1, 1, 1, 1, 0, 0};
        Calendar date = Calendar.getInstance();
        Habit habit = new Habit("Test", repeat, 5, date);

        //int[] repeat2 = {1, 1, 1, 1, 1, 0, 0};
        //Calendar date2 = Calendar.getInstance();
        Habit habit2 = new Habit("Test", repeat, 6, date);

        assertFalse(habit.equals(habit2));

        Habit habit3 = new Habit("Test", repeat, 5, date);
        assertTrue(habit.equals(habit3));
    }
}