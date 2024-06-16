package org.example.view.interactions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.example.controller.EventController;
import org.example.controller.NormalEventController;
import org.example.model.Event;
import org.example.model.Plan;
import org.example.model.types.ConstantEvent;
import org.example.view.Root;

import java.time.LocalTime;

public class AddConstant implements ButtonStrategy{

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
