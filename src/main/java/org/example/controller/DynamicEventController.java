package org.example.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import org.example.database.model.ControllerModel;
import org.example.database.model.DynamicControllerModel;
import org.example.model.Event;
import org.example.model.Plan;
import org.example.model.types.EventType;
import org.example.view.Root;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class DynamicEventController implements EventController{

    private Event event;
    private String selectedType;
    private float distance;

    private HashMap<String, Float> subtypes;

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
    @Override
    public Event getEvent() {
        return this.event;
    }

    @Override
    public Color getColor() {
        return Color.BLUE;
    }

    @Override
    public ArrayList<String> getSubtypes() {
        return new ArrayList<>(this.subtypes.keySet());
    }

    @Override
    public String getSelectedType() {
        return this.selectedType;
    }

    @Override
    public void setSelectedType(int number) {
        this.selectedType = this.getSubtypes().get(number);
        this.event.setEnd(this.event.getStart()
                .plusMinutes(
                        (int) ((distance / this.subtypes.get(this.selectedType)) * 60)));


    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
        this.event.setEnd(this.event.getStart()
                .plusMinutes(
                        (int) ((distance / this.subtypes.get(this.selectedType)) * 60)));
    }

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

    public ControllerModel getModel(){
        DynamicControllerModel model = new DynamicControllerModel();
        model.setDistance(this.getDistance());
        model.setBegin(this.getEvent().getStart().format(DateTimeFormatter.ofPattern("HH:mm")));
        model.setName(this.getEvent().getName());
        model.setSubtype(this.getSelectedType());
        return model;

    }


}
