package org.TravelManager.core.view.interactions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.TravelManager.core.model.Plan;
import org.TravelManager.core.view.Root;
import org.hibernate.SessionFactory;

/**
 * A strategy that defines how to load a plan from the database.
 */
public class LoadSession implements ButtonStrategy{

    private Stage ownerStage;
    private SessionFactory sessionFactory;

    /**
     * A constructor with some of the stuff we need for the strategy to work.
     * @param ownerStage Main application stage so that we can define ownership on the dialogs.
     * @param sessionFactory SessionFactory we use for communicating with the database.
     */
    public LoadSession(Stage ownerStage, SessionFactory sessionFactory){
        this.ownerStage = ownerStage;
        this.sessionFactory = sessionFactory;
    }

    /**
     * Returns an event handler that opens up a dialog where we can select a saved plan to load.
     * @param root Root object we operate with.
     * @return The event handler.
     */
    @Override
    public EventHandler<ActionEvent> action(Root root) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(ownerStage);
                dialog.setTitle("Load plan");
                VBox vbox = new VBox();
                vbox.setSpacing(5);
                vbox.setPadding(new Insets(5));
                vbox.setAlignment(Pos.TOP_CENTER);
                ListView<String> listView = new ListView<>();
                listView.getItems().addAll(Plan.getInstance().getSessions(sessionFactory));
                Button button = new Button("Load");
                vbox.getChildren().addAll(listView, button);
                Scene dialogScene = new Scene(vbox, 400, 400);
                dialog.setScene(dialogScene);
                dialog.show();

                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if(listView.getSelectionModel().getSelectedItem() == null){
                            return;
                        }
                        try {
                            Plan.getInstance().load(listView.getSelectionModel().getSelectedItem(), sessionFactory);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        dialog.close();
                        Plan.getInstance().setSelectedItem(null);
                        root.update();

                    }
                });
            }
        };

    }
}
