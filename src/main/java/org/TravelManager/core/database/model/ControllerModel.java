package org.TravelManager.core.database.model;

import jakarta.persistence.*;
import org.TravelManager.core.controller.EventController;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.SEQUENCE;

/**
 * The class that represents the persistable version of the event controller. Isn't actually used for saving events on the disk,
 * but is used as a parent class for other persistable model classes.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ControllerModel {
    @Id @GeneratedValue(strategy = SEQUENCE)
    @Column(name = "id")
    Integer id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="plan_id", nullable = false)
    PlanModel plan;
    @Column(name = "name")
    String name;
    @Column(name = "begin")
    String begin;
    @Column(name = "subtype")
    String subtype;

    /**
     * Empty constructor.
     */
    public ControllerModel() { }

    /**
     * A constructor that allows us to set all the parts of the model.
     * @param plan The plan the model is a part of.
     * @param name The name of the controller.
     * @param begin The beginning of the event.
     * @param subtype The selected subtype of the controller.
     */
    public ControllerModel(PlanModel plan, String name, String begin, String subtype){
        this.plan = plan;
        this.name = name;
        this.begin = begin;
        this.subtype = subtype;
    }

    /**
     * Return the id of the persistent object.
     * @return The primary key.
     */
    public int getId() {
        return id;
    }

    /**
     * Set the primary key of the object.
     * @param id The primary key to be set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Return the plan model the controller is connected to.
     * @return Plan model.
     */
    public PlanModel getPlan() {
        return plan;
    }

    /**
     * Set the plan model the controller is attached to.
     * @param plan The plan model.
     */
    public void setPlan(PlanModel plan) {
        this.plan = plan;
    }

    /**
     * Return the name of the event.
     * @return Name of the event.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the event.
     * @param name The name of the event.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the beginning of the event.
     * @return The beginning of the event.
     */
    public String getBegin() {
        return begin;
    }

    /**
     * Set the beginning of the event.
     * @param begin The beginning of the event.
     */
    public void setBegin(String begin) {
        this.begin = begin;
    }

    /**
     * Return the selected subtype of the controller.
     * @return The selected subtype.
     */
    public String getSubtype() {
        return subtype;
    }

    /**
     * Set the selected subtype of the controller.
     * @param subtype The selected subtype.
     */
    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    /**
     * In classes that inherit this one it should return a non-persistable version of the controller.
     * Throws an exception if this one gets called.
     */
    public EventController unmodelize() throws Exception {
        throw new Exception("Normal controller models are serialized");
    }

}
