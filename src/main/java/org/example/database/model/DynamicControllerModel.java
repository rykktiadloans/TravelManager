package org.example.database.model;

import jakarta.persistence.*;
import org.example.controller.DynamicEventController;
import org.example.controller.EventController;
import org.example.model.Event;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static jakarta.persistence.GenerationType.AUTO;

@Entity
@Table(name = "dynamic_controllers")
public class DynamicControllerModel extends ControllerModel{
    @Column(name = "distance")
    Float distance;
    public DynamicControllerModel(){}

    public DynamicControllerModel(PlanModel planId, String name, String begin, float distance, String subtype){
        super(planId, name, begin, subtype);
        this.distance = distance;
    }

    /*
    CREATE TABLE normal_controllers(
        id INT PRIMARY KEY NOT NULL,
        name TEXT NOT NULL,
        begin TEXT NOT NULL,
        end TEXT NOT NULL,
        subtype TEXT NOT NULL
    );
     */


    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    @Override
    public EventController unmodelize(){
        Event event = new Event(this.name, LocalTime.parse(this.getBegin()), LocalTime.parse(this.getBegin()));
        DynamicEventController dynamicEventController = new DynamicEventController(event, this.getDistance());
        dynamicEventController.setSelectedType(dynamicEventController.getSubtypes().indexOf(this.getSubtype()));
        return dynamicEventController;
    }
}
