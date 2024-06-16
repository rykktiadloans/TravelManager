package org.example;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.controller.DynamicEventController;
import org.example.controller.EventController;
import org.example.controller.NormalEventController;
import org.example.model.Event;
import org.example.model.Plan;
import org.example.model.types.ConstantEvent;
import org.example.model.types.EventType;
import org.example.model.types.MiscEvent;
import org.example.view.Root;
import org.example.view.interactions.*;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;
import static javafx.scene.input.MouseEvent.MOUSE_RELEASED;


public class Main extends Application {

    @Override
    public void start(Stage stage) {
        // Setting up main space
        Root root = new Root();
        Scene scene = new Scene(root.getRoot(), 800, 600);
        root.update();



        stage.setTitle("Travel Manager");
        stage.setScene(scene);
        stage.show(); //
    }
    public static void main(String[] args) {
        launch();
    }
}