package org.example.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.model.Plan;
import org.example.view.interactions.*;
import org.hibernate.SessionFactory;

/**
 * Root is a GUI object used to build the main interface of the program.
 */
public class Root {
    private MenuBar menuBar;
    private GridPane root;
    private VBox rightPanel;
    private SessionFactory sessionFactory;

    /**
     * The constructor. Takes in a SessionFactory and a Stage for the save/load features to work.
     * @param sessionFactory Session factory we use to communicate with the database.
     * @param ownerStage The main stage of the program.
     */
    public Root(SessionFactory sessionFactory, Stage ownerStage){
        this.sessionFactory = sessionFactory;
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


        MenuItem saveSession = new MenuItem("Save plan");
        saveSession.setOnAction(new SaveSession(ownerStage, sessionFactory).action(this));
        MenuItem loadSession = new MenuItem("Load plan");
        loadSession.setOnAction(new LoadSession(ownerStage, sessionFactory).action(this));


        editMenu.getItems().addAll(addConstant, addMisc, addDynamic, deleteEvent);
        fileMenu.getItems().addAll(saveSession, loadSession);
        this.menuBar = new MenuBar(fileMenu, editMenu);
        this.menuBar.setUseSystemMenuBar(true);

        // Assemble all of that
        this.update();


    }

    /**
     * Reloads akk the components of the root, such as event list and edit box.
     */
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

    /**
     * Return the GUI object.
     * @return The GUI.
     */
    public GridPane getRoot() {
        return this.root;
    }

    /**
     * Set the GUI of the root.
     * @param root The GUI.
     */
    public void setRoot(GridPane root) {
        this.root = root;
    }

    /**
     * Return the edit box.
     * @return Edit box.
     */
    public VBox getRightPanel() {
        return rightPanel;
    }

    /**
     * Change the edit box.
     * @param rightPanel Edit box.
     */
    public void setRightPanel(VBox rightPanel) {
        this.rightPanel = rightPanel;
    }

    /**
     * Return the SessionFactory we use to communicate with the database
     * @return The SessionFactory.
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Set the new SessionFactory
     * @param sessionFactory New SessionFactory.
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
