package com.abdullahalhasan.bindastourmate;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Abdullah Al Hasan on 8/19/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    Context context;

    private static final String DATABASE_NAME = "event_db";
    private static final  int DATABASE_VERSION = 1;

    public static final String ID_COLUMN = "id";
    public static final String LOCATION_NAME_COLUMN = "location";
    public static final String START_DATE_COLUMN = "start_date";
    public static final String BUDGET_COLUMN = "budget";
    public static final String END_DATE_COLUMN = "end_date";
    public static final String TABLE_EVENT = "event_info";

    private static final String CREATE_EVENT_TABLE = "CREATE TABLE "+TABLE_EVENT+" ( "
            +ID_COLUMN+" INTEGER PRIMARY KEY,"
            +LOCATION_NAME_COLUMN+" TEXT,"
            +START_DATE_COLUMN+" TEXT,"
            +END_DATE_COLUMN+" TEXT,"
            +BUDGET_COLUMN+" TEXT )";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_EVENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
