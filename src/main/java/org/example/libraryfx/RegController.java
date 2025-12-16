package org.example.libraryfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class RegController {
    @FXML
    private Button studentButton;
    
    @FXML
    private Button teacherButton;
    
    @FXML
    private Button librarianButton;

    @FXML
    private void initialize() {
        // Ініціалізація контролера
    }

    @FXML
    protected void onStudentButtonClick() throws IOException {
        // Перехід на реєстрацію студента
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("regstu.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) studentButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Реєстрація студента");
    }

    @FXML
    protected void onTeacherButtonClick() throws IOException {
        // Перехід на реєстрацію викладача
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("regte.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) teacherButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Реєстрація викладача");
    }

    @FXML
    protected void onLibrarianButtonClick() throws IOException {
        // Перехід на реєстрацію бібліотекаря
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("reglib.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) librarianButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Реєстрація бібліотекаря");
    }

    @FXML
    protected void onBackButtonClick() throws IOException {
        // Повернення на головну сторінку
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) studentButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Бібліотека");
    }
}
