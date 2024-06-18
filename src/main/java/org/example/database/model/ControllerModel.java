package org.example.database.model;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.AUTO;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ControllerModel {
    @Id @GeneratedValue(strategy = AUTO)
    @Column(name = "id")
    private int id;
    @ManyToOne
    @JoinColumn(name="plan_id", nullable = false)
    private int plan;
    @Column(name = "name")
    private String name;
    @Column(name = "begin")
    private String begin;
    @Column(name = "subtype")
    private String subtype;

    public ControllerModel() { }

    public ControllerModel(int plan, String name, String begin, String subtype){
        this.plan = plan;
        this.name = name;
        this.begin = begin;
        this.subtype = subtype;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlan() {
        return plan;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }
}
