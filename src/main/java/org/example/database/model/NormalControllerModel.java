package org.example.database.model;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.AUTO;

@Entity
@Table(name = "normal_controllers")
public class NormalControllerModel extends ControllerModel{
    @Column(name = "end")
    private String end;

    public NormalControllerModel(){}

    public NormalControllerModel(int plan, String name, String begin, String end, String subtype){
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
}
