package com.example.ming.dontbreakthechain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

public class NewHabitActivity extends AppCompatActivity {

    private TextView titleTextView;
    private TextView habitNameTextView;
    private TextView descriptionTextView;
    private TextView goalTextView;
    private Button saveButton;
    private EditText habitNameEditText;
    private EditText descriptionEditText;
    private EditText goalEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_habit);

        titleTextView = (TextView) findViewById(R.id.titleTextView);
        habitNameTextView = (TextView) findViewById(R.id.habitNameTextView);
        descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
        goalTextView = (TextView) findViewById(R.id.goalTextView);
        saveButton = (Button) findViewById(R.id.saveButton);
        habitNameEditText = (EditText) findViewById(R.id.habitNameEditText);
        descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);
        goalEditText = (EditText) findViewById(R.id.dateEditText);
    }

    private boolean fieldNotEmpty() {
        if (habitNameEditText.getText().toString().trim().length() <= 0){
            Toast.makeText(getApplicationContext(), "Name Field is Empty.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (descriptionEditText.getText().toString().trim().length() <= 0){
            Toast.makeText(getApplicationContext(), "Description Field is Empty.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (goalEditText.getText().toString().trim().length() <= 0){
            Toast.makeText(getApplicationContext(), "Goal Field is Empty.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void newHabit(View view) {
        if (fieldNotEmpty()) {
            File dir = new File(getFilesDir()+"/dontbreakthechain");

            if(!dir.exists()) {
                dir.mkdir();
                Log.d("directory", "made");
            }

            File file = new File(dir.getAbsolutePath() + "/habits.txt");

            if(!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
                output.append(habitNameEditText.getText().toString().trim() + "<|>" + descriptionEditText.getText().toString().trim() + "<|>" + '0' + "<|>" + goalEditText.getText().toString().trim() + "/n");
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent);
        finish();

    }
}
