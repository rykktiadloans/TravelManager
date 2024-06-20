package org.example.model.types;

import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * This interface is used for defining event types that can be used with NormalEventController.
 * Each type has an associated color and list of subtypes.
 */
public interface EventType {
    /**
     * Return the color of the type.
     * @return Color associated with the type.
     */
    public Color getColor();

    /**
     * Return a list of subtypes.
     * @return List if subtypes.
     */
    public ArrayList<String> getSubtypes();
}
