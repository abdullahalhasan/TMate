package com.abdullahalhasan.bindastourmate;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    static ListView eventView;
    Event event;
    int eventID;
    EventManager eventManager;
    EventAdapter adapter;
    Dialog dialog;
    EditText addLocationET;
    EditText addStartFromET;
    EditText addEndToET;
    EditText addBudgetET;
    Button saveBT;
    Button cancelBT;
    Button deleteBT;
    Button updateBT;
    Event updateEvent;

    ArrayList<Event> newEventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        eventView = (ListView) findViewById(R.id.eventListView);

        eventManager = new EventManager(this);

        showEventList();

        eventView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Event newEvent = newEventList.get(position);

                Intent momentIntent = new Intent(getApplicationContext(), AddMoment.class);
                momentIntent.putExtra("EventID", newEvent.getId());
                startActivity(momentIntent);
                finish();
            }
        });

        eventView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {

                updateEvent = newEventList.get(position);

                updateEventDialog();

                return true;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addEventDialogBox();

//                Intent addEventIntent = new Intent(getApplicationContext(),AddEvent.class);
//                startActivity(addEventIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_profile) {
            return true;
        } else if (id == R.id.action_about) {
            return true;
        } else if (id == R.id.action_logout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showEventList() {

        newEventList = eventManager.getAllEvents();
        adapter = new EventAdapter(this, newEventList);
        eventView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void addEventDialogBox() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_add_event);
        dialog.setCancelable(false);
        dialog.setTitle("Add Your Event");
        init();

        saveBT = (Button) dialog.findViewById(R.id.saveBT);
        cancelBT = (Button) dialog.findViewById(R.id.cancelBT);

        dialog.show();

        saveBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewEvent();
                dialog.dismiss();
                showEventList();

            }
        });

        cancelBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }

    private void init() {

        addLocationET = (EditText) dialog.findViewById(R.id.addLocationET);
        addStartFromET = (EditText) dialog.findViewById(R.id.addStartFromET);
        addEndToET = (EditText) dialog.findViewById(R.id.addEndToET);
        addBudgetET = (EditText) dialog.findViewById(R.id.addBudgetET);

        newEventList = new ArrayList();
        eventManager = new EventManager(this);

    }

    public void addNewEvent() {
        String locationName = addLocationET.getText().toString().trim();
        String startingDate = addStartFromET.getText().toString().trim();
        String endingDate = addEndToET.getText().toString().trim();
        String budget = addBudgetET.getText().toString().trim();

        if (locationName.equals("") || startingDate.equals("") || endingDate.equals("") || budget.equals("")) {
            Toast.makeText(Home.this, "Empty Field", Toast.LENGTH_SHORT).show();
            return;
        }

        if (locationName.length() > 20) {
            Toast.makeText(Home.this, "Too Long!!", Toast.LENGTH_SHORT).show();
            return;
        }

        event = new Event(locationName, startingDate, endingDate, budget);

        boolean inserted = eventManager.addEvent(event);

        if (inserted) {
            Toast.makeText(Home.this, "Successfully Save Event !!!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Home.this, "Can't Save Event !!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateEventDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_update_event);
        dialog.setCancelable(false);
        dialog.setTitle("Update Your Event");

        updateInit();

        dialog.show();

        deleteBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
                dialog.dismiss();
                showEventList();
            }
        });

        updateBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
                dialog.dismiss();
                showEventList();
            }
        });

    }

    private void updateInit() {
        addLocationET = (EditText) dialog.findViewById(R.id.addLocationET);
        addStartFromET = (EditText) dialog.findViewById(R.id.addStartFromET);
        addEndToET = (EditText) dialog.findViewById(R.id.addEndToET);
        addBudgetET = (EditText) dialog.findViewById(R.id.addBudgetET);
        updateBT = (Button) dialog.findViewById(R.id.updateBT);
        deleteBT = (Button) dialog.findViewById(R.id.deleteBT);
        eventManager = new EventManager(this);
        eventID = updateEvent.getId();
        event = eventManager.getEvent(eventID);

        if (eventID > -1) {

            addLocationET.setText(event.getLocationName());
            addStartFromET.setText(event.getStartingDate());
            addEndToET.setText(event.getEndigDate());
            addBudgetET.setText(event.getBudget());
        }
    }

    public void delete() {

        boolean deleted = eventManager.deleteEvent(eventID);

        if (deleted) {
            Toast.makeText(Home.this, "Event Deleted Successfully!!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Home.this, "Event Can't Deleted !!", Toast.LENGTH_SHORT).show();
        }

        Intent eventIntent = new Intent(getApplicationContext(), Home.class);
        startActivity(eventIntent);
        finish();
    }

    public void update() {

        boolean updated = eventManager.updateEvent(eventID, event);

        if (updated) {
            Toast.makeText(Home.this, "Event Update Successfully!!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Home.this, "Event Can't Deleted !!", Toast.LENGTH_SHORT).show();
        }

        Intent eventIntent = new Intent(getApplicationContext(), Home.class);
        startActivity(eventIntent);
        finish();
    }


}
