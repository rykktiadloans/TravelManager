package org.example.controller;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.example.model.Event;
import org.example.view.Root;

import java.util.ArrayList;

public interface EventController {
    public Event getEvent();
    public Color getColor();
    public ArrayList<String> getSubtypes();
    public String getSelectedType();
    public void setSelectedType(int number);
    public GridPane getBox();
    public VBox getEditBox(Root root);
}
