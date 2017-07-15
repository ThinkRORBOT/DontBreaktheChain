package com.example.ming.dontbreakthechain;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

public class HabitOverviewAdapter extends BaseAdapter {
    String [] name;
    String [] overview;
    Context context;
    Integer [] progress_c;
    Integer [] progress_goal;
    float [] price;
    private static LayoutInflater inflater=null;
    //Constructors to initialise data depending on the activity that called it
    public HabitOverviewAdapter(MainActivity mainActivity, String[] name, String[] overview, Integer[] progress_c, Integer[] progress_goal) {

        result=products;
        context= essentialProductsActivity;
        price = price_arr;
        imageId=productImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
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
        TextView tv;
        TextView price;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        //new holder object
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.products_list, null);
        //sets the attributes of holder to values in array
        holder.tv=(TextView) rowView.findViewById(R.id.productTitle);
        holder.img=(ImageView) rowView.findViewById(R.id.productImage);
        holder.price=(TextView) rowView.findViewById(R.id.productPrice);
        holder.tv.setText(result[position]);
        holder.img.setImageResource(imageId[position]);
        //for when the price is implemented
        //holder.price.setText(String.valueOf(price[position]));

        return rowView;
    }

}