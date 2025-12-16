package org.example.libraryfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.libraryfx.model.UserSession;
import org.example.libraryfx.service.AuthService;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button backButton;

    @FXML
    private void initialize() {
        // Ініціалізація контролера
    }

    @FXML
    protected void onLoginButtonClick() throws IOException {
        String login = loginField.getText();
        String password = passwordField.getText();

        if (login.isEmpty() || password.isEmpty()) {
            showAlert("Помилка", "Будь ласка, заповніть всі поля");
            return;
        }

        org.example.libraryfx.model.User user = AuthService.login(login, password);

        if (user != null) {
            HelloApplication.setCurrentUser(new UserSession(user, user.getId()));
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mainprofile.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) loginButton.getScene().getWindow();
            SceneUtils.switchScene(stage, root, "Профіль");
        } else {
            showAlert("Помилка входу", "Невірний логін або пароль");
        }
    }

    @FXML
    protected void onBackButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) backButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Бібліотека");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
