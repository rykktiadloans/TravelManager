package org.TravelManager.core.view.interactions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.TravelManager.core.controller.EventController;
import org.TravelManager.core.controller.NormalEventController;
import org.TravelManager.core.model.Event;
import org.TravelManager.core.model.Plan;
import org.TravelManager.core.model.types.ConstantEvent;
import org.TravelManager.core.view.Root;

import java.time.LocalTime;

/**
 * A strategy for adding a new empty constant event
 */
public class AddConstant implements ButtonStrategy{

    /**
     * Returns an EventHandler that adds a new NormalEventController with ConstantType that starts at the latest point of the
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
                    Event event = new Event("Constant", LocalTime.of(6, 0), LocalTime.of(6, 0));
                    ConstantEvent eventType = new ConstantEvent();
                    plan.addEventController(new NormalEventController(event, eventType));
                }
                else {
                    LocalTime start = last.getEvent().getEnd().plusMinutes(1);
                    Event event = new Event("Constant", start, start);
                    ConstantEvent eventType = new ConstantEvent();
                    plan.addEventController(new NormalEventController(event, eventType));


                }
                root.update();


            }
        };

    }
}
