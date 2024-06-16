package org.example.model;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Event {
    private String name;
    private LocalTime start;
    private LocalTime end;

    public Event(String name, LocalTime start, LocalTime end){
        this.name = name;
        this.start = start;
        this.end = end;
        if(this.start.isAfter(this.end)) {
            throw new DateTimeException("Event ends before it starts");
        }
    }

    public String getOverview(){
        String s = String.format("%s %s %s", this.name, this.start.format(DateTimeFormatter.ISO_LOCAL_TIME), this.end.format(DateTimeFormatter.ISO_LOCAL_TIME));
        return s;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        if(start.isAfter(this.end)) {
            throw new DateTimeException("Event ends before it starts");
        }
        else{
            this.start = start;
        }
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        if(this.start.isAfter(end)) {
            throw new DateTimeException("Event ends before it starts");
        }
        else{
            this.end = end;
        }
    }
}
