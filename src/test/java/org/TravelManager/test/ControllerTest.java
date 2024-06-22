package org.TravelManager.test;

import org.TravelManager.core.controller.DynamicEventController;
import org.TravelManager.core.model.Event;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ControllerTest {
    @Test
    public void accurateDynamicTimeCalculationTest(){
        Event event = new Event("sample", LocalTime.of(6, 0), LocalTime.of(6, 0));
        DynamicEventController dynamicEventController = new DynamicEventController(event, 5);
        Assert.assertEquals("11:00", dynamicEventController.getEvent().getEnd().format(DateTimeFormatter.ofPattern("HH:mm")));
    }
}
