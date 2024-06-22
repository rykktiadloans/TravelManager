package org.TravelManager.core.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.TravelManager.core.database.model.ControllerModel;
import org.TravelManager.core.database.model.DynamicControllerModel;
import org.TravelManager.core.model.Event;
import org.TravelManager.core.model.Plan;
import org.TravelManager.core.view.Root;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The class that defines an event controller that only has a set beginning time.
 * Its end time is calculated using the method of transportation set by the selected subtype and the distance that is traversed.
 * It's fit for defining events where the user has to travel by foot or their own vehicle.
 */
public class DynamicEventController implements EventController{

    /**
     * The event that is managed
     */
    private Event event;
    /**
     * The type that is selected for the event.
     */
    private String selectedType;
    /**
     * The distance of the travel.
     */
    private float distance;

    /**
     * The object that maps available subtypes to their speed.
     * <table>
     *     <thead>
     *         <tr>
     *             <th>Subtype</th>
     *             <th>Speed (km/h)</th>
     *         </tr>
     *     </thead>
     *     <tbody>
     *         <tr>
     *             <td>Walk</td>
     *             <td>1 km/h</td>
     *         </tr>
     *         <tr>
     *             <td>Run</td>
     *             <td>3 km/h</td>
     *         </tr>
     *         <tr>
     *             <td>Car</td>
     *             <td>80 km/h</td>
     *         </tr>
     *         <tr>
     *             <td>Bike</td>
     *             <td>10 km/h</td>
     *         </tr>
     *     </tbody>
     * </table>
     */
    private HashMap<String, Float> subtypes;

    /**
     * The constructor. Takes the Event object to manage and the initial distance of the travel.
     * A unique EventType object doesn't need to be supplied because it's stored as a part of the object.
     * @param event Event to manage
     * @param distance Distance of the travel
     */
    public DynamicEventController(Event event, float distance){
        this.event = event;
        this.distance = distance;
        this.subtypes = new HashMap<>();
        this.subtypes.put("Walk", 1.0f);
        this.subtypes.put("Run", 3.0f);
        this.subtypes.put("Car", 80.0f);
        this.subtypes.put("Bike", 10.0f);
        this.selectedType = "Walk";
        this.event.setEnd(this.event.getStart()
                .plusMinutes(
                        (int) ((distance / this.subtypes.get(this.selectedType)) * 60)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Event getEvent() {
        return this.event;
    }

    /**
     * {@inheritDoc}
     * The color of this event controller is blue.
     */
    @Override
    public Color getColor() {
        return Color.BLUE;
    }

    /**
     * {@inheritDoc}
     *
     * <table>
     *     <thead>
     *         <tr>
     *             <th>Subtype</th>
     *             <th>Speed (km/h)</th>
     *         </tr>
     *     </thead>
     *     <tbody>
     *         <tr>
     *             <td>Walk</td>
     *             <td>1 km/h</td>
     *         </tr>
     *         <tr>
     *             <td>Run</td>
     *             <td>3 km/h</td>
     *         </tr>
     *         <tr>
     *             <td>Car</td>
     *             <td>80 km/h</td>
     *         </tr>
     *         <tr>
     *             <td>Bike</td>
     *             <td>10 km/h</td>
     *         </tr>
     *     </tbody>
     * </table>
     */
    @Override
    public ArrayList<String> getSubtypes() {
        return new ArrayList<>(this.subtypes.keySet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSelectedType() {
        return this.selectedType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSelectedType(int number) {
        this.selectedType = this.getSubtypes().get(number);
        this.event.setEnd(this.event.getStart()
                .plusMinutes(
                        (int) ((distance / this.subtypes.get(this.selectedType)) * 60)));


    }

    /**
     * Return the distance of the travel.
     * @return Distance of the travel.
     */
    public float getDistance() {
        return distance;
    }

    /**
     * Set the distance of the travel. The end time is automatically calculated.
     * @param distance Distance of the travel.
     */
    public void setDistance(float distance) {
        this.distance = distance;
        this.event.setEnd(this.event.getStart()
                .plusMinutes(
                        (int) ((distance / this.subtypes.get(this.selectedType)) * 60)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GridPane getBox() {
        GridPane box = new GridPane();
        box.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5.0f), null)));

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPrefWidth(70);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPrefWidth(100);
        box.getColumnConstraints().addAll(col1, col2, col3);
        box.setPadding(new Insets(5.0f));

        Label type = new Label(this.selectedType);
        type.setTextFill(this.getColor());
        Label name = new Label(this.event.getName());
        String beginString = this.getEvent().getStart().format(DateTimeFormatter.ofPattern("HH:mm"));
        String endString = this.getEvent().getEnd().format(DateTimeFormatter.ofPattern("HH:mm"));
        Label time = new Label(beginString + " - " + endString);
        time.setAlignment(Pos.BASELINE_RIGHT);

        box.addColumn(0, type);
        box.addColumn(1, name);
        box.addColumn(2, time);

        return box;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VBox getEditBox(Root root) {
        VBox box = new VBox();
        TextField nameField = new TextField(this.getEvent().getName());
        ComboBox<String> subtypeField = new ComboBox<>();
        subtypeField.getItems().addAll(this.getSubtypes());
        subtypeField.setValue(this.selectedType);
        TextField beginField = new TextField(this.getEvent().getStart().format(DateTimeFormatter.ofPattern("HH:mm")));
        TextField distanceField = new TextField(Float.toString(this.distance));
        Button submitButton = new Button("Submit");
        Label errorLabel = new Label("");
        errorLabel.setTextFill(Color.RED);

        submitButton.setOnAction( actionEvent -> {
            try{
                LocalTime.parse(beginField.getText());
            }
            catch (DateTimeException | NullPointerException e){
                errorLabel.setText("Bad time format");
                return;
            }
            try{
                Float.parseFloat(distanceField.getText());
            }
            catch (NumberFormatException | NullPointerException e){
                errorLabel.setText("Distance should be a fractional number");
                return;
            }
            Event newEvent = new Event(nameField.getText(), LocalTime.parse(beginField.getText()), LocalTime.parse(beginField.getText()));
            DynamicEventController newController = new DynamicEventController(newEvent, Float.parseFloat(distanceField.getText()));
            newController.setSelectedType(this.getSubtypes().indexOf(subtypeField.getValue()));
            try {
                Plan plan = Plan.getInstance();
                plan.getEventControllers().remove(this);
                plan.addEventController(newController);
            }
            catch (DateTimeException e){
                Plan plan = Plan.getInstance();
                plan.addEventController(this);
                errorLabel.setText(e.getLocalizedMessage());
                return;
            }
            Plan plan = Plan.getInstance();
            plan.setSelectedItem(null);
            root.update();
        });

        box.getChildren().addAll(nameField, subtypeField, beginField, distanceField, submitButton, errorLabel);

        return box;
    }

    /**
     * {@inheritDoc}
     */
    public ControllerModel getModel(){
        DynamicControllerModel model = new DynamicControllerModel();
        model.setDistance(this.getDistance());
        model.setBegin(this.getEvent().getStart().format(DateTimeFormatter.ofPattern("HH:mm")));
        model.setName(this.getEvent().getName());
        model.setSubtype(this.getSelectedType());
        return model;

    }


}
