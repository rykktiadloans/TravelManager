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

/**
 * The class that represents a persistable version of the NormalEventController. Extends the ControllerModel class and is actually
 * the one that is saved on the disk.
 */
@Entity
@Table(name = "normal_controllers")
public class NormalControllerModel extends ControllerModel{
    @Column(name = "end")
    String end;

    /**
     * Empty constructor.
     */
    public NormalControllerModel(){}

    /**
     * Full constructor.
     * @param plan The plan model the controller is attached to.
     * @param name The name of the event.
     * @param begin The beginning of the event.
     * @param end The end of the event.
     * @param subtype The selected subtype of the event.
     */
    public NormalControllerModel(PlanModel plan, String name, String begin, String end, String subtype){
        super(plan, name, begin, subtype);
        this.end = end;
    }

    /**
     * Return the end of the event.
     * @return The end of the event.
     */
    public String getEnd() {
        return end;
    }

    /**
     * Set the end of the event.
     * @param end The end of the event.
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     * Returns an EventController unpersistable version of itself. Finds the appropriate EventType using the selected type.
     * @return The unpersistable version of itself.
     * @throws Exception Throws an exception in case the selected type somehow isn't found in any of the EventTypes.
     */
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
