package com.example.ming.dontbreakthechain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

        }

    }
}
