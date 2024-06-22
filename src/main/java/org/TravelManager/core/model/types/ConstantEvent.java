package org.TravelManager.core.model.types;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This is a class that defines a type of event that is associated with public transportation, whose beginning and end
 * are usually statically defined.
 */
public class ConstantEvent implements EventType{

    private final ArrayList<String> subtypes = new ArrayList<>(Arrays.asList(
            "Train",
            "Metro",
            "Bus",
            "Plane",
            "Boat"
    ));

    /**
     * Returns the color red, which is associated with a static event.
     * @return Returns the color red.
     */
    @Override
    public Color getColor() {
        return Color.RED;
    }

    /**
     * Returns a list of subtypes of the event.
     * <ol start="0">
     *     <li>Train</li>
     *     <li>Metro</li>
     *     <li>Bus</li>
     *     <li>Plane</li>
     *     <li>Boat</li>
     * </ol>
     * @return A list of subtypes.
     */
    @Override
    public ArrayList<String> getSubtypes() {
        return this.subtypes;
    }
}
