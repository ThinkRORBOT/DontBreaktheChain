package com.example.ming.dontbreakthechain;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String filename = "userData";
    private InputStream input;
    private File file;
    private ListView habitListView;
    private BufferedReader bufferedReader;
    public static String[] name_arr;
    private String[] description_arr;
    public static Integer[] progress_arr;
    public static Integer[] progress_goal;
    private float[] progress_percentage;
    public static int[] chainImage;
    private ArrayList<String> habitStoreName = new ArrayList<>();
    private ArrayList<String> habitStoreProgress = new ArrayList<>();
    private ArrayList<String> habitStoreProgressGoal = new ArrayList<>();
    private ArrayList<String> habitStoreDescription = new ArrayList<>();
    private ArrayList<Integer> habitImage = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        readFile();
        loadContents();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(getApplicationContext(), NewHabitActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void readFile(){
        File dir = new File(getFilesDir()+"/dontbreakthechain");

        if(!dir.exists()) {
            dir.mkdir();
            Log.d("directory", "made");
        }

        file = new File(dir.getAbsolutePath() + "/habits.txt");

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                messageBox("create file", e.getMessage());
            }
        }

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            messageBox("read file", e.getMessage());
        }

    }

    private void loadContents() {
        String temp = "";

        try {
            temp = bufferedReader.readLine();
        } catch (IOException e){
            e.printStackTrace();
            messageBox("read line", e.getMessage());
        }
        while (temp != null) {
            String tempArr[] = temp.split("<|>");
            habitStoreName.add(tempArr[0]);
            habitStoreDescription.add(tempArr[1]);
            habitStoreProgress.add(tempArr[2]);
            habitStoreProgressGoal.add(tempArr[3]);

            try {
                temp = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        name_arr = habitStoreName.toArray(new String[0]);
        description_arr = habitStoreDescription.toArray(new String[0]);
        progress_arr = habitStoreProgress.toArray(new Integer[0]);
        progress_goal = habitStoreProgressGoal.toArray(new Integer[0]);
        chainImage = new int[name_arr.length];

        for (int i = 0; i < progress_arr.length; i++) {
            progress_percentage[i] = ((float) progress_arr[i]/progress_goal[i])*100;
            if (progress_percentage[i] <= 0.167) {
                chainImage[i] = R.drawable.link1;
            } else if (progress_percentage[i] <= 0.333) {
                chainImage[i] = R.drawable.link2;
            } else if (progress_percentage[i] <= 0.5) {
                chainImage[i] = R.drawable.link3;
            } else if (progress_percentage[i] <= 0.667) {
                chainImage[i] = R.drawable.link4;
            } else if (progress_percentage[i] <= 0.833) {
                chainImage[i] = R.drawable.link5;
            } else {
                chainImage[i] = R.drawable.link6;
            }
        }

        habitListView = (ListView) findViewById(R.id.habitsListView);
        habitListView.setAdapter(new HabitOverviewAdapter(this, name_arr, description_arr, progress_arr, progress_goal, chainImage));

        habitListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent habitIntent = new Intent(getApplicationContext(), MoreInformationActivity.class);
                String s = Integer.toString(position + 1);
                habitIntent.putExtra("habit", s);
                startActivity(habitIntent);

            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void messageBox(String method, String message) {
        Log.d("Exception: " + method, message);

        AlertDialog.Builder messageBox = new AlertDialog.Builder(this);
        messageBox.setTitle(method);
        messageBox.setMessage(message);
        messageBox.setCancelable(false);
        messageBox.setNeutralButton("OK", null);
        messageBox.show();
    }
}
