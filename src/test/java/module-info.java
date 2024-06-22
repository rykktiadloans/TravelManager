/**
 * Module of the Travel Manager application.
 */
module TravelManager.test {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;
    requires jakarta.persistence;
    requires org.xerial.sqlitejdbc;
    requires junit;
    requires TravelManager.core;
    exports org.TravelManager.test to junit;
}