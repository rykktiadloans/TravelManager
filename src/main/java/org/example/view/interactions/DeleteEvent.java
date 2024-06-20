package org.example.view.interactions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.example.model.Plan;
import org.example.view.Root;

/**
 * A strategy on how to delete a selected event.
 */
public class DeleteEvent implements ButtonStrategy{
    /**
     * Returns an event handler that removes the event selected in the plan singleton.
     * @param root Root object we operate with.
     * @return The event handler
     */
    @Override
    public EventHandler<ActionEvent> action(Root root) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Plan plan = Plan.getInstance();
                if(plan.getSelectedItem() == null){
                    System.out.println("null delete");
                    return;
                }
                plan.getEventControllers().remove(plan.getSelectedItem());
                plan.setSelectedItem(null);
                root.update();

            }
        };


    }
}
