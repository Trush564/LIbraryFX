module org.example.libraryfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens org.example.libraryfx to javafx.fxml;
    opens org.example.libraryfx.model to javafx.fxml;
    opens org.example.libraryfx.service to javafx.fxml;
    opens org.example.libraryfx.database to javafx.fxml;
    
    exports org.example.libraryfx;
}