/**
 * Module of the Travel Manager application.
 */
module TravelManager.core {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;
    requires jakarta.persistence;
    requires org.xerial.sqlitejdbc;
    requires junit;
    opens org.TravelManager.core.database.model to org.hibernate.orm.core;
    exports org.TravelManager.core;
    exports org.TravelManager.core.database.model;
    exports org.TravelManager.core.controller;
    exports org.TravelManager.core.model;
    exports org.TravelManager.core.model.types;
}
