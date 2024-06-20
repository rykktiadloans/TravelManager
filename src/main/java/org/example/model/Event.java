package org.example.model;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * This class represents a simple bundle of data that comprise an event.
 */
public class Event {
    private String name;
    private LocalTime start;
    private LocalTime end;

    /**
     * The constructor of the class. <b>Event's start has to be before its end.</b>
     * @param name Name of the event.
     * @param start Start of the event.
     * @param end End of the event, <b>has to happen after start.</b>
     */
    public Event(String name, LocalTime start, LocalTime end){
        this.name = name;
        this.start = start;
        this.end = end;
        if(this.start.isAfter(this.end)) {
            throw new DateTimeException("Event ends before it starts");
        }
    }

    /**
     * Returns a string representation of the event.
     * @return "name start end"
     */
    public String getOverview(){
        String s = String.format("%s %s %s", this.name, this.start.format(DateTimeFormatter.ISO_LOCAL_TIME), this.end.format(DateTimeFormatter.ISO_LOCAL_TIME));
        return s;

    }

    /**
     * Return the name of the event.
     * @return Event's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the event.
     * @param name The name to be set.
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Return the timestamp when the event starts.
     * @return Event's start.
     */
    public LocalTime getStart() {
        return start;
    }

    /**
     * Sets the timestamp when the event begins. <b>Automatically checks that the new start happens before the event's end</b>.
     * @param start The timestamp to be set.
     */
    public void setStart(LocalTime start) {
        if(start.isAfter(this.end)) {
            throw new DateTimeException("Event ends before it starts");
        }
        else{
            this.start = start;
        }
    }

    /**
     * Returns the end of the event.
     * @return Event's end.
     */
    public LocalTime getEnd() {
        return end;
    }

    /**
     * Sets the timestamp when the event ends. <b>Automatically checks that the new end happens after the event's start</b>.
     * @param end The timestamp to be set.
     */
    public void setEnd(LocalTime end) {
        if(this.start.isAfter(end)) {
            throw new DateTimeException("Event ends before it starts");
        }
        else{
            this.end = end;
        }
    }
}
