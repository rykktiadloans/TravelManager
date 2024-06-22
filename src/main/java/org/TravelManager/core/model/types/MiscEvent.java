package org.TravelManager.core.model.types;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class represents a type of event which can be used for any event that isn't related to transportation.
 */
public class MiscEvent implements EventType{

    private final ArrayList<String> subtypes = new ArrayList<>(Arrays.asList(
            "Breakfast",
            "Lunch",
            "Dinner",
            "Sightseeing",
            "Relax"
    ));

    /**
     * Returns the color green.
     * @return Color green.
     */
    @Override
    public Color getColor() {
        return Color.GREEN;
    }

    /**
     * Returns a list of subtypes
     * <ol start="0">
     *     <li>Breakfast</li>
     *     <li>Lunch</li>
     *     <li>Dinner</li>
     *     <li>Sightseeing</li>
     *     <li>Relax</li>
     * </ol>
     * @return A list of subtypes associated with this type.
     */
    @Override
    public ArrayList<String> getSubtypes() {
        return this.subtypes;
    }
}
