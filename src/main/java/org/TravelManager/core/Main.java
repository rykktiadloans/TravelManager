package org.TravelManager.core;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.TravelManager.core.database.model.ControllerModel;
import org.TravelManager.core.database.model.DynamicControllerModel;
import org.TravelManager.core.database.model.NormalControllerModel;
import org.TravelManager.core.database.model.PlanModel;
import org.TravelManager.core.view.Root;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


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