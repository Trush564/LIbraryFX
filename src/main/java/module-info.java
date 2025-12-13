module org.example.libraryfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.libraryfx to javafx.fxml;
    exports org.example.libraryfx;
}