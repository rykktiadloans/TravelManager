package org.example.database.model;

import jakarta.persistence.*;
import org.example.controller.DynamicEventController;
import org.example.controller.EventController;
import org.example.controller.NormalEventController;
import org.example.model.Event;
import org.example.model.types.ConstantEvent;
import org.example.model.types.MiscEvent;

import java.time.LocalTime;

import static jakarta.persistence.GenerationType.AUTO;

@Entity
@Table(name = "normal_controllers")
public class NormalControllerModel extends ControllerModel{
    @Column(name = "end")
    String end;

    public NormalControllerModel(){}

    public NormalControllerModel(PlanModel plan, String name, String begin, String end, String subtype){
        super(plan, name, begin, subtype);
        this.end = end;
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


    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public EventController unmodelize() throws Exception {
        Event event = new Event(this.name, LocalTime.parse(this.getBegin()), LocalTime.parse(this.getEnd()));
        ConstantEvent constantEvent = new ConstantEvent();
        MiscEvent miscEvent = new MiscEvent();
        NormalEventController normalEventController = null;
        if(constantEvent.getSubtypes().contains(this.getSubtype())){
            normalEventController = new NormalEventController(event, constantEvent);
            normalEventController.setSelectedType(constantEvent.getSubtypes().indexOf(this.getSubtype()));
        }
        else if(miscEvent.getSubtypes().contains(this.getSubtype())){
            normalEventController = new NormalEventController(event, miscEvent);
            normalEventController.setSelectedType(miscEvent.getSubtypes().indexOf(this.getSubtype()));
        }
        else {
            throw new Exception("Event with an unusual subtype was saved");
        }

        return normalEventController;

    }
}
