package org.TravelManager.core.database.model;

import jakarta.persistence.*;
import org.TravelManager.core.controller.DynamicEventController;
import org.TravelManager.core.controller.EventController;
import org.TravelManager.core.model.Event;

import java.time.LocalTime;

/**
 * The class that represents a persistable version of the DynamicEventController. Extends the ControllerModel class and is actually
 * the one that is saved on the disk.
 */
@Entity
@Table(name = "dynamic_controllers")
public class DynamicControllerModel extends ControllerModel{
    @Column(name = "distance")
    Float distance;

    /**
     * Empty constructor.
     */
    public DynamicControllerModel(){}

    /**
     * Full contructor
     * @param planId The plan model the controller is attached to.
     * @param name The name of the event.
     * @param begin The beginning of the event.
     * @param distance The distance of the travel.
     * @param subtype The selected subtype of the event.
     */
    public DynamicControllerModel(PlanModel planId, String name, String begin, float distance, String subtype){
        super(planId, name, begin, subtype);
        this.distance = distance;
    }


    /**
     * Returns the distance of the travel.
     * @return The distance.
     */
    public float getDistance() {
        return distance;
    }

    /**
     * Sets the distance of the travel.
     * @param distance The distance of the travel to be set.
     */
    public void setDistance(float distance) {
        this.distance = distance;
    }

    /**
     * Returns an unpersistable DynamicEventController version of itself, upcast to EventController.
     * @return DynamicEventController version of itself.
     */
    @Override
    public EventController unmodelize(){
        Event event = new Event(this.name, LocalTime.parse(this.getBegin()), LocalTime.parse(this.getBegin()));
        DynamicEventController dynamicEventController = new DynamicEventController(event, this.getDistance());
        dynamicEventController.setSelectedType(dynamicEventController.getSubtypes().indexOf(this.getSubtype()));
        return dynamicEventController;
    }
}
