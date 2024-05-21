package com.example.calendarproject;

import lombok.Data;

@Data
public class Events {
    String EVENT,TIME,DATE,MONTH,YEAR;

    public Events(String EVENT, String TIME, String DATE, String MONTH, String YEAR) {
        this.EVENT = EVENT;
        this.TIME = TIME;
        this.DATE = DATE;
        this.MONTH = MONTH;
        this.YEAR = YEAR;
    }
}
