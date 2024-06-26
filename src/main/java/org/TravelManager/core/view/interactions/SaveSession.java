package org.TravelManager.core.view.interactions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.TravelManager.core.model.Plan;
import org.TravelManager.core.view.Root;
import org.hibernate.SessionFactory;

/**
 * A strategy that defines how to save a plan to the database.
 */
public class SaveSession implements ButtonStrategy{

    private Stage ownerStage;
    private SessionFactory sessionFactory;

    /**
     * A constructor with some of the stuff we need for the strategy to work.
     * @param ownerStage Main application stage so that we can define ownership on the dialogs.
     * @param sessionFactory SessionFactory we use for communicating with the database.
     */
    public SaveSession(Stage ownerStage, SessionFactory sessionFactory){
        this.ownerStage = ownerStage;
        this.sessionFactory = sessionFactory;
    }

    /**
     * Returns an event handler that opens up a dialog where we can define a name of the plan we save.
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
                dialog.setTitle("Save plan");
                VBox vbox = new VBox();
                vbox.setSpacing(5);
                vbox.setPadding(new Insets(5));
                vbox.setAlignment(Pos.TOP_CENTER);
                ListView<String> listView = new ListView<>();
                listView.getItems().addAll(Plan.getInstance().getSessions(sessionFactory));
                TextField textField = new TextField("new save");
                Button button = new Button("Save");
                vbox.getChildren().addAll(listView, textField, button);
                Scene dialogScene = new Scene(vbox, 400, 400);
                dialog.setScene(dialogScene);
                dialog.show();

                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if(textField.getText().trim().equals("")){
                            return;
                        }
                        Plan.getInstance().save(textField.getText().trim(), sessionFactory);
                        dialog.close();

                    }
                });

                listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        textField.setText(listView.getSelectionModel().getSelectedItem());
                    }
                });


            }
        };

    }
}
