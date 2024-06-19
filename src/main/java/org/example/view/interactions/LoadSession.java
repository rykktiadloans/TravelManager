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
import org.example.model.Plan;
import org.example.view.Root;
import org.hibernate.SessionFactory;

public class LoadSession implements ButtonStrategy{

    private Stage ownerStage;
    private SessionFactory sessionFactory;

    public LoadSession(Stage ownerStage, SessionFactory sessionFactory){
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
                        root.update();

                    }
                });
            }
        };

    }
}
