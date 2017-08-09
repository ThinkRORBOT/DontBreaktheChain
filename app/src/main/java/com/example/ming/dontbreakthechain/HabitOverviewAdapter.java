package com.example.ming.dontbreakthechain;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HabitOverviewAdapter extends BaseAdapter {
    String [] name;
    String [] overview;
    Context context;
    Integer [] progress_c;
    Integer [] progress_goal;
    int [] habitImage;
    private static LayoutInflater inflater=null;
    //Constructors to initialise data depending on the activity that called it
    public HabitOverviewAdapter(MainActivity mainActivity, String[] name_i, String[] overview_i, Integer[] progress_c_i, Integer[] progress_goal_i, int[] habit_image) {

        name=name_i;
        context= mainActivity;
        progress_c = progress_c_i;
        overview=overview_i;
        progress_goal=progress_goal_i;
        habitImage=habit_image;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public int getCount() {
        // TODO Auto-generated method stub
        return name.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView name;
        TextView overview;
        TextView progress;
        TextView progress_g;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        //new holder object
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.activity_habit_overview_adapter, null);
        //sets the attributes of holder to values in array
        holder.name=(TextView) rowView.findViewById(R.id.habitName);
        holder.img=(ImageView) rowView.findViewById(R.id.habitImg);
        holder.overview=(TextView) rowView.findViewById(R.id.habitOverView);
        holder.progress=(TextView) rowView.findViewById(R.id.habitProgress);
        holder.progress_g=(TextView) rowView.findViewById(R.id.habitProgressGoal);
        holder.name.setText(name[position]);
        holder.img.setImageResource(habitImage[position]);

        return rowView;
    }

}