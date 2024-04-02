module com.example.banking {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens com.example.banking to javafx.fxml;
    exports com.example.banking;
    exports com.example.banking.Controllers;
    exports com.example.banking.Controllers.Admin;
    exports com.example.banking.Controllers.Client;
    exports com.example.banking.Models;
    exports com.example.banking.Views;

}