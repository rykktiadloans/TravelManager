package org.example.view.interactions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.example.view.Root;

public interface ButtonStrategy {
    public EventHandler<ActionEvent> action(Root root);
}
