package org.example.database.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "plans")
public class PlanModel {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;

    @OneToMany(mappedBy = "controller_id")
    private Set<ControllerModel> controllerModels;
    public PlanModel() { };
    public PlanModel(String name){
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ControllerModel> getControllerModels() {
        return controllerModels;
    }

    public void setControllerModels(Set<ControllerModel> controllerModels) {
        this.controllerModels = controllerModels;
    }
}
