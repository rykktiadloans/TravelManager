package org.example.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import org.example.database.model.ControllerModel;
import org.example.database.model.NormalControllerModel;
import org.example.model.Event;
import org.example.model.Plan;
import org.example.model.types.EventType;
import org.example.view.Root;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * A class that defines an event controller that has both beginning and end time set manually.
 * The event type is set as a separate object. Fit for public transportation and misc events such as eating or sightseeing.
 */
public class NormalEventController implements EventController{

    /**
     * The Event to be managed
     */
    private Event event;
    /**
     * The EventType object associated with the event.
     */
    private EventType type;
    /**
     * The selected subtype
     */
    private String selectedType;

    /**
     * The constructor fo the controller. Takes in an event to manage and an associated type of the event.
     * @param event Event for the controller to manage
     * @param type Type of the event
     */
    public NormalEventController(Event event, EventType type){
        this.event = event;
        this.type = type;
        this.selectedType = this.type.getSubtypes().get(0);
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
     * The color is set inside the EventType object associated with the event.
     */
    @Override
    public Color getColor() {
        return this.type.getColor();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<String> getSubtypes() {
        return this.type.getSubtypes();
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
        TextField endField = new TextField(this.getEvent().getEnd().format(DateTimeFormatter.ofPattern("HH:mm")));
        Button submitButton = new Button("Submit");
        Label errorLabel = new Label("");
        errorLabel.setTextFill(Color.RED);

        submitButton.setOnAction( actionEvent -> {
            try{
                LocalTime.parse(beginField.getText());
                LocalTime.parse(endField.getText());
            }
            catch (DateTimeException | NullPointerException e){
                errorLabel.setText("Bad time format");
                return;

            }
            try{
                Event newEvent = new Event(nameField.getText(), LocalTime.parse(beginField.getText()), LocalTime.parse(endField.getText()));
            }
            catch (DateTimeException e){
                errorLabel.setText(e.getLocalizedMessage());
                return;
            }
            Event newEvent = new Event(nameField.getText(), LocalTime.parse(beginField.getText()), LocalTime.parse(endField.getText()));
            NormalEventController newController = new NormalEventController(newEvent, this.type);
            newController.setSelectedType(this.type.getSubtypes().indexOf(subtypeField.getValue()));
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

        box.getChildren().addAll(nameField, subtypeField, beginField, endField, submitButton, errorLabel);

        return box;
    }

    /**
     * {@inheritDoc}
     */
    public ControllerModel getModel(){
        NormalControllerModel model = new NormalControllerModel();
        model.setName(this.getEvent().getName());
        model.setBegin(this.getEvent().getStart().format(DateTimeFormatter.ofPattern("HH:mm")));
        model.setEnd(this.getEvent().getEnd().format(DateTimeFormatter.ofPattern("HH:mm")));
        model.setSubtype(this.selectedType);
        return model;

    }

}
