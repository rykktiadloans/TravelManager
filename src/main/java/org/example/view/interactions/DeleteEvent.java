package org.example.view.interactions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.example.model.Plan;
import org.example.view.Root;

public class DeleteEvent implements ButtonStrategy{
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
