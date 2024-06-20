/**
 * Module of the Travel Manager application.
 */
module TravelManager {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;
    requires jakarta.persistence;
    requires org.xerial.sqlitejdbc;
    opens org.example.database.model to org.hibernate.orm.core;
    exports org.example;
    exports org.example.database.model;
}