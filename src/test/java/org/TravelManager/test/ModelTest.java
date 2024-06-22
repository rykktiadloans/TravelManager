package org.TravelManager.test;

import org.TravelManager.core.controller.NormalEventController;
import org.TravelManager.core.model.Event;
import org.TravelManager.core.model.Plan;
import org.TravelManager.core.model.types.ConstantEvent;
import org.junit.Assert;
import org.junit.Test;

import java.time.DateTimeException;
import java.time.LocalTime;

public class ModelTest {
    @Test
    public void eventBeginBeforeEndTest(){ // In an event, the beginning should come before an end.
        LocalTime begin = LocalTime.of(12, 20);
        LocalTime end = LocalTime.of(12, 0);
        Exception exception = Assert.assertThrows(DateTimeException.class, () -> {
            Event event = new Event("sample", begin, end);
        });
    }

    @Test(expected = Test.None.class) // No exception expected. An event should be able to have equal beginning and end.
    public void eventEqualBeginAndEndTest(){
        LocalTime begin = LocalTime.of(12, 20);
        Event event = new Event("sample", begin, begin);

    }

    @Test
    public void reassigningBeginConflictTest(){ // Assert that an exception will be thrown in case a reassignment will create a conflict
        LocalTime begin = LocalTime.of(12, 20);
        LocalTime end = LocalTime.of(12, 30);
        LocalTime newBegin = LocalTime.of(12, 40);
        Event event = new Event("sample", begin, end);
        Exception exception = Assert.assertThrows(DateTimeException.class, () -> {
            event.setStart(newBegin);
        });
    }
    @Test
    public void reassigningEndConflictTest(){ // Same as above but for the end property
        LocalTime begin = LocalTime.of(12, 20);
        LocalTime end = LocalTime.of(12, 30);
        LocalTime newEnd = LocalTime.of(12, 10);
        Event event = new Event("sample", begin, end);
        Exception exception = Assert.assertThrows(DateTimeException.class, () -> {
            event.setEnd(newEnd);
        });
    }

    @Test
    public void overlappingEventsPlanTest(){ // If a new added event is overlapping, it should throw an exception.
        Plan plan = Plan.getInstance();
        Event event1 = new Event("sample", LocalTime.of(6, 0), LocalTime.of(6, 20));
        Event event2 = new Event("sample 2", LocalTime.of(6, 0), LocalTime.of(6, 10));
        NormalEventController nec1 = new NormalEventController(event1, new ConstantEvent());
        NormalEventController nec2 = new NormalEventController(event2, new ConstantEvent());
        plan.addEventController(nec1);
        Assert.assertThrows(DateTimeException.class, () -> {
            plan.addEventController(nec2);
        });
        plan.getEventControllers().clear();
    }

    @Test
    public void sortPlanTest(){
        Plan plan = Plan.getInstance();
        Event event1 = new Event("sample", LocalTime.of(6, 20), LocalTime.of(6, 40));
        Event event2 = new Event("sample 2", LocalTime.of(6, 0), LocalTime.of(6, 10));
        NormalEventController nec1 = new NormalEventController(event1, new ConstantEvent());
        NormalEventController nec2 = new NormalEventController(event2, new ConstantEvent());
        plan.getEventControllers().add(nec1);
        plan.getEventControllers().add(nec2);
        plan.sortEvents();
        Assert.assertEquals("sample 2", plan.getEventController(0).getEvent().getName());
        plan.getEventControllers().clear();
    }



}
