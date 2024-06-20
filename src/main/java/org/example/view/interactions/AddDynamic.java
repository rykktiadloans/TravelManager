package org.example.view.interactions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.example.controller.DynamicEventController;
import org.example.controller.EventController;
import org.example.model.Event;
import org.example.model.Plan;
import org.example.view.Root;

import java.time.LocalTime;

/**
 * A strategy on how to add a new empty dynamic event.
 */
public class AddDynamic implements ButtonStrategy{

    /**
     * Returns an EventHandler that adds a new DynamicEventController that starts at the latest point of the
     * plan and ends at the same time.
     * @param root Root object we operate with.
     * @return The event handler.
     */
    @Override
    public EventHandler<ActionEvent> action(Root root) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Plan plan = Plan.getInstance();
                EventController last = null;
                if(!plan.getEventControllers().isEmpty()){
                    last = plan.getEventController(plan.getEventControllers().size() - 1);
                }
                if(last == null){
                    Event event = new Event("Dynamic", LocalTime.of(6, 0), LocalTime.of(6, 0));
                    plan.addEventController(new DynamicEventController(event, 0));
                }
                else {
                    LocalTime start = last.getEvent().getEnd().plusMinutes(1);
                    Event event = new Event("Dynamic", start, start);
                    plan.addEventController(new DynamicEventController(event, 0));


                }
                root.update();

            }
        };

    }
}
