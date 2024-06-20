package org.example.view.interactions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.example.controller.EventController;
import org.example.controller.NormalEventController;
import org.example.model.Event;
import org.example.model.Plan;
import org.example.model.types.MiscEvent;
import org.example.view.Root;

import java.time.LocalTime;

/**
 * A strategy on how to add an empty misc event.
 */
public class AddMisc implements ButtonStrategy{

    /**
     * Returns an EventHandler that adds a new NormalEventController with MiscType that starts at the latest point of the
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
                    Event event = new Event("Misc", LocalTime.of(6, 0), LocalTime.of(6, 0));
                    MiscEvent eventType = new MiscEvent();
                    plan.addEventController(new NormalEventController(event, eventType));
                }
                else {
                    LocalTime start = last.getEvent().getEnd().plusMinutes(1);
                    Event event = new Event("Misc", start, start);
                    MiscEvent eventType = new MiscEvent();
                    plan.addEventController(new NormalEventController(event, eventType));


                }

                root.update();


            }
        };
    }
}
