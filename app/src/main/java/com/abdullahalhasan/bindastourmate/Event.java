package com.abdullahalhasan.bindastourmate;

import java.util.Date;

/**
 * Created by Abdullah Al Hasan on 8/19/2016.
 */
public class Event {
    private int id;
    private String locationName;
    private String startingDate;
    private String endigDate;
    private String budget;

    public Event(int id,String locationName, String startingDate, String endigDate, String budget) {
        this.id = id;
        this.locationName = locationName;
        this.startingDate = startingDate;
        this.endigDate = endigDate;
        this.budget = budget;
    }

    public Event(String locationName, String startingDate, String endigDate, String budget) {
        this.locationName = locationName;
        this.startingDate = startingDate;
        this.endigDate = endigDate;
        this.budget = budget;
    }

    public Event(String locationName, String budget) {
        this.locationName = locationName;
        this.budget = budget;
    }

    public int getId() {
        return id;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public String getEndigDate() {
        return endigDate;
    }

    public String getBudget() {
        return budget;
    }
}
