package org.TravelManager.core.database.model;

import jakarta.persistence.*;

import java.util.Set;

/**
 * A class that represents a database model of the plan of events. Can be persisted using Hibernate.
 */
@Entity
@Table(name = "plans")
public class PlanModel {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id")
    Integer id;
    @Column(name="name", unique = true)
    String name;

    @OneToMany(mappedBy = "plan")
    Set<ControllerModel> controllerModels;

    /**
     * Empty constructor
     */
    public PlanModel() { };

    /**
     * Normal constructor that lets you set the name of the plan. Name has to be unique.
     * @param name The name of the plan that can be used for identification. <b>Has to be unique</b>.
     */
    public PlanModel(String name){
        this.name = name;
    }

    /**
     * Returns the primary key of the plan.
     * @return The primary key.
     */
    public int getId() {
        return id;
    }

    /**
     * Set the primary key of the plan model.
     * @param id The primary key to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of the plan.
     * @return Name of the model.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the plan. <b>Name has to be unique</b>.
     * @param name The name to be set for the plan.
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Get the controllers the plan consists of.
     * @return The set of ControllerModels.
     */
    public Set<ControllerModel> getControllerModels() {
        return controllerModels;
    }

    /**
     * Set the controllers of the plan.
     * @param controllerModels The set of ControllerModels, can be empty.
     */
    public void setControllerModels(Set<ControllerModel> controllerModels) {
        this.controllerModels = controllerModels;
    }
}
