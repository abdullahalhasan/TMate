package com.abdullahalhasan.bindastourmate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Abdullah Al Hasan on 8/19/2016.
 */
public class EventAdapter extends ArrayAdapter {
    private Event event;
    private Context context;
    private ArrayList<Event> eventList;

    public EventAdapter(Context context, ArrayList<Event> eventList) {
        super(context, R.layout.row_layout,eventList);
        this.context=context;
        this.eventList = eventList;
    }

    static class ViewHolder {
        TextView locationNameTV;
        TextView budgetTV;
        //private TextView startingDate;
        //private TextView endingDate;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_layout,null);
            viewHolder = new ViewHolder();
            viewHolder.locationNameTV = (TextView) convertView.findViewById(R.id.eventNameTV);
            viewHolder.budgetTV = (TextView) convertView.findViewById(R.id.budgetTV);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.locationNameTV.setText(eventList.get(position).getLocationName());
        String budget = String.valueOf(eventList.get(position).getBudget());
        viewHolder.budgetTV.setText(budget);

        return convertView;
    }


}
