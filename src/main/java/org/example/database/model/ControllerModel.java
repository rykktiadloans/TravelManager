package org.example.database.model;

import jakarta.persistence.*;
import org.example.controller.EventController;
import org.example.model.Plan;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.AUTO;
import static jakarta.persistence.GenerationType.SEQUENCE;

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

    public ControllerModel() { }

    public ControllerModel(PlanModel plan, String name, String begin, String subtype){
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

    public PlanModel getPlan() {
        return plan;
    }

    public void setPlan(PlanModel plan) {
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

    public EventController unmodelize() throws Exception {
        throw new Exception("Normal controller models are serialized");
    }

}
