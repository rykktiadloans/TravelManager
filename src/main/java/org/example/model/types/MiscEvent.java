package org.example.model.types;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;

public class MiscEvent implements EventType{

    private final ArrayList<String> subtypes = new ArrayList<>(Arrays.asList(
            "Breakfast",
            "Lunch",
            "Dinner",
            "Sightseeing",
            "Relax"
    ));
    @Override
    public Color getColor() {
        return Color.GREEN;
    }

    @Override
    public ArrayList<String> getSubtypes() {
        return this.subtypes;
    }
}
