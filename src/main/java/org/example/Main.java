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
import org.example.database.model.ControllerModel;
import org.example.database.model.DynamicControllerModel;
import org.example.database.model.NormalControllerModel;
import org.example.database.model.PlanModel;
import org.example.model.Event;
import org.example.model.Plan;
import org.example.model.types.ConstantEvent;
import org.example.model.types.EventType;
import org.example.model.types.MiscEvent;
import org.example.view.Root;
import org.example.view.interactions.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;
import static javafx.scene.input.MouseEvent.MOUSE_RELEASED;


/**
 * The main class where all the components come together.
 */
public class Main extends Application {

    /**
     * Function that defines the construction of the main window.
     * @param stage Stage that the program is run in.
     */
    @Override
    public void start(Stage stage) {


        Root root = null;

        try{
            // Configuring the Hibernate connection to SQLite for the ability to save sessions.
            Configuration cfg = new Configuration().configure()
                    .addAnnotatedClass(PlanModel.class)
                    .addAnnotatedClass(ControllerModel.class)
                    .addAnnotatedClass(DynamicControllerModel.class)
                    .addAnnotatedClass(NormalControllerModel.class)
            ;
            StandardServiceRegistry ssRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
            SessionFactory sessionFactory = cfg.buildSessionFactory(ssRegistry);
            root = new Root(sessionFactory, stage);
        }
        catch (Exception e){
            throw e;
        }

        // Setting up main space
        Scene scene = new Scene(root.getRoot(), 800, 600);
        root.update();


        stage.setTitle("Travel Manager");
        stage.setScene(scene);
        stage.show(); //
    }

    /**
     * The main() function that is run when the program is launched.
     * @param args Launching flags.
     */
    public static void main(String[] args) {
        launch();
    }
}