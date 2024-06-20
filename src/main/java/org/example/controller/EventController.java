package org.example.controller;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.example.database.model.ControllerModel;
import org.example.model.Event;
import org.example.view.Root;

import java.util.ArrayList;

/**
 * The interface that defines the interface for the event controllers. It manages both the state of the event, its type, and the GUI that relate to it.
 */
public interface EventController {
    /**
     * Returns the Event object that the controller manages.
     * @return Event object of the controller.
     */
    public Event getEvent();

    /**
     * Returns the color associated with the event's type.
     * @return Color of the event's type.
     */
    public Color getColor();

    /**
     * Returns a list of all the available subtypes for the event's type.
     * @return List of subtypes of the event's type.
     */
    public ArrayList<String> getSubtypes();

    /**
     * Returns the selected subtype.
     * @return The selected subtype.
     */
    public String getSelectedType();

    /**
     * Sets the selected subtype of the event. It is set using its index so that only available subtypes are possible.
     * @param number Index of the subtype.
     */
    public void setSelectedType(int number);

    /**
     * Returns the GUI box that is displayed inside the Event list
     * @return The box that is displayed inside the Event list
     */
    public GridPane getBox();

    /**
     * Returns the box that allows the user to edit the event.
     * @param root Root object where we want to render the box.
     * @return The box that allows the user to edit the event.
     */
    public VBox getEditBox(Root root);

    /**
     * Returns a ControllerModel object that can be persisted inside an SQLite database.
     * @return The persistable representation of the controller.
     */
    public ControllerModel getModel();
}
