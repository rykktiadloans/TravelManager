package org.example.view.interactions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.example.view.Root;

/**
 * This is a strategy interface to define the way a button or menu item should work.
 */
public interface ButtonStrategy {
    /**
     * Return the EventHandler to use.
     * @param root Root object we operate with.
     * @return The event handler.
     */
    public EventHandler<ActionEvent> action(Root root);
}
