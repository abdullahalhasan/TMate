package com.abdullahalhasan.bindastourmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddEvent extends AppCompatActivity {

    EditText addLocationET;
    EditText addStartFromET;
    EditText addEndToET;
    EditText addBudgetET;

    EventManager eventManager;
    Event event;
    ArrayList<Event> newEventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        addLocationET = (EditText) findViewById(R.id.addLocationET);
        addStartFromET = (EditText) findViewById(R.id.addStartFromET);
        addEndToET = (EditText) findViewById(R.id.addEndToET);
        addBudgetET = (EditText) findViewById(R.id.addBudgetET);

        newEventList = new ArrayList();
        eventManager = new EventManager(this);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent eventIntent = new Intent(getApplicationContext(),Home.class);
        startActivity(eventIntent);
        finish();
    }

    public void save(View view) {

        addNewEvent();

        Intent eventIntent = new Intent(getApplicationContext(),Home.class);
//        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(eventIntent);
        finish();
    }

    public void cancel(View view) {
        onBackPressed();
    }

    public void addNewEvent() {
        String locationName = addLocationET.getText().toString().trim();
        String startingDate = addStartFromET.getText().toString().trim();
        String endingDate = addEndToET.getText().toString().trim();
        String budget = addBudgetET.getText().toString().trim();

        if (locationName.equals("") || startingDate.equals("") || endingDate.equals("") || budget.equals("")) {
            Toast.makeText(AddEvent.this, "Empty Field !!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (locationName.length()>20) {
            Toast.makeText(AddEvent.this, "Too much long !!", Toast.LENGTH_SHORT).show();
            return;
        }

        event = new Event(locationName,startingDate,endingDate,budget);

        boolean inserted = eventManager.addEvent(event);

        if (inserted) {
            Toast.makeText(AddEvent.this, "Event Saved Successfully!!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AddEvent.this, "Event Can't Saved !!", Toast.LENGTH_SHORT).show();
        }
    }

}