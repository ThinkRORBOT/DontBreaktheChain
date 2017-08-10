package com.example.ming.dontbreakthechain;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.icu.text.DecimalFormat;
import android.preference.DialogPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_information);

        item = getIntent().getStringExtra("habit");
        int s_item = Integer.valueOf(item);

        titleText = (TextView) findViewById(R.id.titleText);
        descriptionText = (TextView) findViewById(R.id.descriptionView);
        startLabel = (TextView) findViewById(R.id.startDLabel);
        progressView = (TextView) findViewById(R.id.progressView);
        percentageView = (TextView) findViewById(R.id.percentageView);
        progressEdit = (EditText) findViewById(R.id.progressEditText);
        changeProgressLabel = (TextView) findViewById(R.id.changePLabel);
        progressImage = (ImageView) findViewById(R.id.progressImage);

        titleText.setText(MainActivity.name_arr[s_item]);
        descriptionText.setText(MainActivity.description_arr[s_item]);
        progressView.setText(MainActivity.progress_arr[s_item] + "/" + MainActivity.progress_goal[s_item]);

        float percentage = ((float) MainActivity.progress_arr[s_item] / (float) MainActivity.progress_goal[s_item]);
        double d_percentage = Math.round(percentage*100.0)/100.0;

        percentageView.setText( d_percentage + ("%"));
        progressImage.setImageResource(MainActivity.chainImage[s_item]);


    }


    public void deleteHabit(View view) {
    }

    public void exitActivity(View view) {
        if (progressEdit.getText().toString().trim().length() > 0 && !enteredText) {

        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(MoreInformationActivity.this).create();
            alertDialog.setTitle("Unsaved Changes");
            alertDialog.setMessage("There is an unsaved change in the text box, do you want to save it?");
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            setResult(RESULT_OK, null);
                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saveProgress();
                            setResult(RESULT_OK, null);
                            finish();
                        }
                    });
        }
    }

    public void addProgress(View view) {
    }

    private void saveProgress() {

    }

    public void addHabit(View view) {
        if (progressEdit.getText().toString().trim().length() > 0) {

        } else {
            Toast.makeText(this, "Enter new date", Toast.LENGTH_SHORT).show();
        }
    }
}
