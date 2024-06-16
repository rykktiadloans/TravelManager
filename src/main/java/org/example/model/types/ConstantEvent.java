package org.example.model.types;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;

public class ConstantEvent implements EventType{

    private final ArrayList<String> subtypes = new ArrayList<>(Arrays.asList(
            "Train",
            "Metro",
            "Bus",
            "Plane",
            "Boat"
    ));
    @Override
    public Color getColor() {
        return Color.RED;
    }

    @Override
    public ArrayList<String> getSubtypes() {
        return this.subtypes;
    }
}
