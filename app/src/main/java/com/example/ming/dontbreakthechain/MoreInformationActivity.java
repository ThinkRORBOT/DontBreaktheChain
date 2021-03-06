package com.example.ming.dontbreakthechain;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.DecimalFormat;
import android.preference.DialogPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static android.app.Activity.RESULT_OK;

public class MoreInformationActivity extends AppCompatActivity {

    private Button deleteButton;
    private Button okButton;
    private Button progressButton;
    private Button updateButton;
    private TextView titleText;
    private TextView descriptionText;
    private TextView startLabel;
    private TextView progressView;
    private TextView percentageView;
    private EditText progressEdit;
    private TextView changeProgressLabel;
    private ImageView progressImage;
    private String item;
    private boolean enteredText = false;
    private boolean progressAdded=false;
    private int s_item;
    private int num_added; //TODO, some logic about adding a max number of progress per day

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_information);

        item = getIntent().getStringExtra("habit");
        s_item = Integer.valueOf(item);

        titleText = (TextView) findViewById(R.id.titleText);
        descriptionText = (TextView) findViewById(R.id.descriptionView);
        startLabel = (TextView) findViewById(R.id.startDLabel);
        progressView = (TextView) findViewById(R.id.progressView);
        percentageView = (TextView) findViewById(R.id.percentageView);
        progressEdit = (EditText) findViewById(R.id.progressEditText);
        changeProgressLabel = (TextView) findViewById(R.id.changePLabel);
        progressImage = (ImageView) findViewById(R.id.progressImage);

        //initialises values for this screen
        titleText.setText(MainActivity.name_arr[s_item]);
        descriptionText.setText(MainActivity.description_arr[s_item]);
        progressView.setText(MainActivity.progress_arr[s_item] + "/" + MainActivity.progress_goal[s_item]);

        float percentage = ((float) MainActivity.progress_arr[s_item] / (float) MainActivity.progress_goal[s_item]);
        double d_percentage = Math.round(percentage*100.0);

        percentageView.setText( d_percentage + ("%"));
        progressImage.setImageResource(MainActivity.chainImage[s_item]);


    }

    //deletes the last line of the file
    private void deleteLine(File file, int lineIndex) throws IOException {
        List<String> lines = new LinkedList<>();
        //read lines into list
        Scanner reader = new Scanner(new FileInputStream(file), "UTF-8");
        while(reader.hasNextLine())
            lines.add(reader.nextLine());

        reader.close();
        //makes sure the number of lines is valid
        //assert lineIndex >= 0 && lineIndex <= lines.size() - 1 : "Line index out of range";
        lines.remove(lineIndex);
        //writes lines back into file
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
        for (String line : lines) {
           writer.write(line);
        }

        writer.flush();
        writer.close();

        setResult(RESULT_OK, null);
        finish();

    }

    //adds line to the last line of the file
    private void appendLine(File file, int lineIndex) throws IOException {
        BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
        output.write( "\n" + MainActivity.name_arr[lineIndex] + "^`" + MainActivity.description_arr[lineIndex] + "^`" +
                MainActivity.progress_arr[lineIndex] + "^`" + MainActivity.progress_goal[lineIndex]+ "^`" + "\n");
        output.close();
    }

    private void changeLine(File file, int lineIndex, int endDate) throws IOException {
        BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
        output.write("\n" +  MainActivity.name_arr[lineIndex] + "^`" + MainActivity.description_arr[lineIndex]+ "^`" +
                MainActivity.progress_arr[lineIndex] + "^`" + endDate + "^`" + "\n");
        output.close();

    }

    public void deleteHabit(View view) {
        File dir = new File(getFilesDir()+"/dontbreakthechain");
        File file = new File(dir.getAbsolutePath() + "/habits.txt");
        try {
            deleteLine(file, s_item);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exitActivity(View view) {
        //checks whether the use will want to save the changes if they want to exis activity
        if (progressEdit.getText().toString().trim().length() == 0 && !enteredText) {
            setResult(RESULT_OK, null);
            finish();

        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(MoreInformationActivity.this).create();
            alertDialog.setTitle("Unsaved Changes");
            alertDialog.setMessage("There is an unsaved change in the text box, do you want to save it?");
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setResult(RESULT_OK, null);
                            finish();

                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setResult(RESULT_OK, null);
                            finish();
                        }
                    });
        }
    }

    public void addProgress(View view) {
        if (MainActivity.progress_arr[s_item] == MainActivity.progress_goal[s_item]) {
            Toast.makeText(this, "Goal already achieved", Toast.LENGTH_LONG).show();
            return;
        }
        MainActivity.progress_arr[s_item] = MainActivity.progress_arr[s_item] + 1;

        File dir = new File(getFilesDir()+"/dontbreakthechain");
        File file = new File(dir.getAbsolutePath() + "/habits.txt");

        try {
            appendLine(file, s_item);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            deleteLine(file, s_item);
        } catch (IOException e) {
            e.printStackTrace();
        }
        progressView.setText(String.valueOf(MainActivity.progress_arr[s_item]));
        progressAdded=true;
        float percentage = ((float) MainActivity.progress_arr[s_item] / (float) MainActivity.progress_goal[s_item]);
        double d_percentage = Math.round(percentage*100.0);

        percentageView.setText( d_percentage + ("%"));
    }

    public void updateHabit(View view) {


        if ((progressEdit.getText().toString().trim().length() > 0) || progressAdded) {
            File dir = new File(getFilesDir()+"/dontbreakthechain");
            File file = new File(dir.getAbsolutePath() + "/habits.txt");

            try {
                changeLine(file, s_item, Integer.valueOf(progressEdit.getText().toString()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                deleteLine(file, s_item);
            } catch (IOException e) {
                e.printStackTrace();
            }

            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(this, "Make a change", Toast.LENGTH_SHORT).show();
        }
    }
}
