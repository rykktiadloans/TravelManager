package org.example.database.model;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.AUTO;

@Entity
@Table(name = "dynamic_controllers")
public class DynamicControllerModel extends ControllerModel{
    @Column(name = "distance")
    private float distance;
    public DynamicControllerModel(){}

    public DynamicControllerModel(int planId, String name, String begin, float distance, String subtype){
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
}
