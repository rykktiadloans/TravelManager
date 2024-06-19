package org.example.view.interactions;

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
import org.example.controller.EventController;
import org.example.controller.NormalEventController;
import org.example.model.Event;
import org.example.model.Plan;
import org.example.model.types.ConstantEvent;
import org.example.view.Root;
import org.hibernate.SessionFactory;

import java.time.LocalTime;

public class SaveSession implements ButtonStrategy{

    private Stage ownerStage;
    private SessionFactory sessionFactory;

    public SaveSession(Stage ownerStage, SessionFactory sessionFactory){
        this.ownerStage = ownerStage;
        this.sessionFactory = sessionFactory;
    }

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
