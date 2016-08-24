package com.abdullahalhasan.bindastourmate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Abdullah Al Hasan on 8/19/2016.
 */
public class EventManager {
    private Event event;
    private DatabaseHelper helper;
    private SQLiteDatabase database;
    private Context context;

    public EventManager(Context context) {
        this.context = context;
        helper = new DatabaseHelper(context);
    }

    private void open() {
        database = helper.getWritableDatabase();
    }

    private void close() {
        helper.close();
    }

    public boolean addEvent(Event event) {
        this.open();

        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseHelper.LOCATION_NAME_COLUMN,event.getLocationName());
        contentValues.put(DatabaseHelper.START_DATE_COLUMN,event.getStartingDate());
        contentValues.put(DatabaseHelper.END_DATE_COLUMN,event.getEndigDate());
        contentValues.put(DatabaseHelper.BUDGET_COLUMN,event.getBudget());

        long inserted = database.insert(DatabaseHelper.TABLE_EVENT,null,contentValues);

        this.close();

        if (inserted>0) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Event> getAllEvents() {
        this.open();

        ArrayList<Event> eventList = new ArrayList();
        Cursor cursor = database.query(DatabaseHelper.TABLE_EVENT,null,null,null,null,null,null);

        if (cursor!=null && cursor.getCount()>0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ID_COLUMN));
                String locationName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOCATION_NAME_COLUMN));
                String startingDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.START_DATE_COLUMN));
                String endingDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.END_DATE_COLUMN));
                String budget = cursor.getString(cursor.getColumnIndex(DatabaseHelper.BUDGET_COLUMN));

                Event event = new Event(id,locationName,startingDate,endingDate,budget);
                eventList.add(event);
                cursor.moveToNext();
            }
            this.close();
        }
        return eventList;
    }

    public Event getEvent(int id) {
        this.open();

        Cursor cursor = database.query(DatabaseHelper.TABLE_EVENT,new String[] {DatabaseHelper.ID_COLUMN,DatabaseHelper.LOCATION_NAME_COLUMN,
        DatabaseHelper.START_DATE_COLUMN,DatabaseHelper.END_DATE_COLUMN,DatabaseHelper.BUDGET_COLUMN},
                DatabaseHelper.ID_COLUMN+" = "+id,null,null,null,null);
        cursor.moveToFirst();

        int eventID = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ID_COLUMN));
        String locationName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOCATION_NAME_COLUMN));
        String startingDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.START_DATE_COLUMN));
        String endingDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.END_DATE_COLUMN));
        String budget = cursor.getString(cursor.getColumnIndex(DatabaseHelper.BUDGET_COLUMN));

        event = new Event(eventID,locationName,startingDate,endingDate,budget);

        this.close();
        return event;
    }

    public boolean updateEvent(int id, Event event) {
        this.open();

        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseHelper.LOCATION_NAME_COLUMN,event.getLocationName());
        contentValues.put(DatabaseHelper.START_DATE_COLUMN,event.getStartingDate());
        contentValues.put(DatabaseHelper.END_DATE_COLUMN,event.getEndigDate());
        contentValues.put(DatabaseHelper.BUDGET_COLUMN,event.getBudget());
        String[] newID = {Integer.toString(id)};
        int updated = database.update(DatabaseHelper.TABLE_EVENT,contentValues,DatabaseHelper.ID_COLUMN+" = ?",newID);

        this.close();

        if (updated>0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteEvent(int id) {
        this.open();

        int deleted = database.delete(DatabaseHelper.TABLE_EVENT,DatabaseHelper.ID_COLUMN+" = "+id,null);

        this.close();

        if (deleted>0) {
            return true;
        } else {
            return false;
        }
    }


}
