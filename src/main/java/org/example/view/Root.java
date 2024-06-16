package org.example.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.example.model.Plan;
import org.example.view.interactions.AddConstant;
import org.example.view.interactions.AddDynamic;
import org.example.view.interactions.AddMisc;
import org.example.view.interactions.DeleteEvent;

public class Root {
    private MenuBar menuBar;
    private GridPane root;
    private VBox rightPanel;

    public Root(){
        this.root = new GridPane();
        Plan plan = Plan.getInstance();
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col1.setPercentWidth(65);
        col1.setHgrow(Priority.ALWAYS);
        col2.setPercentWidth(35);
        col2.setHgrow(Priority.ALWAYS);
        this.root.getColumnConstraints().addAll(col1, col2);

        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints();
        row1.setMinHeight(20);
        row1.setMaxHeight(20);
        row2.setVgrow(Priority.ALWAYS);
        this.root.getRowConstraints().addAll(row1, row2);

        // Menubar
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        MenuItem addConstant = new MenuItem("Add constant event");
        addConstant.setOnAction(new AddConstant().action(this));
        MenuItem addMisc = new MenuItem("Add misc event");
        addMisc.setOnAction(new AddMisc().action(this));
        MenuItem addDynamic = new MenuItem("Add dynamic event");
        addDynamic.setOnAction(new AddDynamic().action(this));
        MenuItem deleteEvent = new MenuItem("Delete selected event");
        deleteEvent.setOnAction(new DeleteEvent().action(this));
        editMenu.getItems().addAll(addConstant, addMisc, addDynamic, deleteEvent);
        this.menuBar = new MenuBar(fileMenu, editMenu);
        this.menuBar.setUseSystemMenuBar(true);

        // Assemble all of that
        this.update();


    }

    public void update(){
        this.rightPanel = new VBox();
        Plan plan = Plan.getInstance();
        if(plan.getSelectedItem() != null){
            this.rightPanel = plan.getSelectedItem().getEditBox(this);
        }
        this.rightPanel.setPadding(new Insets(5));
        this.rightPanel.setSpacing(5);

        this.root.getChildren().clear();
        this.root.add(this.menuBar, 0, 0, 2, 1);
        this.root.add(Plan.getInstance().getPane(this), 0, 1);
        this.root.add(this.rightPanel,1, 1);
    }

    public GridPane getRoot() {
        return this.root;
    }

    public void setRoot(GridPane root) {
        this.root = root;
    }

    public VBox getRightPanel() {
        return rightPanel;
    }

    public void setRightPanel(VBox rightPanel) {
        this.rightPanel = rightPanel;
    }
}
