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
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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


        try{
            Configuration cfg = new Configuration().configure();
            StandardServiceRegistry ssRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
            SessionFactory sessionFactory = cfg.buildSessionFactory(ssRegistry);
            Session session = sessionFactory.openSession();
            Object result = session.createQuery("select sqlite_version()").getSingleResult();
            System.out.println("MY DATABASE VERSION IS::::\n"+result);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }


        stage.setTitle("Travel Manager");
        stage.setScene(scene);
        stage.show(); //
    }
    public static void main(String[] args) {
        launch();
    }
}