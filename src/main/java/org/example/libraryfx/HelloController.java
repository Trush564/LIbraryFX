package org.example.libraryfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private Button loginButton;
    
    @FXML
    private Button registrationButton;

    @FXML
    protected void onLoginButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) loginButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Вхід");
    }

    @FXML
    protected void onRegistrationButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("reg.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) registrationButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Реєстрація");
    }
}
