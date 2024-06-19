package org.example.model;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.example.controller.EventController;
import org.example.database.model.ControllerModel;
import org.example.database.model.DynamicControllerModel;
import org.example.database.model.NormalControllerModel;
import org.example.database.model.PlanModel;
import org.example.view.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.DateTimeException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javafx.scene.input.MouseEvent.MOUSE_RELEASED;

public class Plan {
    private static Plan plan = null;
    private int openPlan = -1;
    private ArrayList<EventController> eventControllers;
    private EventController selectedItem = null;

    private Plan(){
        this.eventControllers = new ArrayList<>();
    }

    public ArrayList<EventController> getEventControllers(){
        return this.eventControllers;
    }

    public void addEventController(EventController ec){
        boolean isOverlap = this.getEventControllers().stream().allMatch(cur -> {
            return !(cur.getEvent().getEnd().isBefore(ec.getEvent().getStart()) || cur.getEvent().getStart().isAfter(ec.getEvent().getEnd()));
        }) && !this.getEventControllers().isEmpty();
        if(isOverlap){
            throw new DateTimeException("New event overlaps with other events");
        }
        this.getEventControllers().add(ec);
        this.sortEvents();
    }

    public EventController getEventController(int i){
        return this.eventControllers.get(i);
    }

    public void deleteEventController(int i){
        this.eventControllers.remove(i);
    }

    public void sortEvents(){
        Stream<EventController> stream = this.eventControllers.stream();
        stream = stream.sorted(Comparator.comparing(cur -> cur.getEvent().getStart()));
        this.eventControllers = (ArrayList<EventController>) stream.collect(Collectors.toList());
    }

    public static Plan getInstance(){
        if(plan == null){
            plan = new Plan();
        }
        return plan;
    }

    public EventController getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(EventController selectedItem) {
        this.selectedItem = selectedItem;
    }

    public ScrollPane getPane(Root root){
        ScrollPane eventControllerScroller = new ScrollPane();
        eventControllerScroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        eventControllerScroller.setPadding(new Insets(5.0f));
        eventControllerScroller.setFitToWidth(true);
        VBox eventControllerBox = new VBox();
        eventControllerBox.setSpacing(5.0f);
        eventControllerScroller.setContent(eventControllerBox);
        ArrayList<GridPane> children = new ArrayList<>();

        // Select an event
        eventControllerBox.addEventHandler(MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Plan plan = Plan.getInstance();
                for(int i = 0; i < children.size(); i++){
                    if(children.get(i).isHover() ){
                        int prev = 0;
                        if(plan.getSelectedItem() != null){
                            prev = plan.eventControllers.indexOf(plan.getSelectedItem());
                        }
                        plan.setSelectedItem(plan.getEventController(i));
                        root.update();

                    }
                }

            }
        });

        for(EventController controller : plan.getEventControllers()){
            GridPane t = controller.getBox();
            if(controller == this.getSelectedItem()){
                t.setBorder(new Border(new BorderStroke(new Color(0, (double) 174 / 255, 1, 1), BorderStrokeStyle.SOLID, new CornerRadii(5.0), new BorderWidths(2))));

            }
            children.add(t);
            eventControllerBox.getChildren().add(t);
        }

        return eventControllerScroller;  //
    }

    public void save(String name, SessionFactory sessionFactory){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        PlanModel planModel = new PlanModel(name);
        planModel.setControllerModels(new HashSet<>());
        try {
            transaction.begin();
            if(this.openPlan != -1){
                session.remove(session.find(PlanModel.class, this.openPlan));
            }
            transaction.commit();
            transaction = session.getTransaction();
            transaction.begin();
            session.persist(planModel);
            for(EventController eventController : this.getEventControllers()){
                ControllerModel controllerModel = eventController.getModel();
                controllerModel.setPlan(planModel);
                planModel.getControllerModels().add(controllerModel);
                session.persist(controllerModel);
            }
            this.openPlan = planModel.getId();
            System.out.println(this.openPlan);
            transaction.commit();

        }
        catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            throw e;
        }
        finally {
            session.close();
        }

    }

    public void load(String name, SessionFactory sessionFactory) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            PlanModel planModel = session.createQuery("WHERE name LIKE ?1", PlanModel.class)
                            .setParameter(1, name)
                    .getSingleResult();
            Collection<NormalControllerModel> normalControllerModelCollection = new ArrayList<>(
                    session.createQuery("WHERE plan = ?1", NormalControllerModel.class)
                    .setParameter(1, planModel).getResultList());
            Collection<DynamicControllerModel> dynamicControllerModelCollection = new ArrayList<>(
                    session.createQuery("WHERE plan = ?1", DynamicControllerModel.class)
                            .setParameter(1, planModel).getResultList());
            Collection<ControllerModel> collection = new ArrayList<>(normalControllerModelCollection);
            collection.addAll(dynamicControllerModelCollection);
            this.getEventControllers().clear();
            for(ControllerModel model : collection){
                EventController eventController = model.unmodelize();
                this.addEventController(eventController);
            }
            this.openPlan = planModel.getId();
            transaction.commit();

        }
        catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            throw e;
        }
        finally {
            session.close();
        }

    }

    public Collection<String> getSessions(SessionFactory sessionFactory){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        Collection<String> collection;
        try{
            transaction.begin();
            var query = session.createQuery("SELECT name FROM PlanModel", String.class);
            collection = query.getResultList();
            transaction.commit();
        }
        catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            throw e;
        }
        finally {
            session.close();

        }
        return collection;

    }
}
